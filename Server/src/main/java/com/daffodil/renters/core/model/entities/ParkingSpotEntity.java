package com.daffodil.renters.core.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class ParkingSpotEntity {

    private enum PARKING_SIZE {
        BICYCLE,
        SCOOTER,
        CAR,
        MINI_TRUCK
    }

    private enum PARKING_TYPE {
        GENERAL,
        RESERVED,
        HANDICAPPED,
        EMERGENCY_VEHICLES
    }

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Getter
    @Setter
    private boolean electric;

    @Getter
    @Setter
    @Enumerated
    @Column(columnDefinition = "smallint")
    private PARKING_SIZE parkingSize;

    @Getter
    @Setter
    @Enumerated
    @Column(columnDefinition = "smallint")
    private PARKING_TYPE parkingType;

    @Getter
    @Setter
    private int price;

    @Getter
    @Setter
    @ManyToOne
    private HouseEntity house;

    @Getter
    @Setter
    @ManyToOne
    private OccupantEntity occupant;

    public ParkingSpotEntity() {
    }
}
