package com.daffodil.renters.core.model.beans.postables;

import com.daffodil.renters.core.model.entities.PropertyEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Property {

    @Getter
    @Setter
    private Optional<Long> id;

    @Getter
    @Setter
    private Optional<String> description;

    @Getter
    @Setter
    private Optional<PropertyEntity.PROPERTY_TYPE> propertyType;

    @Getter
    @Setter
    private Optional<PropertyEntity.FURNISHING_TYPE> furnishingType;

    @Getter
    @Setter
    private Optional<Integer> area;

    @Getter
    @Setter
    private Optional<Long> rent;

    @Getter
    @Setter
    private Optional<Boolean> roommates;

    @Getter
    @Setter
    private Optional<Long> securityDeposit;

    @Getter
    @Setter
    private Optional<Long> brokerage;

    @Getter
    @Setter
    private Optional<Integer> lockInPeriod;

    @Getter
    @Setter
    private Optional<Date> listedOn;

    @Getter
    @Setter
    private Optional<Building> building;

    @Getter
    @Setter
    private Optional<Seller> seller;

    @Getter
    @Setter
    private Optional<Amenities> amenities;

    @Getter
    @Setter
    private Optional<List<Room>> rooms;

    @Getter
    @Setter
    private Optional<List<ParkingSpot>> parkingSpots;

    public Property(Optional<Long> id, Optional<String> description, Optional<PropertyEntity.PROPERTY_TYPE> propertyType, Optional<PropertyEntity.FURNISHING_TYPE> furnishingType, Optional<Integer> area, Optional<Long> rent, Optional<Boolean> roommates, Optional<Long> securityDeposit, Optional<Long> brokerage, Optional<Integer> lockInPeriod, Optional<Date> listedOn, Optional<Building> building, Optional<Seller> seller, Optional<Amenities> amenities, Optional<List<Room>> rooms, Optional<List<ParkingSpot>> parkingSpots) {
        this.id = id;
        this.description = description;
        this.propertyType = propertyType;
        this.furnishingType = furnishingType;
        this.area = area;
        this.rent = rent;
        this.roommates = roommates;
        this.securityDeposit = securityDeposit;
        this.brokerage = brokerage;
        this.lockInPeriod = lockInPeriod;
        this.listedOn = listedOn;
        this.building = building;
        this.seller = seller;
        this.amenities = amenities;
        this.rooms = rooms;
        this.parkingSpots = parkingSpots;
    }
}
