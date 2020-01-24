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
    @ManyToOne(cascade = CascadeType.ALL)
    private SellerEntity seller;

    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.ALL)
    private AmenitiesEntity amenities;

    // Children
    @Getter
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    List<RoomEntity> rooms;
    // Reserved parking spots for the listing
    @Getter
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    List<ParkingSpotEntity> parkingSpots;

    public void performPostParentCreationMappings(BuildingEntity buildingEntity) {
        this.building = buildingEntity;
        triggerParkingSpotsPostParentCreationMappings();
        triggerRoomsPostParentCreationMappings();
    }

    // Called by parent after this has been constructed
    // Tells the child root to tell it's child occupants to map it's parking spots to their building
    // Done post construction as now root building has already been constructed
    private void triggerRoomsPostParentCreationMappings() {
        final List<RoomEntity> rooms = this.rooms;
        new Thread(() -> {
            if (rooms != null) {
                rooms.forEach(PropertyEntity.this::triggerRoomMappings);
            }
        }).start();
    }

    private void triggerRoomMappings(RoomEntity entity) {
        if (entity != null) {
            entity.performPostParentCreationMappings(PropertyEntity.this);
        }
    }


    // Called after object construction because even though property is available during construction, building isn't
    private void triggerParkingSpotsPostParentCreationMappings() {
        final List<ParkingSpotEntity> parkingSpots = this.parkingSpots;
        new Thread(() -> {
            if (parkingSpots != null) {
                parkingSpots.forEach(PropertyEntity.this::triggerParkingSpotMappings);
            }
        }).start();
    }

    // Called by parent after object construction
    private void triggerParkingSpotMappings(ParkingSpotEntity parkingSpotEntity) {
        if (parkingSpotEntity != null) {
            parkingSpotEntity.setProperty(PropertyEntity.this);
            parkingSpotEntity.setBuilding(PropertyEntity.this.building);
            parkingSpotEntity.performPostParentCreationMappings(PropertyEntity.this);
        }
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
        this.parkingSpots = parkingSpots;
    }

    public PropertyEntity setRooms(List<RoomEntity> rooms) {
        this.rooms = rooms;
        mapAllRooms();
        return this;
    }

    // Called during object construction
    // To be run whenever rooms are inserted
    private void mapAllRooms() {
        final List<RoomEntity> rooms = this.rooms;
        new Thread(() -> {
            if (rooms != null) {
                long rentShare = PropertyEntity.this.rent / rooms.size();
                rooms.forEach(roomEntity -> PropertyEntity.this.mapRoom(roomEntity, rentShare));
            }
        }).start();

    }

    // Called during object construction
    // Set this property for each room
    // Set rent to all rooms as a fraction of total rent of this property
    private void mapRoom(RoomEntity roomEntity, long roomRent) {
        if (roomEntity != null) {
            roomEntity.setProperty(PropertyEntity.this);
            roomEntity.setRent(roomRent);
        }
    }
}
