package com.daffodil.renters.core.model.beans;

import com.daffodil.renters.core.model.beans.postables.Amenities;
import com.daffodil.renters.core.model.beans.postables.ParkingSpot;
import com.daffodil.renters.core.model.beans.postables.Seller;

import java.util.Date;
import java.util.List;

public class Listing extends ListingSkeletal {

    String description;
    Amenities amenities;
    Seller seller;
    long securityDeposit;
    long brokerage;
    int ageOfProperty;
    int lockInPeriod;
    Date listedOn;
    List<ParkingSpot> parkingSpots;

    public static class Filter extends ListingSkeletal.Filter {

        public Filter() {
        }
    }

}
