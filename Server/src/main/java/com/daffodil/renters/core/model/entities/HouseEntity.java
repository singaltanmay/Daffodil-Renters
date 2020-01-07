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
    List<RoomEntity> rooms;

    public HouseEntity(String address, double latitude, double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public HouseEntity(long id, String address, double latitude, double longitude, List<RoomEntity> rooms) {
        this.id = id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rooms = rooms;
    }

    protected HouseEntity() {
    }

    private HouseEntity(Builder builder) {
        this.id = builder.id;
        this.address = builder.address;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.rooms = builder.rooms;
    }

    public static class Builder {
        private long id;
        private String address;
        private double latitude;
        private double longitude;
        List<RoomEntity> rooms;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder setRooms(List<RoomEntity> rooms) {
            this.rooms = rooms;
            return this;
        }

        public HouseEntity build() {
            return new HouseEntity(this);
        }

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

    public List<RoomEntity> getRooms() {
        return rooms;
    }
}