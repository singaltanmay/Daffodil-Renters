package com.daffodil.renters.core.service;

import com.daffodil.renters.core.model.beans.Listing;
import com.daffodil.renters.core.model.beans.ListingSkeletal;
import com.daffodil.renters.core.model.beans.postables.Building;
import com.daffodil.renters.core.model.beans.postables.Property;
import com.daffodil.renters.core.service.entity.BuildingService;
import com.daffodil.renters.core.service.entity.PropertyService;
import com.daffodil.renters.core.service.entity.RoomService;
import com.daffodil.renters.core.service.pooledactions.filteredquery.FilteredQueryTask;
import com.daffodil.renters.core.service.pooledactions.listingcreator.GenerateListingsTask;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;

@Service
public class AppService {

    private BuildingService buildingService;
    private PropertyService propertyService;
    private RoomService roomService;

    private ForkJoinPool masterPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

    @Autowired
    public AppService(BuildingService buildingService, PropertyService propertyService, RoomService roomService) {
        this.buildingService = buildingService;
        this.propertyService = propertyService;
        this.roomService = roomService;
    }

    /**
     * Insert a new building into the database. All children are recursively added to the database in this process.
     */
    public void createBuilding(Building building) {
        buildingService.insertBuilding(building);
    }

    /**
     * Gets listings when no filter is provided by client
     */
    public ResponseEntity<?> getListing(Optional<Long> propertyId, Optional<Boolean> min) {

        List<ListingSkeletal> listingSkeletalList = getListings(propertyId, min.orElse(false));
        if (listingSkeletalList != null) {
            return new ResponseEntity<>(listingSkeletalList, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    /**
     * Gets a skeletal listing from property id by querying for parent building
     */
    private List<ListingSkeletal> getListings(Optional<Long> propertyId, boolean min) {
        List<ListingSkeletal> listings = new LinkedList<>();

        if (propertyId.isPresent()) {
            long id = propertyId.get();
            Building building = buildingService.getBuildingByPropertyId(id);
            Property property = building.getProperties().map(it -> it.size() > 0 ? it.get(0) : null).orElse(propertyService.getPropertyById(id));
            property.setBuilding(Optional.of(building));
            listings.addAll(getListings(List.of(property), true, min));
        } else {
            return getListings(propertyService.getAllProperties(), false, min);
        }

        return listings;
    }

    /**
     * Executes getMinSkeletalListings for each property on new Threads.
     *
     * @param properties       List of properties.
     * @param buildingsPresent Pass true when parent buildings are already
     *                         known as otherwise the database will be queried for each property to find parent.
     * @return List of skeletal listings.
     */
    private List<ListingSkeletal> getListings(List<Property> properties, boolean buildingsPresent, boolean minListing) {

        if (!buildingsPresent) {
            properties.forEach(p -> {
                Building building = buildingService.getBuildingByPropertyId(p.getId().get());
                p.setBuilding(Optional.of(building));
            });
        }

        GenerateListingsTask task = new GenerateListingsTask(properties, minListing);
        masterPool.invoke(task);
        return task.join();
    }

    /**
     * Gets listings according to requested filter by performing recursive multithreaded queries.
     *
     * @param filter Filter provided by client.
     * @param min    If true then only ListingSkeletal is returned and an unnecessarily deep search is prevented.
     * @return A response entity containg a List of Listing(Skeletal)s and a Http response code.
     */
    public ResponseEntity<?> getFilteredListings(Listing.Filter filter, Optional<Boolean> min) {

        /**
         * Indicate to all children that a deep search is not required
         */
        if (min.isPresent() && min.get()) filter.minListing = true;

        if (filter.minListing) {

            /**
             * Launch a FilteredQueryTask
             */
            FilteredQueryTask queryTask = new FilteredQueryTask(filter, getServiceBundle());
            List<ListingSkeletal> skeletals = masterPool.invoke(queryTask);
            return new ResponseEntity<>(skeletals, HttpStatus.OK);

        } else {
            // TODO generate max listings
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }

    /**
     * Initializes a new ServiceBundle by setting all child services from this class.
     *
     * @return A ServiceBundle object
     */
    public ServiceBundle getServiceBundle() {
        return new ServiceBundle(
                this.buildingService,
                this.propertyService,
                this.roomService
        );
    }

    /**
     * An object containing an instance of all services. This is passed around to execute queries in multithreaded operations.
     */
    public class ServiceBundle {
        @Getter
        private BuildingService buildingService;
        @Getter
        private PropertyService propertyService;
        @Getter
        private RoomService roomService;

        public ServiceBundle(BuildingService buildingService, PropertyService propertyService, RoomService roomService) {
            this.buildingService = buildingService;
            this.propertyService = propertyService;
            this.roomService = roomService;
        }
    }

}
