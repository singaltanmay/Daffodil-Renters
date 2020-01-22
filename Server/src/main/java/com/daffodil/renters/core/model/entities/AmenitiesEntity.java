package com.daffodil.renters.core.model.entities;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToOne;

public class AmenitiesEntity {

    // Parent
    @Id
    @Column(nullable = false)
    @OneToOne
    private PropertyEntity property;

    @Getter
    boolean gasPipeline;
    @Getter
    boolean swimmingPool;
    @Getter
    boolean gym;
    @Getter
    boolean lift;
    @Getter
    boolean gatedCommunity;
    @Getter
    boolean parking;
    @Getter
    boolean parkingAllowed;

    protected AmenitiesEntity() {
    }

    public AmenitiesEntity(PropertyEntity property) {
        this.property = property;
    }

    public AmenitiesEntity setProperty(PropertyEntity property) {
        this.property = property;
        return this;
    }

    public AmenitiesEntity setGasPipeline(boolean gasPipeline) {
        this.gasPipeline = gasPipeline;
        return this;
    }

    public AmenitiesEntity setSwimmingPool(boolean swimmingPool) {
        this.swimmingPool = swimmingPool;
        return this;
    }

    public AmenitiesEntity setGym(boolean gym) {
        this.gym = gym;
        return this;
    }

    public AmenitiesEntity setLift(boolean lift) {
        this.lift = lift;
        return this;
    }

    public AmenitiesEntity setGatedCommunity(boolean gatedCommunity) {
        this.gatedCommunity = gatedCommunity;
        return this;
    }

    public AmenitiesEntity setParking(boolean parking) {
        this.parking = parking;
        return this;
    }

    public AmenitiesEntity setParkingAllowed(boolean parkingAllowed) {
        this.parkingAllowed = parkingAllowed;
        return this;
    }
}