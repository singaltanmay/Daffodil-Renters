package com.daffodil.renters.core.model.entities;

import com.daffodil.renters.core.model.beans.Occupant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "occupant")
public class OccupantEntity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Getter
    @Setter
    private String firstName;
    @Getter
    @Setter
    private String lastName;
    @Getter
    @Setter
    private String phoneNumber;
    @Getter
    @Setter
    @Temporal(TemporalType.DATE)
    private Date dateMovedIn = new Date();

    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeLastRentPaid = dateMovedIn;

    // Parents
    @Getter
    @Setter
    @ManyToOne
    private RoomEntity room;

    @Getter
    @Transient
    private long rent = room.getRent() / room.getNumberOfOccupants();

    @Getter
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

        public OccupantEntity build(Occupant occupant) {
            if (occupant == null) return null;
            this.id = occupant.getId();
            this.firstName = occupant.getFirstName();
            this.lastName = occupant.getLastName();
            this.phoneNumber = occupant.getPhoneNumber();
            this.dateMovedIn = occupant.getDateMovedIn();
            this.timeLastRentPaid = occupant.getTimeLastRentPaid();
            this.room = new RoomEntity.Builder().build(occupant.getRoom());
            return new OccupantEntity(this);
        }

    }

}
