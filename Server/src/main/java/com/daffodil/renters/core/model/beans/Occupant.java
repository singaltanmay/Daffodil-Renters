package com.daffodil.renters.core.model.beans;

import com.daffodil.renters.core.model.entities.OccupantEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Occupant {

    @Getter
    @Setter
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
    private Date dateMovedIn;
    @Getter
    @Setter
    private Date timeLastRentPaid;
    @Getter
    @Setter
    private Room room;
    @Getter
    @Setter
    private long rent;
    @Getter
    @Setter
    private Date dateRentDue;
    @Getter
    @Setter
    private List<ParkingSpot> parkingSpots;

    // TODO
    public static class Filter {

    }

    protected Occupant() {
    }

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
        private Date dateRentDue;

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

        public Builder setDateRentDue(Date dateRentDue) {
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
            this.timeLastRentPaid = entity.getTimeLastRentPaid();
            this.dateRentDue = entity.getDateRentDue();
            return new Occupant(this);
        }

    }

    public static List<Occupant> listFrom(Iterable<OccupantEntity> entities) {
        List<Occupant> occupants = new LinkedList<>();
        if (entities != null) {
            for (OccupantEntity entity : entities) {
                Occupant build = new Occupant.Builder().build(entity);
                occupants.add(build);
            }
        }
        return occupants;
    }

}
