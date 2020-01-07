package com.daffodil.renters.core.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private short id;

    private short capacity;
    private long rent;

    // Children
    @OneToMany(mappedBy = "room")
    private List<Occupant> occupants;

    public Room setHouse(House house) {
        this.house = house;
        return this;
    }

    // Parent
    @ManyToOne
    private House house;

    public Room(short capacity, long rent) {
        this.capacity = capacity;
        this.rent = rent;
    }



    protected Room() {
    }

    public int getNumberOfOccupants() {
        return occupants.size();
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

    public List<Occupant> getOccupants() {
        return occupants;
    }

    public House getHouse() {
        return house;
    }
}
