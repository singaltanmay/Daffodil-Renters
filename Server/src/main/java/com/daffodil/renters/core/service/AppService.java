package com.daffodil.renters.core.service;

import com.daffodil.renters.core.model.beans.Listing;
import com.daffodil.renters.core.model.beans.ListingSkeletal;
import com.daffodil.renters.core.model.beans.postables.Building;
import com.daffodil.renters.core.model.beans.postables.Property;
import com.daffodil.renters.core.model.beans.postables.Room;
import com.daffodil.renters.core.service.entity.BuildingService;
import com.daffodil.renters.core.service.entity.PropertyService;
import com.daffodil.renters.core.service.entity.RoomService;
import com.daffodil.renters.core.service.pooledactions.filteredquery.FilteredQueryTask;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

@Service
public class AppService {

    /**
     * Executor service that limits and manages launched threads.
     */
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    private BuildingService buildingService;
    private PropertyService propertyService;
    private RoomService roomService;

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

        if (min.isPresent() && min.get()) {
            List<ListingSkeletal> listingSkeletalList = getMinSkeletalListings(propertyId);
            if (listingSkeletalList != null) {
                return new ResponseEntity<>(listingSkeletalList, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    /**
     * Gets a skeletal listing from property id by querying for parent building
     */
    public List<ListingSkeletal> getMinSkeletalListings(Optional<Long> propertyId) {
        List<ListingSkeletal> listings = new LinkedList<>();

        if (propertyId.isPresent()) {
            long id = propertyId.get();
            Building building = buildingService.getBuildingByPropertyId(id);
            Property property = building.getProperties().map(it -> it.size() > 0 ? it.get(0) : null).orElse(propertyService.getPropertyById(id));
            property.setBuilding(Optional.of(building));
            // TODO idk
            ListingSkeletal minListing = /*getMinListing(property)*/null;
            if (minListing != null) {
                listings.add(minListing);
            }
        } else {
            return getListingSkeletalFromPropertyList(propertyService.getAllProperties(), false);

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
    private List<ListingSkeletal> getListingSkeletalFromPropertyList(List<Property> properties, boolean buildingsPresent) {

        List<ListingSkeletal> listings = new LinkedList<>();

        if (properties != null) {

            CountDownLatch latch = new CountDownLatch(properties.size());

            for (Property p : properties) {

                if (p.getId().isPresent()) {
                    Long pid = p.getId().get();

                    new Thread(() -> {

                        if (!buildingsPresent) {
                            Building building = buildingService.getBuildingByPropertyId(pid);
                            p.setBuilding(Optional.of(building));
                        }
// TODO idk
//                        ListingSkeletal listingSkeletal = getMinListing(p);
//                        if (listingSkeletal != null) listings.add(listingSkeletal);
                        latch.countDown();
                    }).start();

                } else latch.countDown();

            }

            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        return listings;
    }

    /**
     * Gets all listings which comply with applied filters.
     *
     * @param filter Filter which is recursively applied to all children.
     * @param min    Skeletal listing required or not.
     * @return Response Entity containing a SkeletalListing or Listing determined by {@param min}.
     */
    public ResponseEntity<?> getFilteredListing(Listing.Filter filter, Optional<Boolean> min) {

        /**
         * Tell all children that a deep search is not required
         */
        if (min.isPresent() && min.get()) filter.minListing = true;

        /**
         * Buildings do not have any information about their children
         */
        List<Building> buildings = buildingService.runFilteredQuery(filter);

        /**
         * Bind child information to each building using separate recursive queries on multiple threads
         */
        bindFilteredChildrenToBuildings(filter, buildings);

        /**
         * Takes every property list in every building and creates a list of skeletal Listings
         */
        if (filter.minListing) {

            List<ListingSkeletal> skeletals = new LinkedList<>();

            if (buildings != null) {

                CountDownLatch latch = new CountDownLatch(buildings.size());

                buildings.forEach(it -> {

                    new Thread(() -> {

                        if (it.getProperties().isPresent()) {
                            List<Property> properties = it.getProperties().get();
                            properties.forEach(p -> p.setBuilding(Optional.of(it)));
                            List<ListingSkeletal> list = getListingSkeletalFromPropertyList(properties, true);
                            skeletals.addAll(list);
                        }

                        latch.countDown();

                    }).start();

                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                });
            }

            return new ResponseEntity<>(skeletals, HttpStatus.OK);

        } else {
            // TODO generate max listings
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private void bindFilteredChildrenToBuildings(Listing.Filter filter, List<Building> buildings) {

        if (buildings != null) {

            CountDownLatch latch = new CountDownLatch(buildings.size());

            buildings.forEach(building -> {
                new Thread(() -> {

                    // Get all filtered properties of this building
                    List<Property> properties = propertyService.runFilteredQuery((Listing.Filter) filter.setBuildingId(building.getId()));
                    bindFilteredChildrenToProperties(filter, properties);
                    building.setProperties(Optional.of(properties));

                    // TODO also bind parking spots in case of a deeper search

                    latch.countDown();
                }).start();
            });

            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    private void bindFilteredChildrenToProperties(Listing.Filter filter, List<Property> properties) {

        if (properties != null) {

            CountDownLatch latch = new CountDownLatch(properties.size());

            properties.forEach(property -> {
                new Thread(() -> {

                    List<Room> rooms = roomService.runFilteredQuery((Listing.Filter) filter.setPropertyId(property.getId()));
                    // TODO perform deeper search in case of min == false

                    property.setRooms(Optional.of(rooms));

                    latch.countDown();
                }).start();
            });

            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public ServiceBundle getServiceBundle() {
        return new ServiceBundle(
                this.buildingService,
                this.propertyService,
                this.roomService
        );
    }

    public ResponseEntity<?> getFilteredListingForkJoinPooled(Listing.Filter filter, Optional<Boolean> min) {

        /**
         * Tell all children that a deep search is not required
         */
        if (min.isPresent() && min.get()) filter.minListing = true;

        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        if (filter.minListing) {

            FilteredQueryTask queryTask = new FilteredQueryTask(filter, getServiceBundle());
            List<ListingSkeletal> skeletals = forkJoinPool.invoke(queryTask);
            return new ResponseEntity<>(skeletals, HttpStatus.OK);

        } else {
            // TODO generate max listings
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }

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
