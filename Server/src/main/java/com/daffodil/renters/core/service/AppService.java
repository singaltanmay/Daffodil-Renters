package com.daffodil.renters.core.service;

import com.daffodil.renters.core.model.beans.ListingSkeletal;
import com.daffodil.renters.core.model.beans.postables.Building;
import com.daffodil.renters.core.model.beans.postables.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public ResponseEntity<?> getAllListings(Optional<Long> property_id, Optional<Boolean> min, Optional<Integer> page) {

        if (property_id.isPresent()) {
            if (min.isPresent() && min.get()) {
                Property property = propertyService.getPropertyById(property_id.get());
                if (property != null) {
                    Building building = buildingService.getBuildingByPropertyId(property_id.get());
                    return new ResponseEntity<>(getMinListing(building, property), HttpStatus.OK);
                }
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    public ListingSkeletal getMinListing(Building building, Property property) {

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
