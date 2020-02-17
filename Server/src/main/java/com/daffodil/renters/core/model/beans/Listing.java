package com.daffodil.renters.core.model.beans;

import com.daffodil.renters.core.model.beans.postables.Amenities;
import com.daffodil.renters.core.model.beans.postables.ParkingSpot;
import com.daffodil.renters.core.model.beans.postables.Seller;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Listing extends ListingSkeletal {

    @Getter
    @Setter
    String description;
    @Getter
    @Setter
    Amenities amenities;
    @Getter
    @Setter
    Seller seller;
    @Getter
    @Setter
    long securityDeposit;
    @Getter
    @Setter
    long brokerage;
    @Getter
    @Setter
    int ageOfPropertyMonths;
    @Getter
    @Setter
    int lockInPeriod;
    @Getter
    @Setter
    Date listedOn;
    @Getter
    @Setter
    List<ParkingSpot> sharedParkingSpots;

    public static class Filter extends ListingSkeletal.Filter {

        public Filter() {
        }
    }

}
