package com.daffodil.renters.core.model.beans.postables;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Building {

    @Getter
    @Setter
    private Optional<Long> id;

    @Getter
    @Setter
    @JsonProperty("a_building")
    private Optional<String> addressBuildingName;

    @Getter
    @Setter
    @JsonProperty("a_locality")
    private Optional<String> addressLocalityName;

    @Getter
    @Setter
    @JsonProperty("a_subdiv")
    private Optional<String> addressSubdivision;

    @Getter
    @Setter
    @JsonProperty("a_city")
    private Optional<String> addressCity;

    @Getter
    @Setter
    @JsonProperty("a_state")
    private Optional<String> addressState;

    @Getter
    @Setter
    @JsonProperty("a_pincode")
    private Optional<String> addressPinCode;

    @Getter
    @Setter
    @JsonProperty("constructed")
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
    @JsonProperty("shared_p_spots")
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
