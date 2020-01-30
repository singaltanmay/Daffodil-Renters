package com.daffodil.renters.core.service;

import com.daffodil.renters.core.model.beans.Listing;
import com.daffodil.renters.core.model.beans.ListingSkeletal;
import com.daffodil.renters.core.model.beans.postables.Building;
import com.daffodil.renters.core.model.beans.postables.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

@Service
public class AppService {

    private BuildingService buildingService;
    private PropertyService propertyService;

    @Autowired
    public AppService(BuildingService buildingService, PropertyService propertyService) {
        this.buildingService = buildingService;
        this.propertyService = propertyService;
    }

    public void createBuilding(Building building) {
        buildingService.insertBuilding(building);
    }

    public ResponseEntity<?> getListing(Optional<Long> propertyId, Optional<Boolean> min) {

        if (min.isPresent() && min.get()) {
            List<ListingSkeletal> listingSkeletalList = getMinSkeletalListings(propertyId);
            if (listingSkeletalList != null) {
                return new ResponseEntity<>(listingSkeletalList, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    public List<ListingSkeletal> getMinSkeletalListings(Optional<Long> propertyId) {
        List<ListingSkeletal> listings = new LinkedList<>();

        if (propertyId.isPresent()) {
            long id = propertyId.get();
            Building building = buildingService.getBuildingByPropertyId(id);
            Property property = building.getProperties().map(it -> it.size() > 0 ? it.get(0) : null).orElse(propertyService.getPropertyById(id));
            ListingSkeletal minListing = getMinListing(building, property);
            if (minListing != null) {
                listings.add(minListing);
            }
        } else {
            return getListingSkeletalFromPropertyList(propertyService.getAllProperties());

        }

        return listings;
    }

    private List<ListingSkeletal> getListingSkeletalFromPropertyList(List<Property> properties) {

        List<ListingSkeletal> listings = new LinkedList<>();

        if (properties != null) {

            CountDownLatch latch = new CountDownLatch(properties.size());

            for (Property p : properties) {

                if (p.getId().isPresent()) {
                    Long pid = p.getId().get();

                    new Thread(() -> {
                        Building building = buildingService.getBuildingByPropertyId(pid);
                        ListingSkeletal listingSkeletal = getMinListing(building, p);
                        if (listingSkeletal != null) listings.add(listingSkeletal);
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

    private ListingSkeletal getMinListing(Building building, Property property) {

        if (building == null || property == null) return null;

        ListingSkeletal p = new ListingSkeletal();

        building.getLatitude().ifPresent(p::setLatitude);
        building.getLongitude().ifPresent(p::setLongitude);
        building.getAddressBuildingName().ifPresent(p::setAddressBuildingName);
        building.getAddressLocalityName().ifPresent(p::setAddressLocalityName);
        building.getAddressSubdivision().ifPresent(p::setAddressSubdivision);
        building.getAddressCity().ifPresent(p::setAddressCity);
        building.getAddressState().ifPresent(p::setAddressState);
        building.getAddressPinCode().ifPresent(p::setAddressPinCode);

        property.getId().ifPresent(p::setId);
        property.getRooms().ifPresent(rooms -> p.setBedrooms(rooms.size()));
        property.getPropertyType().ifPresent(p::setPropertyType);
        property.getFurnishingType().ifPresent(p::setFurnishing);
        property.getArea().ifPresent(p::setArea);
        property.getRent().ifPresent(p::setRent);
        property.getRoommates().ifPresent(p::setRoommates);

        return p;

    }

    public ResponseEntity<?> getFilteredListing(Listing.Filter filter, Optional<Boolean> min) {

        List<ListingSkeletal> skeletals = new LinkedList<>();

        List<Building> buildings = buildingService.runFilteredQuery(filter);

        if (buildings != null) {

            CountDownLatch latch = new CountDownLatch(buildings.size());

            buildings.forEach(building -> {
                new Thread(() -> {
                    // Get all filtered properties of this building
                    building.getProperties().ifPresentOrElse(it -> {
                        List<ListingSkeletal> listingSkeletals = getListingSkeletalFromPropertyList(it);
                        skeletals.addAll(listingSkeletals);
                        latch.countDown();
                    }, latch::countDown);
                }).start();
            });

            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        return new ResponseEntity<>(skeletals, HttpStatus.OK);
    }

}
