package com.daffodil.renters.core.model.entities;

import com.daffodil.renters.core.model.beans.House;
import com.daffodil.renters.core.model.beans.Occupant;
import com.daffodil.renters.core.model.beans.ParkingSpot;
import com.daffodil.renters.core.model.beans.Room;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class EntityFactory {

    public static class HouseEntityBuilder {
        private long id;
        private String address;
        private double latitude;
        private double longitude;
        List<RoomEntity> rooms;
        List<ParkingSpotEntity> parkingSpots;

        public HouseEntityBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public HouseEntityBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public HouseEntityBuilder setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public HouseEntityBuilder setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public HouseEntityBuilder setRooms(List<RoomEntity> rooms) {
            this.rooms = rooms;
            return this;
        }

        public HouseEntityBuilder setParkingSpots(List<ParkingSpotEntity> parkingSpots) {
            this.parkingSpots = parkingSpots;
            return this;
        }

        public PropertyEntity build() {
            return new PropertyEntity()
                    .setId(this.id)
                    .setAddress(this.address)
                    .setLatitude(this.latitude)
                    .setLongitude(this.longitude)
                    .setParkingSpots(this.parkingSpots)
                    .setRooms(this.rooms);
        }

        public PropertyEntity build(House house) {
            if (house == null) return null;
            this.id = house.getId();
            this.address = house.getAddress();
            this.latitude = house.getLatitude();
            this.longitude = house.getLongitude();
            this.rooms = EntityFactory.RoomEntityBuilder.listFrom(house.getRooms());
            this.parkingSpots = EntityFactory.ParkingSpotEntityBuilder.listFrom(house.getParkingSpots());
            return this.build();
        }

        public static List<PropertyEntity> listFrom(List<House> houses) {
            if (houses == null) return new LinkedList<>();
            List<PropertyEntity> entities = new LinkedList<>();
            houses.forEach(house -> {
                PropertyEntity propertyEntity = new EntityFactory.HouseEntityBuilder().build(house);
                if (propertyEntity != null) {
                    entities.add(propertyEntity);
                }
            });
            return entities;
        }

    }

    public static class RoomEntityBuilder {

        private long id;
        private long capacity;
        private long rent;
        private List<OccupantEntity> occupants;
        private PropertyEntity house;

        public RoomEntityBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public RoomEntityBuilder setCapacity(long capacity) {
            this.capacity = capacity;
            return this;
        }

        public RoomEntityBuilder setRent(long rent) {
            this.rent = rent;
            return this;
        }

        public RoomEntityBuilder setOccupants(List<OccupantEntity> occupants) {
            this.occupants = occupants;
            return this;
        }

        public RoomEntityBuilder setHouse(PropertyEntity house) {
            this.house = house;
            return this;
        }

        public RoomEntity build() {
            return new RoomEntity()
                    .setId(this.id)
                    .setCapacity(this.capacity)
                    .setRent(this.rent)
                    .setProperty(this.house)
                    .setOccupants(this.occupants);
        }

        public RoomEntity build(Room room) {
            if (room == null) return null;
            this.id = room.getId();
            this.capacity = room.getCapacity();
            this.rent = room.getRent();
            this.house = new HouseEntityBuilder().build(room.getHouse());
            this.occupants = EntityFactory.OccupantEntityBuilder.listFrom(room.getOccupants());
            return this.build();
        }

        public static List<RoomEntity> listFrom(List<Room> rooms) {
            if (rooms == null) return new LinkedList<>();
            List<RoomEntity> entities = new LinkedList<>();
            rooms.forEach(room -> {
                RoomEntity roomEntity = new EntityFactory.RoomEntityBuilder().build(room);
                if (roomEntity != null) {
                    entities.add(roomEntity);
                }
            });
            return entities;
        }

    }

    public static class OccupantEntityBuilder {

        private long id;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private Date dateMovedIn;
        private Date timeLastRentPaid;
        private RoomEntity room;
        private List<ParkingSpotEntity> parkingSpots;

        public OccupantEntityBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public OccupantEntityBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public OccupantEntityBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public OccupantEntityBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public OccupantEntityBuilder setDateMovedIn(Date dateMovedIn) {
            this.dateMovedIn = dateMovedIn;
            return this;
        }

        public OccupantEntityBuilder setTimeLastRentPaid(Date timeLastRentPaid) {
            this.timeLastRentPaid = timeLastRentPaid;
            return this;
        }

        public OccupantEntityBuilder setRoom(RoomEntity room) {
            this.room = room;
            return this;
        }

        public OccupantEntityBuilder setParkingSpots(List<ParkingSpotEntity> parkingSpots) {
            this.parkingSpots = parkingSpots;
            return this;
        }

        public OccupantEntity build() {
            return new OccupantEntity()
                    .setId(this.id)
                    .setFirstName(this.firstName)
                    .setLastName(this.lastName)
                    .setPhoneNumber(this.phoneNumber)
                    .setDateMovedIn(this.dateMovedIn)
                    .setTimeLastRentPaid(this.timeLastRentPaid)
                    .setRoom(this.room)
                    .setParkingSpots(this.parkingSpots);
        }

        public OccupantEntity build(Occupant occupant) {
            if (occupant == null) return null;
            this.id = occupant.getId();
            this.firstName = occupant.getFirstName();
            this.lastName = occupant.getLastName();
            this.phoneNumber = occupant.getPhoneNumber();
            this.dateMovedIn = occupant.getDateMovedIn();
            this.timeLastRentPaid = occupant.getTimeLastRentPaid();
            this.room = new RoomEntityBuilder().build(occupant.getRoom());
            this.parkingSpots = EntityFactory.ParkingSpotEntityBuilder.listFrom(occupant.getParkingSpots());
            return this.build();
        }

        public static List<OccupantEntity> listFrom(List<Occupant> occupants) {
            if (occupants == null) return new LinkedList<>();
            List<OccupantEntity> entities = new LinkedList<>();
            occupants.forEach(occupant -> {
                OccupantEntity occupantEntity = new EntityFactory.OccupantEntityBuilder().build(occupant);
                if (occupantEntity != null) {
                    entities.add(occupantEntity);
                }
            });
            return entities;
        }

    }

    public static class ParkingSpotEntityBuilder {

        private long id;
        private boolean electric;
        private ParkingSpotEntity.PARKING_SIZE parkingSize;
        private ParkingSpotEntity.PARKING_TYPE parkingType;
        private int price;
        private PropertyEntity propertyEntity;
        private OccupantEntity occupantEntity;

        public ParkingSpotEntityBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public ParkingSpotEntityBuilder setElectric(boolean electric) {
            this.electric = electric;
            return this;
        }

        public ParkingSpotEntityBuilder setParkingSize(ParkingSpotEntity.PARKING_SIZE parkingSize) {
            this.parkingSize = parkingSize;
            return this;
        }

        public ParkingSpotEntityBuilder setParkingType(ParkingSpotEntity.PARKING_TYPE parkingType) {
            this.parkingType = parkingType;
            return this;
        }

        public ParkingSpotEntityBuilder setPrice(int price) {
            this.price = price;
            return this;
        }

        public ParkingSpotEntityBuilder setPropertyEntity(PropertyEntity propertyEntity) {
            this.propertyEntity = propertyEntity;
            return this;
        }

        public ParkingSpotEntityBuilder setOccupantEntity(OccupantEntity occupantEntity) {
            this.occupantEntity = occupantEntity;
            return this;
        }

        public ParkingSpotEntity build() {
            return new ParkingSpotEntity()
                    .setId(this.id)
                    .setElectric(this.electric)
                    .setParkingSize(this.parkingSize)
                    .setParkingType(this.parkingType)
                    .setPrice(this.price)
                    .setProperty(this.propertyEntity)
                    .setOccupant(this.occupantEntity);
        }

        public ParkingSpotEntity build(ParkingSpot parkingSpot) {
            if (parkingSpot == null) return null;
            this.id = parkingSpot.getId();
            this.electric = parkingSpot.isElectric();
            this.parkingSize = parkingSpot.getParkingSize();
            this.parkingType = parkingSpot.getParkingType();
            this.price = parkingSpot.getPrice();
            this.propertyEntity = new HouseEntityBuilder().build(parkingSpot.getHouse());
            this.occupantEntity = new OccupantEntityBuilder().build(parkingSpot.getOccupant());
            return this.build();
        }

        public static List<ParkingSpotEntity> listFrom(Iterable<ParkingSpot> parkingSpots) {
            if (parkingSpots == null) return new LinkedList<>();
            List<ParkingSpotEntity> entities = new LinkedList<>();
            parkingSpots.forEach(parkingSpot -> {
                ParkingSpotEntity parkingSpotEntity = new EntityFactory.ParkingSpotEntityBuilder().build(parkingSpot);
                if (parkingSpotEntity != null) {
                    entities.add(parkingSpotEntity);
                }
            });
            return entities;
        }

    }
}
