package com.daffodil.renters.core.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private byte id;

    private short maxBeds;
    private long rent;

    // Children
    @OneToMany(mappedBy = "room")
    private List<Occupant> occupants;

    // Parent
    @ManyToOne
    private House house;

    public long getRent() {
        return rent;
    }

    public List<Occupant> getOccupants() {
        return occupants;
    }

    public int getNumberOfOccupants() {
        return occupants.size();
    }

    public House getHouse() {
        return house;
    }
}
