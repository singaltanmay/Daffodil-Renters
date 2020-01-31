package com.daffodil.renters.core.service.pooledactions.listingcreator;

import com.daffodil.renters.core.model.beans.ListingSkeletal;
import com.daffodil.renters.core.model.beans.postables.Building;
import com.daffodil.renters.core.model.beans.postables.Property;

import java.util.concurrent.RecursiveTask;

/**
 * Creates a skeletal listing from a property (containing parent building)
 */
class MinListingFromPropertyCreator extends RecursiveTask<ListingSkeletal> {

    private Property property;

    public MinListingFromPropertyCreator(Property property) {
        this.property = property;
    }

    @Override
    protected ListingSkeletal compute() {

        if (property == null || property.getBuilding().isEmpty()) return null;

        ListingSkeletal p = new ListingSkeletal();

        Building building = property.getBuilding().get();

        building.getLatitude().ifPresent(p::setLatitude);
        building.getLongitude().ifPresent(p::setLongitude);
        building.getAddressBuildingName().ifPresent(p::setAddressBuildingName);
        building.getAddressLocalityName().ifPresent(p::setAddressLocalityName);
        building.getAddressSubdivision().ifPresent(p::setAddressSubdivision);
        building.getAddressCity().ifPresent(p::setAddressCity);
        building.getAddressState().ifPresent(p::setAddressState);
        building.getAddressPinCode().ifPresent(p::setAddressPinCode);

        property.getId().ifPresent(p::setPropertyId);
        property.getRooms().ifPresent(rooms -> p.setBedrooms(rooms.size()));
        property.getPropertyType().ifPresent(p::setPropertyType);
        property.getFurnishingType().ifPresent(p::setFurnishing);
        property.getArea().ifPresent(p::setArea);
        property.getRent().ifPresent(p::setRent);
        property.getRoommates().ifPresent(p::setRoommates);

        return p;

    }
}