package com.daffodil.renters.core.model.beans;

import com.daffodil.renters.core.model.entities.HouseEntity;
import com.daffodil.renters.core.model.entities.RoomEntity;

import java.util.LinkedList;
import java.util.List;

public class House {

    long id;
    private String address;
    private double latitude;
    private double longitude;
    List<Room> rooms;

    public House(long id, String address, double latitude, double longitude, List<Room> rooms) {
        this.id = id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rooms = rooms;
    }

    public House(Builder builder) {
        this.id = builder.id;
        this.address = builder.address;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.rooms = builder.rooms;
    }

    public static class Builder {
        long id;
        private String address;
        private double latitude;
        private double longitude;
        List<Room> rooms;

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

        public Builder setRooms(List<Room> rooms) {
            this.rooms = rooms;
            return this;
        }

        public House build() {
            return new House(this);
        }

        public House build(HouseEntity entity) {
            this.id = entity.getId();
            this.address = entity.getAddress();
            this.latitude = entity.getLatitude();
            this.longitude = entity.getLongitude();

            LinkedList<Room> rooms = new LinkedList<>();
            List<RoomEntity> entityRooms = entity.getRooms();
            for (RoomEntity entity1 : entityRooms) {
                rooms.add((new Room.Builder()).build(entity1));
            }
            this.rooms = rooms;
            return new House(this);
        }

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

    public List<Room> getRooms() {
        return rooms;
    }

    public House setRooms(List<Room> rooms) {
        this.rooms = rooms;
        return this;
    }

}
