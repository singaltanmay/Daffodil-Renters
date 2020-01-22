package com.daffodil.renters.core.model.entities;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

public class AmenitiesEntity {


    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

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

    @Getter
    @OneToMany(mappedBy = "amenities")
    private List<PropertyEntity> properties;


    private void mapAllProperties() {
        List<PropertyEntity> properties = this.properties;
        new Thread(() -> {
            if (properties != null) {
                properties.forEach(AmenitiesEntity.this::mapProperty);
            }
        }).start();
    }

    private void mapProperty(PropertyEntity entity) {
        if (entity != null) {
            entity.setAmenities(AmenitiesEntity.this);
        }
    }

    protected AmenitiesEntity() {
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

    public AmenitiesEntity setProperties(List<PropertyEntity> properties) {
        this.properties = properties;
        mapAllProperties();
        return this;
    }

}