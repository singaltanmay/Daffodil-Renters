package com.daffodil.renters.core.service.pooledactions.listingcreator;

import com.daffodil.renters.core.model.beans.Listing;
import com.daffodil.renters.core.model.beans.postables.Building;
import com.daffodil.renters.core.model.beans.postables.Property;
import com.daffodil.renters.core.service.GeoLocationService;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.RecursiveTask;

/**
 * Creates a listing from a property (containing parent building)
 */
public class ListingFromPropertyCreator extends RecursiveTask<Listing> {

    private Property property;

    public ListingFromPropertyCreator(Property property) {
        this.property = property;
    }


    @Override
    protected Listing compute() {

        if (property == null || property.getBuilding().isEmpty()) return null;

        Listing listing = new Listing();

        Building building = property.getBuilding().get();

        Optional<Double> latitude = building.getLatitude();
        latitude.ifPresent(listing::setLatitude);
        Optional<Double> longitude = building.getLongitude();
        longitude.ifPresent(listing::setLongitude);
        building.getAddressBuildingName().ifPresent(listing::setAddressBuildingName);
        building.getAddressLocalityName().ifPresent(listing::setAddressLocalityName);
        building.getAddressSubdivision().ifPresent(listing::setAddressSubdivision);
        building.getAddressCity().ifPresent(listing::setAddressCity);
        building.getAddressState().ifPresent(listing::setAddressState);
        building.getAddressPinCode().ifPresent(listing::setAddressPinCode);

        property.getId().ifPresent(listing::setPropertyId);
        property.getRooms().ifPresent(rooms -> listing.setBedrooms(rooms.size()));
        property.getPropertyType().ifPresent(listing::setPropertyType);
        property.getFurnishingType().ifPresent(listing::setFurnishing);
        property.getArea().ifPresent(listing::setArea);
        property.getRent().ifPresent(listing::setRent);
        property.getRoommates().ifPresent(listing::setRoommates);

        if (latitude.isPresent() && longitude.isPresent()) {
            listing.setDistanceKm(GeoLocationService.fromDegrees(latitude.get(), longitude.get()).distanceTo(GeoLocationService.fromDegrees(28.465082, 77.056162), null));
        }

        property.getDescription().ifPresent(listing::setDescription);
        property.getAmenities().ifPresent(it -> {
            it.setProperties(Optional.empty());
            listing.setAmenities(it);
        });
        property.getSeller().ifPresent(seller -> {
            seller.setProperties(Optional.empty());
            listing.setSeller(seller);
        });
        property.getSecurityDeposit().ifPresent(listing::setSecurityDeposit);
        property.getBrokerage().ifPresent(listing::setBrokerage);
        building.getBuildingConstructed().ifPresent(date -> {
            Date now = new Date();
            long timePassed = now.getTime() - date.getTime();
            int diffMonths = (int) (timePassed / (1000 * 60 * 60 * 24 * 12));
            listing.setAgeOfPropertyMonths(diffMonths);
        });
        property.getLockInPeriod().ifPresent(listing::setLockInPeriod);
        property.getListedOn().ifPresent(listing::setListedOn);
        building.getSharedParkingSpots().ifPresent(listing::setSharedParkingSpots);

        return listing;

    }
}
