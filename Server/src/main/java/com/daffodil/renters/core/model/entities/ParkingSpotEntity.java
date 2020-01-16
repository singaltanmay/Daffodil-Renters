package com.daffodil.renters.core.model.entities;

import com.daffodil.renters.core.model.beans.ParkingSpot;
import lombok.Getter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "parking_spot")
public class ParkingSpotEntity {


    public enum PARKING_SIZE {
        BICYCLE,
        SCOOTER,
        CAR,
        MINI_TRUCK;
    }


    public enum PARKING_TYPE {
        GENERAL,
        RESERVED,
        HANDICAPPED,
        EMERGENCY_VEHICLE;
    }

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Getter
    private boolean electric;

    @Getter
    @Enumerated
    @Column(columnDefinition = "smallint")
    private PARKING_SIZE parkingSize = PARKING_SIZE.CAR;

    @Getter
    @Enumerated
    @Column(columnDefinition = "smallint")
    private PARKING_TYPE parkingType = PARKING_TYPE.GENERAL;

    @Getter
    private int price;

    @Getter
    @ManyToOne
    private HouseEntity house;

    @Getter
    @ManyToOne
    private OccupantEntity occupant;

    public ParkingSpotEntity(HouseEntity house) {
        this.house = house;
    }

    protected ParkingSpotEntity() {
    }

    public ParkingSpotEntity setId(long id) {
        this.id = id;
        return this;
    }

    public ParkingSpotEntity setElectric(boolean electric) {
        this.electric = electric;
        return this;
    }

    public ParkingSpotEntity setParkingSize(PARKING_SIZE parkingSize) {
        if (parkingSize != null) {
            this.parkingSize = parkingSize;
        }
        return this;
    }

    public ParkingSpotEntity setParkingType(PARKING_TYPE parkingType) {
        if (parkingType != null) {
            this.parkingType = parkingType;
        }
        return this;
    }

    public ParkingSpotEntity setPrice(int price) {
        this.price = price;
        return this;
    }

    public ParkingSpotEntity setHouse(HouseEntity house) {
        this.house = house;
        return this;
    }

    public ParkingSpotEntity setOccupant(OccupantEntity occupant) {
        this.occupant = occupant;
        return this;
    }
}
