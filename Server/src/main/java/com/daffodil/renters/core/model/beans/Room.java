package com.daffodil.renters.core.model.beans;

import com.daffodil.renters.core.model.entities.OccupantEntity;
import com.daffodil.renters.core.model.entities.RoomEntity;

import java.util.LinkedList;
import java.util.List;

public class Room {

    private short id;
    private short capacity;
    private long rent;
    private List<Occupant> occupants;
    private House house;

    public Room(short id, short capacity, long rent, List<Occupant> occupants, House house) {
        this.id = id;
        this.capacity = capacity;
        this.rent = rent;
        this.occupants = occupants;
        this.house = house;
    }

    public Room(Builder builder) {
        this.id = builder.id;
        this.capacity = builder.capacity;
        this.house = builder.house;
        this.occupants = builder.occupants;
        this.rent = builder.rent;
    }

    public static class Builder {

        private short id;
        private short capacity;
        private long rent;
        private List<Occupant> occupants;
        private House house;

        public Builder setId(short id) {
            this.id = id;
            return this;
        }

        public Builder setCapacity(short capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder setRent(long rent) {
            this.rent = rent;
            return this;
        }

        public Builder setOccupants(List<Occupant> occupants) {
            this.occupants = occupants;
            return this;
        }

        public Builder setHouse(House house) {
            this.house = house;
            return this;
        }

        public Room build() {
            return new Room(this);
        }

        public Room build(RoomEntity entity) {
            if (entity == null) return null;
            this.id = entity.getId();
            this.capacity = entity.getCapacity();
            this.house = (new House.Builder()).build(entity.getHouse());

            List<OccupantEntity> entityOccupants = entity.getOccupants();
            List<OccupantEntity> occupantEntities = entityOccupants != null ? entityOccupants : new LinkedList<>();
            LinkedList<Occupant> occupants = new LinkedList<>();
            for (OccupantEntity occ : occupantEntities) {
                Occupant build = new Occupant.Builder().build(occ);
                if (build != null) {
                    occupants.add(build);
                }
            }
            this.occupants = occupants;

            this.rent = entity.getRent();
            return new Room(this);
        }


    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public short getCapacity() {
        return capacity;
    }

    public void setCapacity(short capacity) {
        this.capacity = capacity;
    }

    public long getRent() {
        return rent;
    }

    public void setRent(long rent) {
        this.rent = rent;
    }

    public List<Occupant> getOccupants() {
        return occupants;
    }

    public void setOccupants(List<Occupant> occupants) {
        this.occupants = occupants;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}
