package com.daffodil.renters.core.model.entities;

import lombok.Getter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "property")
public class PropertyEntity {

    public enum PROPERTY_TYPE {
        APARTMENT,
        INDEPENDENT_FLOOR,
        INDEPENDENT_HOUSE,
        PG
    }

    public enum FURNISHING_TYPE {
        FULLY_FURNISHED,
        SEMI_FURNISHED,
        UNFURNISHED
    }

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    @Getter
    private String description;

    @Getter
    @Enumerated
    @Column(columnDefinition = "smallint")
    private PROPERTY_TYPE propertyType;

    @Getter
    @Enumerated
    @Column(columnDefinition = "smallint")
    private FURNISHING_TYPE furnishingType;

    @Getter
    int area;

    // Rent for new tenant
    // Has to be dynamically calculated if roommates
    @Getter
    long rent;

    @Getter
    boolean roommates;

    @Getter
    private long securityDeposit;

    @Getter
    private long brokerage;

    @Getter
    private int lockInPeriod;

    @Getter
    private Date listedOn;

    // Parents
    // Building of which this property is a part of
    @Getter
    @ManyToOne
    BuildingEntity building;
    // Seller of the builder - Broker or Owner
    @Getter
    @ManyToOne
    private SellerEntity seller;

    // Children
    @Getter
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    List<RoomEntity> rooms;
    // Reserved parking spots for the listing
    @Getter
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    List<ParkingSpotEntity> parkingSpots;

    // To be run whenever rooms are inserted
    private void mapAllRooms() {
        final List<RoomEntity> rooms = this.rooms;
        new Thread(() -> {
            if (rooms != null) {
                rooms.forEach(PropertyEntity.this::mapRoom);
            }
        }).start();

    }

    private void mapAllParkingSpots() {
        final List<ParkingSpotEntity> parkingSpots = this.parkingSpots;
        new Thread(() -> {
            if (parkingSpots != null) {
                parkingSpots.forEach(PropertyEntity.this::mapParkingSpot);
            }
        }).start();
    }

    // To be run whenever a new room is added
    private void mapRoom(RoomEntity roomEntity) {
        if (roomEntity != null) roomEntity.setProperty(PropertyEntity.this);
    }

    private void mapParkingSpot(ParkingSpotEntity parkingSpotEntity) {
        if (parkingSpotEntity != null) parkingSpotEntity.setProperty(PropertyEntity.this);
    }

    protected PropertyEntity() {
    }

    public PropertyEntity(BuildingEntity building) {
        this.building = building;
    }

    public long getNumberOfRooms() {
        return rooms.size();
    }

    public PropertyEntity setId(long id) {
        this.id = id;
        return this;
    }

    public PropertyEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public PropertyEntity setPropertyType(PROPERTY_TYPE propertyType) {
        this.propertyType = propertyType;
        return this;
    }

    public PropertyEntity setFurnishingType(FURNISHING_TYPE furnishingType) {
        this.furnishingType = furnishingType;
        return this;
    }

    public PropertyEntity setArea(int area) {
        this.area = area;
        return this;
    }

    public PropertyEntity setRent(long rent) {
        this.rent = rent;
        return this;
    }

    public PropertyEntity setRoommates(boolean roommates) {
        this.roommates = roommates;
        return this;
    }

    public PropertyEntity setSecurityDeposit(long securityDeposit) {
        this.securityDeposit = securityDeposit;
        return this;
    }

    public PropertyEntity setBrokerage(long brokerage) {
        this.brokerage = brokerage;
        return this;
    }

    public PropertyEntity setLockInPeriod(int lockInPeriod) {
        this.lockInPeriod = lockInPeriod;
        return this;
    }

    public PropertyEntity setListedOn(Date listedOn) {
        this.listedOn = listedOn;
        return this;
    }

    public PropertyEntity setBuilding(BuildingEntity building) {
        this.building = building;
        return this;
    }

    public PropertyEntity setSeller(SellerEntity seller) {
        this.seller = seller;
        return this;
    }

    public PropertyEntity setRooms(List<RoomEntity> rooms) {
        this.rooms = rooms;
        mapAllParkingSpots();
        return this;
    }

    public PropertyEntity setParkingSpots(List<ParkingSpotEntity> parkingSpots) {
        this.parkingSpots = parkingSpots;
        mapAllParkingSpots();
        return this;
    }
}
