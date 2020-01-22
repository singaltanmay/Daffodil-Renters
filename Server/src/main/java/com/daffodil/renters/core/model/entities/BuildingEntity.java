package com.daffodil.renters.core.model.entities;

import lombok.Getter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

public class BuildingEntity {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    // Composite Address
    // Building name/number with floor number, apartment no etc
    @Getter
    private String addressBuildingName;
    // Name of society/colony/apartment complex
    @Getter
    private String addressLocalityName;
    // Sector or other subdivision
    @Getter
    private String addressSubdivision;
    @Getter
    private String addressCity;
    @Getter
    private String addressState;
    @Getter
    private String addressPinCode;

    // Used to calculate the age of the building
    @Getter
    Date buildingConstructed;

    //Coordinates
    @Getter
    private double latitude;
    @Getter
    private double longitude;

    // Children
    @Getter
    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    List<PropertyEntity> properties;

    // Parking spots common to everyone in the building
    @Getter
    List<ParkingSpotEntity> sharedParkingSpots;

    private void mapAllProperties() {
        final List<PropertyEntity> properties = this.properties;
        new Thread(() -> {
            if (properties != null) {
                properties.forEach(BuildingEntity.this::mapProperty);
            }
        }).start();
    }

    private void mapAllSharedParkingSpots() {
        final List<ParkingSpotEntity> parkingSpots = this.sharedParkingSpots;
        new Thread(() -> {
            if (parkingSpots != null) {
                parkingSpots.forEach(BuildingEntity.this::mapSharedParkingSpot);
            }
        }).start();
    }

    private void mapProperty(PropertyEntity propertyEntity) {
        if (propertyEntity != null) {
            propertyEntity.setBuilding(BuildingEntity.this);
        }
    }

    private void mapSharedParkingSpot(ParkingSpotEntity parkingSpotEntity) {
        if (parkingSpotEntity != null) {
            parkingSpotEntity.setBuilding(BuildingEntity.this);
        }
    }

    protected BuildingEntity() {
    }

    // Min constructor
    public BuildingEntity(String addressBuildingName, double latitude, double longitude) {
        this.addressBuildingName = addressBuildingName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Setters
    public BuildingEntity setId(long id) {
        this.id = id;
        return this;
    }

    public BuildingEntity setAddressBuildingName(String addressBuildingName) {
        this.addressBuildingName = addressBuildingName;
        return this;
    }

    public BuildingEntity setAddressLocalityName(String addressLocalityName) {
        this.addressLocalityName = addressLocalityName;
        return this;
    }

    public BuildingEntity setAddressSubdivision(String addressSubdivision) {
        this.addressSubdivision = addressSubdivision;
        return this;
    }

    public BuildingEntity setAddressCity(String addressCity) {
        this.addressCity = addressCity;
        return this;
    }

    public BuildingEntity setAddressState(String addressState) {
        this.addressState = addressState;
        return this;
    }

    public BuildingEntity setAddressPinCode(String addressPinCode) {
        this.addressPinCode = addressPinCode;
        return this;
    }

    public BuildingEntity setBuildingConstructed(Date buildingConstructed) {
        this.buildingConstructed = buildingConstructed;
        return this;
    }

    public BuildingEntity setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public BuildingEntity setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public BuildingEntity setProperties(List<PropertyEntity> properties) {
        this.properties = properties;
        mapAllProperties();
        return this;
    }

    public BuildingEntity setSharedParkingSpots(List<ParkingSpotEntity> sharedParkingSpots) {
        this.sharedParkingSpots = sharedParkingSpots;
        mapAllSharedParkingSpots();
        return this;
    }
}
