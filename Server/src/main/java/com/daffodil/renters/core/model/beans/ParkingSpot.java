package com.daffodil.renters.core.model.beans;

import com.daffodil.renters.core.model.entities.ParkingSpotEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParkingSpot {

    @Getter
    @Setter
    private long id;
    @Getter
    @Setter
    private boolean electric;
    @Getter
    @Setter
    private ParkingSpotEntity.PARKING_SIZE parkingSize;
    @Getter
    @Setter
    private ParkingSpotEntity.PARKING_TYPE parkingType;
    @Getter
    @Setter
    private int price;
    @Getter
    @Setter
    private House house;
    @Getter
    @Setter
    private Occupant occupant;

    public ParkingSpot(long id, boolean electric, ParkingSpotEntity.PARKING_SIZE parkingSize, ParkingSpotEntity.PARKING_TYPE parkingType, int price, House house, Occupant occupant) {
        this.id = id;
        this.electric = electric;
        this.parkingSize = parkingSize;
        this.parkingType = parkingType;
        this.price = price;
        this.house = house;
        this.occupant = occupant;
    }

    public ParkingSpot(Builder builder) {
        this.id = builder.id;
        this.electric = builder.electric;
        this.parkingSize = builder.parkingSize;
        this.parkingType = builder.parkingType;
        this.price = builder.price;
    }

    public static class Builder {
        private long id;
        private boolean electric;
        private ParkingSpotEntity.PARKING_SIZE parkingSize;
        private ParkingSpotEntity.PARKING_TYPE parkingType;
        private int price;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setElectric(boolean electric) {
            this.electric = electric;
            return this;
        }

        public Builder setParkingSize(ParkingSpotEntity.PARKING_SIZE parkingSize) {
            this.parkingSize = parkingSize;
            return this;
        }

        public Builder setParkingType(ParkingSpotEntity.PARKING_TYPE parkingType) {
            this.parkingType = parkingType;
            return this;
        }

        public Builder setPrice(int price) {
            this.price = price;
            return this;
        }

        public ParkingSpot build() {
            return new ParkingSpot(this);
        }

        public ParkingSpot build(ParkingSpotEntity entity) {
            if (entity == null) return null;
            this.id = entity.getId();
            this.electric = entity.isElectric();
            this.parkingSize = entity.getParkingSize();
            this.parkingType = entity.getParkingType();
            this.price = entity.getPrice();
            return new ParkingSpot(this);
        }

    }

    public static List<ParkingSpot> listFrom(Iterable<ParkingSpotEntity> entities) {
        List<ParkingSpot> parkingSpots = new LinkedList<>();
        if (entities != null) {
            for (ParkingSpotEntity entity : entities) {
                ParkingSpot build = new ParkingSpot.Builder().build(entity);
                parkingSpots.add(build);
            }
        }
        return parkingSpots;
    }

}
