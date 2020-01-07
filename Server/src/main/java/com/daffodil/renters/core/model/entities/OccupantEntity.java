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
    private RoomEntity room;

    @Transient
    private long rent = room.getRent() / room.getNumberOfOccupants();

    @Transient
    private LocalDate dateRentDue = LocalDateTime.from(timeLastRentPaid.toInstant()).plusDays(30).toLocalDate();

    public OccupantEntity(RoomEntity room) {
        this.room = room;
    }

    public OccupantEntity(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.phoneNumber = builder.phoneNumber;
        if (builder.dateMovedIn != null) {
            this.dateMovedIn = builder.dateMovedIn;
        }
        if (builder.timeLastRentPaid != null) {
            this.timeLastRentPaid = builder.timeLastRentPaid;
        }
        this.room = builder.room;
    }

    public static class Builder {

        private long id;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private Date dateMovedIn;
        private Date timeLastRentPaid;
        private RoomEntity room;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setDateMovedIn(Date dateMovedIn) {
            this.dateMovedIn = dateMovedIn;
            return this;
        }

        public Builder setTimeLastRentPaid(Date timeLastRentPaid) {
            this.timeLastRentPaid = timeLastRentPaid;
            return this;
        }

        public Builder setRoom(RoomEntity room) {
            this.room = room;
            return this;
        }

        public OccupantEntity build() {
            return new OccupantEntity(this);
        }

    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Date getDateMovedIn() {
        return dateMovedIn;
    }

    public Date getTimeLastRentPaid() {
        return timeLastRentPaid;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public long getRent() {
        return rent;
    }

    public LocalDate getDateRentDue() {
        return dateRentDue;
    }
}
