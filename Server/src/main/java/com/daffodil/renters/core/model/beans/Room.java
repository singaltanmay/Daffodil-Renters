package com.daffodil.renters.core.model.beans;

import com.daffodil.renters.core.model.entities.HouseEntity;
import com.daffodil.renters.core.model.entities.RoomEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

public class Room {

    @Getter
    @Setter
    private long id;
    @Getter
    @Setter
    private long capacity;
    @Getter
    @Setter
    private long rent;
    @Getter
    @Setter
    private List<Occupant> occupants;
    @Getter
    @Setter
    private House house;

    public Room(long id, short capacity, long rent, List<Occupant> occupants, House house) {
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

        private long id;
        private long capacity;
        private long rent;
        private List<Occupant> occupants;
        private House house;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setCapacity(long capacity) {
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
            this.rent = entity.getRent();
            return new Room(this);
        }


    }

    public static List<Room> listFrom(Iterable<RoomEntity> entities) {
        List<Room> rooms = new LinkedList<>();
        if (entities != null) {
            for (RoomEntity entity : entities) {
                Room build = new Room.Builder().build(entity);
                rooms.add(build);
            }
        }
        return rooms;
    }
}
