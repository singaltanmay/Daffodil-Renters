package com.daffodil.renters.core.model.entities;

import com.daffodil.renters.core.model.beans.ParkingSpot;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "parking_spot")
public class ParkingSpotEntity {

    public enum PARKING_SIZE {
        BICYCLE,
        SCOOTER,
        CAR,
        MINI_TRUCK
    }

    public enum PARKING_TYPE {
        GENERAL,
        RESERVED,
        HANDICAPPED,
        EMERGENCY_VEHICLES
    }

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Getter
    @Setter
    private boolean electric;

    @Getter
    @Setter
    @Enumerated
    @Column(columnDefinition = "smallint")
    private PARKING_SIZE parkingSize = PARKING_SIZE.CAR;

    @Getter
    @Setter
    @Enumerated
    @Column(columnDefinition = "smallint")
    private PARKING_TYPE parkingType = PARKING_TYPE.GENERAL;

    @Getter
    @Setter
    private int price;

    @Getter
    @Setter
    @ManyToOne
    private HouseEntity house;

    @Getter
    @Setter
    @ManyToOne
    private OccupantEntity occupant;

    public ParkingSpotEntity(HouseEntity house) {
        this.house = house;
    }

    public ParkingSpotEntity(Builder builder) {
        this.id = builder.id;
        this.electric = builder.electric;
        this.parkingSize = builder.parkingSize;
        this.parkingType = builder.parkingType;
        this.price = builder.price;
        this.house = builder.houseEntity;
        this.occupant = builder.occupantEntity;
    }

    public static class Builder {
        private long id;
        private boolean electric;
        private PARKING_SIZE parkingSize;
        private PARKING_TYPE parkingType;
        private int price;
        private HouseEntity houseEntity;
        private OccupantEntity occupantEntity;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setElectric(boolean electric) {
            this.electric = electric;
            return this;
        }

        public Builder setParkingSize(PARKING_SIZE parkingSize) {
            this.parkingSize = parkingSize;
            return this;
        }

        public Builder setParkingType(PARKING_TYPE parkingType) {
            this.parkingType = parkingType;
            return this;
        }

        public Builder setPrice(int price) {
            this.price = price;
            return this;
        }

        public Builder setHouseEntity(HouseEntity houseEntity) {
            this.houseEntity = houseEntity;
            return this;
        }

        public Builder setOccupantEntity(OccupantEntity occupantEntity) {
            this.occupantEntity = occupantEntity;
            return this;
        }

        public ParkingSpotEntity build() {
            return new ParkingSpotEntity(this);
        }

        public ParkingSpotEntity build(ParkingSpot ps) {
            if (ps == null) return null;
            this.id = ps.getId();
            this.electric = ps.isElectric();
            this.parkingSize = ps.getParkingSize();
            this.parkingType = ps.getParkingType();
            this.price = ps.getPrice();
            this.houseEntity = new HouseEntity.Builder().build(ps.getHouse());
            this.occupantEntity = new OccupantEntity.Builder().build(ps.getOccupant());
            return new ParkingSpotEntity(this);
        }

    }

    protected ParkingSpotEntity() {
    }
}
