package com.daffodil.renters.core.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

public class AmenitiesEntity {


    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Getter
    @Setter
    boolean gasPipeline;
    @Getter
    @Setter
    boolean swimmingPool;
    @Getter
    @Setter
    boolean gym;
    @Getter
    @Setter
    boolean lift;
    @Getter
    @Setter
    boolean gatedCommunity;
    @Getter
    @Setter
    boolean parking;
    @Getter
    @Setter
    boolean petsAllowed;

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

    public AmenitiesEntity(long id, boolean gasPipeline, boolean swimmingPool, boolean gym, boolean lift, boolean gatedCommunity, boolean parking, boolean petsAllowed, List<PropertyEntity> properties) {
        this.id = id;
        this.gasPipeline = gasPipeline;
        this.swimmingPool = swimmingPool;
        this.gym = gym;
        this.lift = lift;
        this.gatedCommunity = gatedCommunity;
        this.parking = parking;
        this.petsAllowed = petsAllowed;
        this.properties = properties;
    }

    public AmenitiesEntity setProperties(List<PropertyEntity> properties) {
        this.properties = properties;
        mapAllProperties();
        return this;
    }

}