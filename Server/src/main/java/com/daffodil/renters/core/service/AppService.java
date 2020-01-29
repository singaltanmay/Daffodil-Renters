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

    public void createListing(Building building) {
        buildingService.insertBuilding(building);
    }

    public ResponseEntity<?> getListing(Optional<Long> propertyId, Optional<Boolean> min, Optional<Integer> page) {

        if (min.isPresent() && min.get()) {
            List<ListingSkeletal> listingSkeletalList = getMinSkeletalListings(propertyId, page);
            if (listingSkeletalList != null) {
                return new ResponseEntity<>(listingSkeletalList, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    public List<ListingSkeletal> getMinSkeletalListings(Optional<Long> propertyId, Optional<Integer> page) {
        List<ListingSkeletal> listings = new LinkedList<>();

        if (propertyId.isPresent()) {
            long id = propertyId.get();
            Building building = buildingService.getBuildingByPropertyId(id);
            Property property = propertyService.getPropertyById(id);
            ListingSkeletal minListing = getMinListing(building, property);
            if (minListing != null) {
                listings.add(minListing);
            }
        } else {
            List<Property> properties = propertyService.getAllProperties();
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
        }

        return listings;
    }

    public ListingSkeletal getMinListing(Building building, Property property) {

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
}
