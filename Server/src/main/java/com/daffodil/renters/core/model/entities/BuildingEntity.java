package com.daffodil.renters.core.model.entities;

import lombok.Getter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "building")
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
    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    List<ParkingSpotEntity> parkingSpots;

    public void performPostParentCreationMappings() {
        triggerParkingSpotsPostParentCreationMappings();
        triggerPropertiesPostParentCreationMappings();
    }

    // Set this building for all child parking spots
    private void triggerParkingSpotsPostParentCreationMappings() {
        final List<ParkingSpotEntity> parkingSpots = this.parkingSpots;
        new Thread(() -> {
            if (parkingSpots != null) {
                parkingSpots.forEach(BuildingEntity.this::triggerParkingSpotMappings);
            }
        }).start();
    }

    private void triggerParkingSpotMappings(ParkingSpotEntity parkingSpotEntity) {
        if (parkingSpotEntity != null) {
            parkingSpotEntity.performPostParentCreationMappings(BuildingEntity.this);
        }
    }

    // Set this building for all child properties
    private void triggerPropertiesPostParentCreationMappings() {
        final List<PropertyEntity> properties = this.properties;
        new Thread(() -> {
            if (properties != null) {
                properties.forEach(BuildingEntity.this::triggerPropertyMappings);
            }
        }).start();
    }

    private void triggerPropertyMappings(PropertyEntity propertyEntity) {
        if (propertyEntity != null) {
            propertyEntity.performPostParentCreationMappings(BuildingEntity.this);
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

    public BuildingEntity(long id, String addressBuildingName, String addressLocalityName, String addressSubdivision, String addressCity, String addressState, String addressPinCode, Date buildingConstructed, double latitude, double longitude, List<PropertyEntity> properties, List<ParkingSpotEntity> parkingSpots) {
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
        this.setProperties(properties);
        this.setParkingSpots(parkingSpots);
        performPostParentCreationMappings();
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
        return this;
    }

    public BuildingEntity setParkingSpots(List<ParkingSpotEntity> parkingSpots) {
        this.parkingSpots = parkingSpots;
        return this;
    }

}
