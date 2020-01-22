package com.daffodil.renters.core.model.entities;

import com.daffodil.renters.core.model.beans.Occupant;
import com.daffodil.renters.core.model.beans.postables.ParkingSpot;
import com.daffodil.renters.core.model.beans.Room;
import com.daffodil.renters.core.model.beans.postables.Building;
import com.daffodil.renters.core.model.beans.postables.Property;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class EntityFactory {

    public static class BuildingEntityBuilder {

        private long id;
        private String addressBuildingName;
        private String addressLocalityName;
        private String addressSubdivision;
        private String addressCity;
        private String addressState;
        private String addressPinCode;
        private Date buildingConstructed;
        private double latitude;
        private double longitude;
        private List<PropertyEntity> properties;
        private List<ParkingSpotEntity> sharedParkingSpots;

        public BuildingEntityBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public BuildingEntityBuilder setAddressBuildingName(String addressBuildingName) {
            this.addressBuildingName = addressBuildingName;
            return this;
        }

        public BuildingEntityBuilder setAddressLocalityName(String addressLocalityName) {
            this.addressLocalityName = addressLocalityName;
            return this;
        }

        public BuildingEntityBuilder setAddressSubdivision(String addressSubdivision) {
            this.addressSubdivision = addressSubdivision;
            return this;
        }

        public BuildingEntityBuilder setAddressCity(String addressCity) {
            this.addressCity = addressCity;
            return this;
        }

        public BuildingEntityBuilder setAddressState(String addressState) {
            this.addressState = addressState;
            return this;
        }

        public BuildingEntityBuilder setAddressPinCode(String addressPinCode) {
            this.addressPinCode = addressPinCode;
            return this;
        }

        public BuildingEntityBuilder setBuildingConstructed(Date buildingConstructed) {
            this.buildingConstructed = buildingConstructed;
            return this;
        }

        public BuildingEntityBuilder setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public BuildingEntityBuilder setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public BuildingEntityBuilder setProperties(List<PropertyEntity> properties) {
            this.properties = properties;
            return this;
        }

        public BuildingEntityBuilder setSharedParkingSpots(List<ParkingSpotEntity> sharedParkingSpots) {
            this.sharedParkingSpots = sharedParkingSpots;
            return this;
        }

        public BuildingEntity build() {
            return new BuildingEntity(
                    this.id,
                    this.addressBuildingName,
                    this.addressLocalityName,
                    this.addressSubdivision,
                    this.addressCity,
                    this.addressState,
                    this.addressPinCode,
                    this.buildingConstructed,
                    this.latitude,
                    this.longitude,
                    this.properties,
                    this.sharedParkingSpots);
        }

        public BuildingEntity build(Building building) {
            if (building == null) return null;
            building.getId().ifPresent(this::setId);
            building.getAddressBuildingName().ifPresent(this::setAddressBuildingName);
            building.getAddressLocalityName().ifPresent(this::setAddressLocalityName);
            building.getAddressSubdivision().ifPresent(this::setAddressSubdivision);
            building.getAddressCity().ifPresent(this::setAddressCity);
            building.getAddressState().ifPresent(this::setAddressState);
            building.getAddressPinCode().ifPresent(this::setAddressPinCode);
            building.getBuildingConstructed().ifPresent(this::setBuildingConstructed);
            building.getLatitude().ifPresent(this::setLatitude);
            building.getLongitude().ifPresent(this::setLongitude);
            building.getProperties().ifPresent(it -> {
                this.setProperties(PropertyEntityBuilder.listFrom(it));
            });
            building.getSharedParkingSpots().ifPresent(it -> {
                this.setSharedParkingSpots(ParkingSpotEntityBuilder.listFrom(it));
            });
            return this.build();
        }

        public static List<BuildingEntity> listFrom(List<Building> buildings) {
            if (buildings == null) return new LinkedList<>();
            List<BuildingEntity> entities = new LinkedList<>();
            buildings.forEach(building -> {
                BuildingEntity buildingEntity = new EntityFactory.BuildingEntityBuilder().build(building);
                if (buildingEntity != null) {
                    entities.add(buildingEntity);
                }
            });
            return entities;
        }

    }

    public static class PropertyEntityBuilder {
        private long id;
        private String description;
        private PropertyEntity.PROPERTY_TYPE propertyType;
        private PropertyEntity.FURNISHING_TYPE furnishingType;
        private int area;
        private long rent;
        private boolean roommates;
        private long securityDeposit;
        private long brokerage;
        private int lockInPeriod;
        private Date listedOn;
        private BuildingEntity building;
        private SellerEntity seller;
        private List<RoomEntity> rooms;
        private List<ParkingSpotEntity> parkingSpots;

        public PropertyEntityBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public PropertyEntityBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public PropertyEntityBuilder setPropertyType(PropertyEntity.PROPERTY_TYPE propertyType) {
            this.propertyType = propertyType;
            return this;
        }

        public PropertyEntityBuilder setFurnishingType(PropertyEntity.FURNISHING_TYPE furnishingType) {
            this.furnishingType = furnishingType;
            return this;
        }

        public PropertyEntityBuilder setArea(int area) {
            this.area = area;
            return this;
        }

        public PropertyEntityBuilder setRent(long rent) {
            this.rent = rent;
            return this;
        }

        public PropertyEntityBuilder setRoommates(boolean roommates) {
            this.roommates = roommates;
            return this;
        }

        public PropertyEntityBuilder setSecurityDeposit(long securityDeposit) {
            this.securityDeposit = securityDeposit;
            return this;
        }

        public PropertyEntityBuilder setBrokerage(long brokerage) {
            this.brokerage = brokerage;
            return this;
        }

        public PropertyEntityBuilder setLockInPeriod(int lockInPeriod) {
            this.lockInPeriod = lockInPeriod;
            return this;
        }

        public PropertyEntityBuilder setListedOn(Date listedOn) {
            this.listedOn = listedOn;
            return this;
        }

        public PropertyEntityBuilder setBuilding(BuildingEntity building) {
            this.building = building;
            return this;
        }

        public PropertyEntityBuilder setSeller(SellerEntity seller) {
            this.seller = seller;
            return this;
        }

        public PropertyEntityBuilder setRooms(List<RoomEntity> rooms) {
            this.rooms = rooms;
            return this;
        }

        public PropertyEntityBuilder setParkingSpots(List<ParkingSpotEntity> parkingSpots) {
            this.parkingSpots = parkingSpots;
            return this;
        }

        public PropertyEntity build() {
            return new PropertyEntity()
                    .setId(this.id)
                    .setDescription(this.description)
                    .setPropertyType(this.propertyType)
                    .setFurnishingType(this.furnishingType)
                    .setArea(this.area)
                    .setRent(this.rent)
                    .setRoommates(this.roommates)
                    .setSecurityDeposit(this.securityDeposit)
                    .setBrokerage(this.brokerage)
                    .setLockInPeriod(this.lockInPeriod)
                    .setListedOn(this.listedOn)
                    .setBuilding(this.building)
                    .setSeller(this.seller)
                    .setParkingSpots(this.parkingSpots)
                    .setRooms(this.rooms);
        }

        public static List<PropertyEntity> listFrom(List<Property> it) {
            //TODO
            return null;
        }

    }

    public static class RoomEntityBuilder {

        private long id;
        private long capacity;
        private long rent;
        private List<OccupantEntity> occupants;
        private PropertyEntity property;

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

        public RoomEntityBuilder setProperty(PropertyEntity property) {
            this.property = property;
            return this;
        }

        public RoomEntity build() {
            return new RoomEntity()
                    .setId(this.id)
                    .setCapacity(this.capacity)
                    .setRent(this.rent)
                    .setProperty(this.property)
                    .setOccupants(this.occupants);
        }

        public RoomEntity build(Room room) {
            if (room == null) return null;
            this.id = room.getId();
            this.capacity = room.getCapacity();
            this.rent = room.getRent();
            this.property = new PropertyEntityBuilder().build(room.getProperty());
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
                    .setBuilding(this.propertyEntity)
                    .setOccupant(this.occupantEntity);
        }

        public ParkingSpotEntity build(ParkingSpot parkingSpot) {
            if (parkingSpot == null) return null;
            this.id = parkingSpot.getId();
            this.electric = parkingSpot.isElectric();
            this.parkingSize = parkingSpot.getParkingSize();
            this.parkingType = parkingSpot.getParkingType();
            this.price = parkingSpot.getPrice();
            this.propertyEntity = new PropertyEntityBuilder().build(parkingSpot.getProperty());
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
