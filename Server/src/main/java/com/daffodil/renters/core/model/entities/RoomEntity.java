package com.daffodil.renters.core.model.entities;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

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

    private void mapAllOccupants() {
        List<OccupantEntity> occupants = this.occupants;
        new Thread(() -> {
            if (occupants != null) {
                occupants.forEach(entity -> RoomEntity.this.mapOccupant(entity, Optional.empty()));
            }
        }).start();
    }

    public void mapAllOccupantParkingSpots(Optional<BuildingEntity> buildingEntity) {
        List<OccupantEntity> occupants = this.occupants;
        new Thread(() -> {
            if (occupants != null) {
                occupants.forEach(entity -> RoomEntity.this.mapOccupant(entity, buildingEntity));
            }
        }).start();
    }

    private void mapOccupant(OccupantEntity entity, Optional<BuildingEntity> buildingEntity) {
        if (entity != null) {
            entity.setRoom(RoomEntity.this);
            mapOccupantParkingSpot(entity, buildingEntity);
        }
    }

    private void mapOccupantParkingSpot(OccupantEntity occupantEntity, Optional<BuildingEntity> buildingEntity) {
        if (occupantEntity != null) {
            occupantEntity.getParkingSpots().forEach(it -> {
                it.setProperty(RoomEntity.this.property);
                buildingEntity.ifPresent(it::setBuilding);
            });
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
        mapAllOccupantParkingSpots(Optional.empty());
        return this;
    }

}
