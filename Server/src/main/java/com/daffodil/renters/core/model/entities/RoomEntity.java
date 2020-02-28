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

    // Used to set variable rent in case of shared properties
    private long rent;

    // Children
    @Getter
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<OccupantEntity> occupants;

    // Parent
    @Getter
    @ManyToOne
    private PropertyEntity property;

    public void performPostParentCreationMappings(PropertyEntity propertyEntity) {
        this.property = propertyEntity;
        triggerOccupantsPostParentCreationMapping();
    }

    private void triggerOccupantsPostParentCreationMapping() {
        List<OccupantEntity> occupants = this.occupants;
        new Thread(() -> {
            if (occupants != null) {
                occupants.forEach(RoomEntity.this::triggerOccupantMappings);
            }
        }).start();
    }

    private void triggerOccupantMappings(OccupantEntity occupant) {
        if (occupant != null) {
            occupant.performPostParentCreationMappings(RoomEntity.this);
        }
    }

    public RoomEntity(short capacity, long rent) {
        this.capacity = capacity;
        this.rent = rent;
    }

    protected RoomEntity() {
    }

    public long getRent() {
        if (property != null) {
            long propertyRent = property.getRent();
            long numberOfRooms = property.getNumberOfRooms();
            if (numberOfRooms > 0) return propertyRent / numberOfRooms;
            else return propertyRent;
        } else return 0;
    }

    public int getNumberOfOccupants() {
        return occupants.size();
    }

    public RoomEntity setOccupants(List<OccupantEntity> occupants) {
        this.occupants = occupants;
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
