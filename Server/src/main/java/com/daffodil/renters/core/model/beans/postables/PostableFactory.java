package com.daffodil.renters.core.model.beans.postables;

import com.daffodil.renters.core.model.beans.House;
import com.daffodil.renters.core.model.beans.Occupant;
import com.daffodil.renters.core.model.entities.BuildingEntity;
import com.daffodil.renters.core.model.entities.OccupantEntity;
import com.daffodil.renters.core.model.entities.ParkingSpotEntity;
import com.daffodil.renters.core.model.entities.PropertyEntity;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class PostableFactory {

    public static class BuildingBuilder {
        //TODO
        public static Building build(BuildingEntity building) {
            return null;
        }
    }


    public static class PropertyBuilder {
        //TODO
        public static Property build(PropertyEntity property) {
            return null;
        }
    }

    public static class OccupantBuilder {
        //TODO
        public static Occupant build(OccupantEntity occupant) {
            return null;
        }
    }

    public static class ParkingSpotBuilder {
        private long id;
        private boolean electric;
        private ParkingSpotEntity.PARKING_SIZE parkingSize;
        private ParkingSpotEntity.PARKING_TYPE parkingType;
        private int price;
        private Building building;
        private Property property;
        private Occupant occupant;

        public ParkingSpotBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public ParkingSpotBuilder setElectric(boolean electric) {
            this.electric = electric;
            return this;
        }

        public ParkingSpotBuilder setParkingSize(ParkingSpotEntity.PARKING_SIZE parkingSize) {
            this.parkingSize = parkingSize;
            return this;
        }

        public ParkingSpotBuilder setParkingType(ParkingSpotEntity.PARKING_TYPE parkingType) {
            this.parkingType = parkingType;
            return this;
        }

        public ParkingSpotBuilder setPrice(int price) {
            this.price = price;
            return this;
        }

        public ParkingSpotBuilder setBuilding(Building building) {
            this.building = building;
            return this;
        }

        public ParkingSpotBuilder setProperty(Property property) {
            this.property = property;
            return this;
        }

        public ParkingSpotBuilder setOccupant(Occupant occupant) {
            this.occupant = occupant;
            return this;
        }

        public ParkingSpot build() {
            return new ParkingSpot(
                    Optional.of(this.id),
                    Optional.of(this.electric),
                    Optional.of(this.parkingSize),
                    Optional.of(this.parkingType),
                    Optional.of(this.price),
                    Optional.of(this.building),
                    Optional.of(this.property),
                    Optional.of(this.occupant));
        }

        public ParkingSpot build(ParkingSpotEntity entity) {
            if (entity == null) return null;
            this.id = entity.getId();
            this.electric = entity.isElectric();
            this.parkingSize = entity.getParkingSize();
            this.parkingType = entity.getParkingType();
            this.price = entity.getPrice();
            this.building = BuildingBuilder.build(entity.getBuilding());
            this.property = PropertyBuilder.build(entity.getProperty());
            this.occupant = OccupantBuilder.build(entity.getOccupant());
            return this.build();
        }

        public static List<ParkingSpot> listFrom(Iterable<ParkingSpotEntity> entities) {
            List<ParkingSpot> parkingSpots = new LinkedList<>();
            if (entities != null) {
                for (ParkingSpotEntity entity : entities) {
                    ParkingSpot build = new ParkingSpotBuilder().build(entity);
                    parkingSpots.add(build);
                }
            }
            return parkingSpots;
        }

    }


}
