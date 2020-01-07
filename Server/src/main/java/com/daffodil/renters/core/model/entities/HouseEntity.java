package com.daffodil.renters.core.model.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "house")
public class HouseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private String address;

    //Coordinates
    private double latitude;
    private double longitude;

    // Children
    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL)
    List<RoomEntity> roomEntities;

    public HouseEntity(String address, double latitude, double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public HouseEntity(long id, String address, double latitude, double longitude, List<RoomEntity> roomEntities) {
        this.id = id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.roomEntities = roomEntities;
    }

    protected HouseEntity() {
    }

    public HouseEntity setRoomEntities(List<RoomEntity> roomEntities) {
        this.roomEntities = roomEntities;
        return this;
    }

    public long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public List<RoomEntity> getRoomEntities() {
        return roomEntities;
    }
}
