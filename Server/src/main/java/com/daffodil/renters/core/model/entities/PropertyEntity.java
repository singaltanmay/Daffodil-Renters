package com.daffodil.renters.core.model.entities;

import lombok.Getter;
import lombok.Setter;

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
    @Setter
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
    @Setter
    int area;

    // Rent for new tenant
    // Has to be dynamically calculated if roommates
    @Getter
    @Setter
    long rent;

    @Getter
    @Setter
    boolean roommates;

    @Getter
    @Setter
    private long securityDeposit;

    @Getter
    @Setter
    private long brokerage;

    @Getter
    @Setter
    private int lockInPeriod;

    @Getter
    @Setter
    private Date listedOn;

    // Parents
    // Building of which this property is a part of
    @Getter
    @Setter
    @ManyToOne
    BuildingEntity building;
    // Seller of the builder - Broker or Owner
    @Getter
    @Setter
    @ManyToOne
    private SellerEntity seller;

    @Getter
    @Setter
    @ManyToOne
    private AmenitiesEntity amenities;

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

    public PropertyEntity(long id, String description, PROPERTY_TYPE propertyType, FURNISHING_TYPE furnishingType, int area, long rent, boolean roommates, long securityDeposit, long brokerage, int lockInPeriod, Date listedOn, BuildingEntity building, SellerEntity seller, AmenitiesEntity amenities, List<RoomEntity> rooms, List<ParkingSpotEntity> parkingSpots) {
        this.id = id;
        this.description = description;
        this.propertyType = propertyType;
        this.furnishingType = furnishingType;
        this.area = area;
        this.rent = rent;
        this.roommates = roommates;
        this.securityDeposit = securityDeposit;
        this.brokerage = brokerage;
        this.lockInPeriod = lockInPeriod;
        this.listedOn = listedOn;
        this.building = building;
        this.seller = seller;
        this.amenities = amenities;
        setRooms(rooms);
        setParkingSpots(parkingSpots);
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
