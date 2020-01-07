package com.daffodil.renters.core.Insertables;

import com.daffodil.renters.core.model.Room;

import java.util.List;

public class HouseInsertable {

    private String address;
    private double latitude;
    private double longitude;
    List<Room> rooms;

    public static class Builder {

        private String address;
        private double latitude;
        private double longitude;
        List<Room> rooms;

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

        public HouseInsertable build() {
            return new HouseInsertable(this);
        }

    }

    public HouseInsertable(Builder builder) {
        this.address = builder.address;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.rooms = builder.rooms;
    }

    public String getAddress() {
        return address;
    }

    public HouseInsertable setAddress(String address) {
        this.address = address;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public HouseInsertable setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public HouseInsertable setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public HouseInsertable setRooms(List<Room> rooms) {
        this.rooms = rooms;
        return this;
    }
}
