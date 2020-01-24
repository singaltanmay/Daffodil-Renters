package com.daffodil.renters.core.model.beans.postables;

import com.daffodil.renters.core.model.entities.ParkingSpotEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParkingSpot {

    @Getter
    @Setter
    private Optional<Long> id;
    @Getter
    @Setter
    private Optional<Boolean> electric;
    @Getter
    @Setter
    private Optional<ParkingSpotEntity.PARKING_SIZE> parkingSize;
    @Getter
    @Setter
    private Optional<ParkingSpotEntity.PARKING_TYPE> parkingType;
    @Getter
    @Setter
    private Optional<Integer> price;
    @Getter
    @Setter
    private Optional<Building> building;
    @Getter
    @Setter
    private Optional<Property> property;
    @Getter
    @Setter
    private Optional<Occupant> occupant;

    public ParkingSpot(Optional<Long> id, Optional<Boolean> electric, Optional<ParkingSpotEntity.PARKING_SIZE> parkingSize, Optional<ParkingSpotEntity.PARKING_TYPE> parkingType, Optional<Integer> price, Optional<Building> building, Optional<Property> property, Optional<Occupant> occupant) {
        this.id = id;
        this.electric = electric;
        this.parkingSize = parkingSize;
        this.parkingType = parkingType;
        this.price = price;
        this.building = building;
        this.property = property;
        this.occupant = occupant;
    }
}
