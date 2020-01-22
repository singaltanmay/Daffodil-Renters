package com.daffodil.renters.core.model.entities;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "room")
public class RoomEntity {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Getter
    private long capacity;

    @Getter
    private long rent;

    // Children
    @Getter
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<OccupantEntity> occupants;

    // Parent
    @Getter
    @ManyToOne
    private PropertyEntity property;

    private void mapAllOccupants() {
        List<OccupantEntity> occupants = this.getOccupants();
        if (occupants != null) {
            for (OccupantEntity oc : occupants) {
                mapOccupant(oc);
            }
        }
    }

    private void mapOccupant(OccupantEntity entity) {
        if (entity != null) {
            entity.setRoom(RoomEntity.this);
        }
    }

    public RoomEntity(short capacity, long rent) {
        this.capacity = capacity;
        this.rent = rent;
    }

    protected RoomEntity() {
    }

    public int getNumberOfOccupants() {
        return occupants.size();
    }

    public RoomEntity setOccupants(List<OccupantEntity> occupants) {
        this.occupants = occupants;
        mapAllOccupants();
        return this;
    }

    public RoomEntity setId(long id) {
        this.id = id;
        return this;
    }

    public RoomEntity setCapacity(long capacity) {
        this.capacity = capacity;
        return this;
    }

    public RoomEntity setRent(long rent) {
        this.rent = rent;
        return this;
    }

    public RoomEntity setProperty(PropertyEntity property) {
        this.property = property;
        return this;
    }
}
