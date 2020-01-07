package com.daffodil.renters.core.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "occupant")
public class Occupant {

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
    private Room room;

    public Occupant(Room room) {
        this.room = room;
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
