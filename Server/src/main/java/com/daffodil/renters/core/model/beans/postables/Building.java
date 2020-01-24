package com.daffodil.renters.core.model.beans.postables;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
    @Setter
    private Optional<List<Property>> properties;

    @Getter
    @Setter
    private Optional<List<ParkingSpot>> sharedParkingSpots;

    public Building(Optional<Long> id, Optional<String> addressBuildingName, Optional<String> addressLocalityName, Optional<String> addressSubdivision, Optional<String> addressCity, Optional<String> addressState, Optional<String> addressPinCode, Optional<Date> buildingConstructed, Optional<Double> latitude, Optional<Double> longitude, Optional<List<Property>> properties, Optional<List<ParkingSpot>> sharedParkingSpots) {
        this.id = id;
        this.addressBuildingName = addressBuildingName;
        this.addressLocalityName = addressLocalityName;
        this.addressSubdivision = addressSubdivision;
        this.addressCity = addressCity;
        this.addressState = addressState;
        this.addressPinCode = addressPinCode;
        this.buildingConstructed = buildingConstructed;
        this.latitude = latitude;
        this.longitude = longitude;
        this.properties = properties;
        this.sharedParkingSpots = sharedParkingSpots;
    }
}
