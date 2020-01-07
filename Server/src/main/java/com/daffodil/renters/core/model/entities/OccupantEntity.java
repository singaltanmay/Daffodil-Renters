package com.daffodil.renters.core.model.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "occupant")
public class OccupantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;
    private String lastName;
    private String phoneNumber;

    @Temporal(TemporalType.DATE)
    private Date dateMovedIn = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date timeLastRentPaid = dateMovedIn;

    // Parents
    @ManyToOne
    private RoomEntity roomEntity;


    @Transient
    private long rent = roomEntity.getRent() / roomEntity.getNumberOfOccupants();

    @Transient
    private LocalDate dateRentDue = LocalDateTime.from(timeLastRentPaid.toInstant()).plusDays(30).toLocalDate();

    public OccupantEntity(RoomEntity roomEntity) {
        this.roomEntity = roomEntity;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDateMovedIn() {
        return dateMovedIn;
    }

    public void setDateMovedIn(Date dateMovedIn) {
        this.dateMovedIn = dateMovedIn;
    }

    public Date getTimeLastRentPaid() {
        return timeLastRentPaid;
    }

    public void setTimeLastRentPaid(Date timeLastRentPaid) {
        this.timeLastRentPaid = timeLastRentPaid;
    }

    public RoomEntity getRoomEntity() {
        return roomEntity;
    }

    public void setRoomEntity(RoomEntity roomEntity) {
        this.roomEntity = roomEntity;
    }
}
