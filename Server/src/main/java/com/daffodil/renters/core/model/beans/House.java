package com.daffodil.renters.core.model.beans;

import com.daffodil.renters.core.model.entities.HouseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class House {

    @Getter
    @Setter
    private long id;
    @Getter
    @Setter
    private String address;
    @Getter
    @Setter
    private double latitude;
    @Getter
    @Setter
    private double longitude;
    @Getter
    @Setter
    List<Room> rooms;

    public static class Filter {
        @Getter
        @Setter
        private Optional<Long> id = Optional.empty();
        @Getter
        @Setter
        private Optional<String> address = Optional.empty();
        @Getter
        @Setter
        private Optional<Double> latitude = Optional.empty();
        @Getter
        @Setter
        private Optional<Double> longitude = Optional.empty();
        @Getter
        @Setter
        private Optional<Double> rangeKm = Optional.empty();

        public boolean isUnfiltered() {
            return id.isEmpty() && address.isEmpty() && latitude.isEmpty() && longitude.isEmpty() && rangeKm.isEmpty();
        }
    }

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
            if (entity == null) return null;
            this.id = entity.getId();
            this.address = entity.getAddress();
            this.latitude = entity.getLatitude();
            this.longitude = entity.getLongitude();
            return new House(this);
        }

    }

    public static List<House> listFrom(Iterable<HouseEntity> entities) {
        List<House> houses = new LinkedList<>();
        if (entities != null) {
            for (HouseEntity entity : entities) {
                House build = new House.Builder().build(entity);
                houses.add(build);
            }
        }
        return houses;
    }

}
