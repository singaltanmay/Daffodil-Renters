package com.daffodil.renters.core.model.beans.postables;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Building {

    @Getter
    @Setter
    private Optional<Long> id;

    @Getter
    @Setter
    private Optional<String> addressBuildingName;

    @Getter
    @Setter
    private Optional<String> addressLocalityName;

    @Getter
    @Setter
    private Optional<String> addressSubdivision;

    @Getter
    @Setter
    private Optional<String> addressCity;

    @Getter
    @Setter
    private Optional<String> addressState;

    @Getter
    @Setter
    private Optional<String> addressPinCode;

    @Getter
    @Setter
    private Optional<Date> buildingConstructed;

    @Getter
    @Setter
    private Optional<Double> latitude;

    @Getter
    @Setter
    private Optional<Double> longitude;

    @Getter
    private Optional<List<Property>> properties;

    @Getter
    private Optional<List<ParkingSpot>> sharedParkingSpots;

}
