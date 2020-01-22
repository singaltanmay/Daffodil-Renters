package com.daffodil.renters.core.model.beans;

import java.util.Date;
import java.util.List;

public class Property extends PropertySkeletal {

    String description;
    Amenities amenities;
    Seller seller;
    long securityDeposit;
    long brokerage;
    int ageOfProperty;
    int lockInPeriod;
    Date listedOn;
    List<ParkingSpot> parkingSpots;

}
