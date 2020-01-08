package com.daffodil.renters.core.model.beans;

import com.daffodil.renters.core.model.entities.OccupantEntity;

import java.time.LocalDate;
import java.util.Date;

public class Occupant {

    private long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Date dateMovedIn;
    private Date timeLastRentPaid;
    private Room room;
    private long rent;
    private LocalDate dateRentDue;

    public Occupant(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.phoneNumber = builder.phoneNumber;
        this.dateMovedIn = builder.dateMovedIn;
        this.timeLastRentPaid = builder.timeLastRentPaid;
        this.rent = builder.rent;
        this.room = builder.room;
        this.dateRentDue = builder.dateRentDue;
    }

    public static class Builder {

        private long id;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private Date dateMovedIn;
        private Date timeLastRentPaid;
        private Room room;
        private long rent;
        private LocalDate dateRentDue;

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

        public Builder setRoom(Room room) {
            this.room = room;
            return this;
        }

        public Builder setRent(long rent) {
            this.rent = rent;
            return this;
        }

        public Builder setDateRentDue(LocalDate dateRentDue) {
            this.dateRentDue = dateRentDue;
            return this;
        }

        public Occupant build() {
            return new Occupant(this);
        }

        public Occupant build(OccupantEntity entity) {
            if (entity == null) return null;
            this.id = entity.getId();
            this.firstName = entity.getFirstName();
            this.lastName = entity.getLastName();
            this.phoneNumber = entity.getPhoneNumber();
            this.rent = entity.getRent();
            this.dateMovedIn = entity.getDateMovedIn();
            this.dateRentDue = entity.getDateRentDue();
            this.room = new Room.Builder().build(entity.getRoom());
            this.timeLastRentPaid = entity.getTimeLastRentPaid();
            return new Occupant(this);
        }

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getRent() {
        return rent;
    }

    public void setRent(long rent) {
        this.rent = rent;
    }

    public LocalDate getDateRentDue() {
        return dateRentDue;
    }

    public void setDateRentDue(LocalDate dateRentDue) {
        this.dateRentDue = dateRentDue;
    }
}
