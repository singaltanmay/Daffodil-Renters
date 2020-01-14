package com.daffodil.renters.core.model.entities;

import com.daffodil.renters.core.model.beans.ParkingSpot;
import com.daffodil.renters.core.repo.OccupantRepository;
import com.daffodil.renters.core.repo.RoomRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "parking_spot")
public class ParkingSpotEntity {


    public enum PARKING_SIZE {
        BICYCLE,
        SCOOTER,
        CAR,
        MINI_TRUCK;
    }


    public enum PARKING_TYPE {
        GENERAL,
        RESERVED,
        HANDICAPPED,
        EMERGENCY_VEHICLE;
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
        PARKING_SIZE parkingSize = builder.parkingSize;
        if (parkingSize != null) {
            this.parkingSize = parkingSize;
        }
        PARKING_TYPE parkingType = builder.parkingType;
        if (parkingType != null) {
            this.parkingType = parkingType;
        }
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

        public ParkingSpotEntity build(ParkingSpot parkingSpot) {
            if (parkingSpot == null) return null;
            this.id = parkingSpot.getId();
            this.electric = parkingSpot.isElectric();
            this.parkingSize = parkingSpot.getParkingSize();
            this.parkingType = parkingSpot.getParkingType();
            this.price = parkingSpot.getPrice();
            this.houseEntity = new HouseEntity.Builder().build(parkingSpot.getHouse());
            this.occupantEntity = new OccupantEntity.Builder().build(parkingSpot.getOccupant());
            return new ParkingSpotEntity(this);
        }


    }

    public static List<ParkingSpotEntity> listFrom(Iterable<ParkingSpot> parkingSpots) {
        if (parkingSpots == null) return new LinkedList<>();

        List<ParkingSpotEntity> entities = new LinkedList<>();
        parkingSpots.forEach(parkingSpot -> {
            ParkingSpotEntity parkingSpotEntity = new ParkingSpotEntity.Builder().build(parkingSpot);
            if (parkingSpotEntity != null) {
                entities.add(parkingSpotEntity);
            }
        });

        return entities;
    }

    protected ParkingSpotEntity() {
    }
}
