package com.daffodil.renters.core.model.beans;

import com.daffodil.renters.core.model.entities.RoomEntity;

import java.util.List;

public class House {

    private String address;
    private double latitude;
    private double longitude;
    List<RoomEntity> roomEntities;

    public static class Builder {

        private String address;
        private double latitude;
        private double longitude;
        List<RoomEntity> roomEntities;

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

        public Builder setRoomEntities(List<RoomEntity> roomEntities) {
            this.roomEntities = roomEntities;
            return this;
        }

        public House build() {
            return new House(this);
        }

    }

    public House(Builder builder) {
        this.address = builder.address;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.roomEntities = builder.roomEntities;
    }

    public String getAddress() {
        return address;
    }

    public House setAddress(String address) {
        this.address = address;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public House setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public House setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public List<RoomEntity> getRoomEntities() {
        return roomEntities;
    }

    public House setRoomEntities(List<RoomEntity> roomEntities) {
        this.roomEntities = roomEntities;
        return this;
    }
}
