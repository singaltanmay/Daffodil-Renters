package com.daffodil.renters.core.model.entities;

import javax.persistence.*;
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
    private List<OccupantEntity> occupantEntities;

    public RoomEntity setHouseEntity(HouseEntity houseEntity) {
        this.houseEntity = houseEntity;
        return this;
    }

    // Parent
    @ManyToOne
    private HouseEntity houseEntity;

    public RoomEntity(short capacity, long rent) {
        this.capacity = capacity;
        this.rent = rent;
    }



    protected RoomEntity() {
    }

    public int getNumberOfOccupants() {
        return occupantEntities.size();
    }

    public short getId() {
        return id;
    }

    public short getCapacity() {
        return capacity;
    }

    public long getRent() {
        return rent;
    }

    public List<OccupantEntity> getOccupantEntities() {
        return occupantEntities;
    }

    public HouseEntity getHouseEntity() {
        return houseEntity;
    }
}
