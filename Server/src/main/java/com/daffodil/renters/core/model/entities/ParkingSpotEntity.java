package com.daffodil.renters.core.model.entities;

import lombok.Getter;
import lombok.Setter;

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
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Getter
    @Setter
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
    @Setter
    private int price;

    @Getter
    @Setter
    @ManyToOne
    private BuildingEntity building;

    @Getter
    @Setter
    @ManyToOne
    private PropertyEntity property;

    @Getter
    @Setter
    @ManyToOne
    private OccupantEntity occupant;

    // Called by building entity on shared parking spots belonging to no property specifically
    public void performPostParentCreationMappings(BuildingEntity entity) {
        this.building = entity;
    }

    // Called by PropertyEntity on parking spots reserved for a property but not for any occupant
    public void performPostParentCreationMappings(PropertyEntity entity) {
        this.property = entity;
        this.building = this.property.getBuilding();
    }

    // Called by OccupantEntity on parking spots rented by owners
    public void performPostParentCreationMappings(OccupantEntity entity) {
        this.occupant = entity;
        this.property = this.occupant.getRoom().getProperty();
        this.building = this.property.getBuilding();
    }

    public ParkingSpotEntity(BuildingEntity building) {
        this.building = building;
    }

    protected ParkingSpotEntity() {
    }

    public ParkingSpotEntity(long id, boolean electric, PARKING_SIZE parkingSize, PARKING_TYPE parkingType, int price, BuildingEntity building, PropertyEntity property, OccupantEntity occupant) {
        this.id = id;
        this.electric = electric;
        setParkingSize(parkingSize);
        setParkingType(parkingType);
        this.price = price;
        this.building = building;
        this.property = property;
        this.occupant = occupant;
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
}
