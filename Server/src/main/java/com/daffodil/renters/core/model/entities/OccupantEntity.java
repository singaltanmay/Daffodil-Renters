package com.daffodil.renters.core.model.entities;

import lombok.Getter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "occupant")
public class OccupantEntity {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Getter
    private String firstName;
    @Getter
    private String lastName;
    @Getter
    private String phoneNumber;
    @Getter
    @Temporal(TemporalType.DATE)
    private Date dateMovedIn = new Date();

    @Getter
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeLastRentPaid = new Date();

    // Parents
    @Getter
    @ManyToOne
    private RoomEntity room;

    @Getter
    @OneToMany(mappedBy = "occupant", cascade = CascadeType.ALL)
    List<ParkingSpotEntity> parkingSpots;

    public long getRent() {
        if (room != null && room.getRent() != 0 && room.getNumberOfOccupants() != 0) {
            long roomRent = room.getRent() / room.getNumberOfOccupants();
            // Add price of all parking spots to rent
            if (parkingSpots != null) {
                roomRent += parkingSpots.stream().mapToLong(ParkingSpotEntity::getPrice).sum();
            }
            return roomRent;
        } else return 0;
    }

    public Date getDateRentDue() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timeLastRentPaid);
        calendar.add(Calendar.DATE, 30);
        return calendar.getTime();
    }

    public void mapAllParkingSpots() {
        List<ParkingSpotEntity> parkingSpotEntities = this.parkingSpots;
        if (parkingSpotEntities != null) {
            for (ParkingSpotEntity parkSpotE : parkingSpotEntities) {
                mapParkingSpot(parkSpotE);
            }
        }
    }

    public void mapParkingSpot(ParkingSpotEntity parkingSpotEntity) {
        if (parkingSpotEntity != null) {
            parkingSpotEntity.setOccupant(OccupantEntity.this);
            RoomEntity roomEntity = OccupantEntity.this.getRoom();
            if (roomEntity != null) {
                HouseEntity houseEntity = roomEntity.getHouse();
                if (houseEntity != null) {
                    parkingSpotEntity.setHouse(houseEntity);
                }
            }
        }
    }

    public OccupantEntity(RoomEntity room) {
        this.room = room;
    }

    public OccupantEntity() {
    }

    public OccupantEntity setParkingSpots(List<ParkingSpotEntity> parkingSpots) {
        this.parkingSpots = parkingSpots;
        mapAllParkingSpots();
        return this;
    }

    public OccupantEntity setId(long id) {
        this.id = id;
        return this;
    }

    public OccupantEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public OccupantEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public OccupantEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public OccupantEntity setDateMovedIn(Date dateMovedIn) {
        if (dateMovedIn != null) {
            this.dateMovedIn = dateMovedIn;
        }
        return this;
    }

    public OccupantEntity setTimeLastRentPaid(Date timeLastRentPaid) {
        if (timeLastRentPaid != null) {
            this.timeLastRentPaid = timeLastRentPaid;
        }
        return this;
    }

    public OccupantEntity setRoom(RoomEntity room) {
        this.room = room;
        return this;
    }
}
