package com.daffodil.renters.core.model.entities;

import lombok.Getter;

import javax.persistence.*;

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
    private PropertyEntity property;

    @Getter
    @ManyToOne
    private OccupantEntity occupant;

    public ParkingSpotEntity(PropertyEntity property) {
        this.property = property;
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

    public ParkingSpotEntity setProperty(PropertyEntity property) {
        this.property = property;
        return this;
    }

    public ParkingSpotEntity setOccupant(OccupantEntity occupant) {
        this.occupant = occupant;
        return this;
    }
}
