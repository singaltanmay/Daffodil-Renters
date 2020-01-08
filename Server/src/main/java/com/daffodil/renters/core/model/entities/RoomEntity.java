package com.daffodil.renters.core.model.entities;

import com.daffodil.renters.core.model.beans.Occupant;
import com.daffodil.renters.core.model.beans.Room;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "room")
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private short id;

    private short capacity;
    private long rent;

    // Children
    @OneToMany(mappedBy = "room")
    private List<OccupantEntity> occupants;

    // Parent
    @ManyToOne
    private HouseEntity house;

    public RoomEntity(short capacity, long rent) {
        this.capacity = capacity;
        this.rent = rent;
    }

    public RoomEntity(Builder builder) {
        this.id = builder.id;
        this.capacity = builder.capacity;
        this.rent = builder.rent;
        this.occupants = builder.occupants;
        this.house = builder.house;
    }

    protected RoomEntity() {
    }

    public static class Builder {

        private short id;
        private short capacity;
        private long rent;
        private List<OccupantEntity> occupants;
        private HouseEntity house;

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

        public Builder setOccupants(List<OccupantEntity> occupants) {
            this.occupants = occupants;
            return this;
        }

        public Builder setHouse(HouseEntity house) {
            this.house = house;
            return this;
        }

        public RoomEntity build() {
            return new RoomEntity(this);
        }

        public RoomEntity build(Room room) {
            if (room == null) return null;
            this.id = room.getId();
            this.capacity = room.getCapacity();
            this.rent = room.getRent();
            this.house = new HouseEntity.Builder().build(room.getHouse());
            List<Occupant> roomOccupants = room.getOccupants();
            List<Occupant> occupants = roomOccupants != null ? roomOccupants : new LinkedList<>();
            List<OccupantEntity> occupantEntities = new LinkedList<>();
            for (Occupant occ : occupants) {
                OccupantEntity build = new OccupantEntity.Builder().build(occ);
                if (build != null) {
                    occupantEntities.add(build);
                }
            }
            this.occupants = occupantEntities;
            return new RoomEntity(this);
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

    public List<OccupantEntity> getOccupants() {
        return occupants;
    }

    public void setOccupants(List<OccupantEntity> occupants) {
        this.occupants = occupants;
    }

    public HouseEntity getHouse() {
        return house;
    }

    public void setHouse(HouseEntity house) {
        this.house = house;
    }

    public int getNumberOfOccupants() {
        return occupants.size();
    }

}
