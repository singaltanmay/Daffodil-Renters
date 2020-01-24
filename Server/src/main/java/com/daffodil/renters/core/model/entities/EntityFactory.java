package com.daffodil.renters.core.model.entities;

import com.daffodil.renters.core.model.beans.postables.*;
import lombok.Getter;

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
            building.getProperties().ifPresent(it -> this.setProperties(PropertyEntityBuilder.listFrom(it)));
            building.getSharedParkingSpots().ifPresent(it -> this.setSharedParkingSpots(ParkingSpotEntityBuilder.listFrom(it)));
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
        private String unit;
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
        private AmenitiesEntity amenities;
        private List<RoomEntity> rooms;
        private List<ParkingSpotEntity> parkingSpots;

        public PropertyEntityBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public PropertyEntityBuilder setUnit(String unit) {
            this.unit = unit;
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

        public PropertyEntityBuilder setAmenities(AmenitiesEntity amenities) {
            this.amenities = amenities;
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
            return new PropertyEntity(
                    this.id,
                    this.unit,
                    this.description,
                    this.propertyType,
                    this.furnishingType,
                    this.area,
                    this.rent,
                    this.roommates,
                    this.securityDeposit,
                    this.brokerage,
                    this.lockInPeriod,
                    this.listedOn,
                    this.building,
                    this.seller,
                    this.amenities,
                    this.rooms,
                    this.parkingSpots);
        }

        public PropertyEntity build(Property property) {
            if (property == null) return null;
            property.getId().ifPresent(this::setId);
            property.getUnit().ifPresent(this::setUnit);
            property.getDescription().ifPresent(this::setDescription);
            property.getPropertyType().ifPresent(this::setPropertyType);
            property.getFurnishingType().ifPresent(this::setFurnishingType);
            property.getArea().ifPresent(this::setArea);
            property.getRent().ifPresent(this::setRent);
            property.getRoommates().ifPresent(this::setRoommates);
            property.getSecurityDeposit().ifPresent(this::setSecurityDeposit);
            property.getBrokerage().ifPresent(this::setBrokerage);
            property.getLockInPeriod().ifPresent(this::setLockInPeriod);
            property.getListedOn().ifPresent(this::setListedOn);
            property.getBuilding().ifPresent(it -> this.setBuilding(new BuildingEntityBuilder().build(it)));
            property.getSeller().ifPresent(it -> this.setSeller(new SellerEntityBuilder().build(it)));
            property.getAmenities().ifPresent(it -> this.setAmenities(new AmenitiesEntityBuilder().build(it)));
            property.getRooms().ifPresent(it -> this.setRooms(RoomEntityBuilder.listFrom(it)));
            property.getParkingSpots().ifPresent(it -> this.setParkingSpots(ParkingSpotEntityBuilder.listFrom(it)));
            return this.build();
        }

        public static List<PropertyEntity> listFrom(List<Property> properties) {
            if (properties == null) return new LinkedList<>();
            List<PropertyEntity> entities = new LinkedList<>();
            properties.forEach(property -> {
                PropertyEntity propertyEntity = new EntityFactory.PropertyEntityBuilder().build(property);
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
            room.getId().ifPresent(this::setId);
            room.getCapacity().ifPresent(this::setCapacity);
            room.getRent().ifPresent(this::setRent);
            room.getProperty().ifPresent(it -> this.setProperty(new PropertyEntityBuilder().build(it)));
            room.getOccupants().ifPresent(it -> this.setOccupants(OccupantEntityBuilder.listFrom(it)));
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
            occupant.getId().ifPresent(this::setId);
            occupant.getFirstName().ifPresent(this::setFirstName);
            occupant.getLastName().ifPresent(this::setLastName);
            occupant.getPhoneNumber().ifPresent(this::setPhoneNumber);
            occupant.getDateMovedIn().ifPresent(this::setDateMovedIn);
            occupant.getTimeLastRentPaid().ifPresent(this::setTimeLastRentPaid);
            occupant.getRoom().ifPresent(it -> this.setRoom(new RoomEntityBuilder().build(it)));
            occupant.getParkingSpots().ifPresent(it -> this.setParkingSpots(ParkingSpotEntityBuilder.listFrom(it)));
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
        private BuildingEntity buildingEntity;
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

        public ParkingSpotEntityBuilder setBuildingEntity(BuildingEntity buildingEntity) {
            this.buildingEntity = buildingEntity;
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
            return new ParkingSpotEntity(
                    this.id,
                    this.electric,
                    this.parkingSize,
                    this.parkingType,
                    this.price,
                    this.buildingEntity,
                    this.propertyEntity,
                    this.occupantEntity);
        }

        public ParkingSpotEntity build(ParkingSpot parkingSpot) {
            if (parkingSpot == null) return null;
            parkingSpot.getId().ifPresent(this::setId);
            parkingSpot.getElectric().ifPresent(this::setElectric);
            parkingSpot.getParkingSize().ifPresent(this::setParkingSize);
            parkingSpot.getParkingType().ifPresent(this::setParkingType);
            parkingSpot.getPrice().ifPresent(this::setPrice);
            parkingSpot.getBuilding().ifPresent(it -> new BuildingEntityBuilder().build(it));
            parkingSpot.getProperty().ifPresent(it -> new PropertyEntityBuilder().build(it));
            parkingSpot.getOccupant().ifPresent(it -> new OccupantEntityBuilder().build(it));
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

    public static class AmenitiesEntityBuilder {

        @Getter
        private long id;
        @Getter
        boolean gasPipeline;
        @Getter
        boolean swimmingPool;
        @Getter
        boolean gym;
        @Getter
        boolean lift;
        @Getter
        boolean gatedCommunity;
        @Getter
        boolean parking;
        @Getter
        boolean petsAllowed;
        @Getter
        private List<PropertyEntity> properties;

        public AmenitiesEntityBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public AmenitiesEntityBuilder setGasPipeline(boolean gasPipeline) {
            this.gasPipeline = gasPipeline;
            return this;
        }

        public AmenitiesEntityBuilder setSwimmingPool(boolean swimmingPool) {
            this.swimmingPool = swimmingPool;
            return this;
        }

        public AmenitiesEntityBuilder setGym(boolean gym) {
            this.gym = gym;
            return this;
        }

        public AmenitiesEntityBuilder setLift(boolean lift) {
            this.lift = lift;
            return this;
        }

        public AmenitiesEntityBuilder setGatedCommunity(boolean gatedCommunity) {
            this.gatedCommunity = gatedCommunity;
            return this;
        }

        public AmenitiesEntityBuilder setParking(boolean parking) {
            this.parking = parking;
            return this;
        }

        public AmenitiesEntityBuilder setPetsAllowed(boolean petsAllowed) {
            this.petsAllowed = petsAllowed;
            return this;
        }

        public AmenitiesEntityBuilder setProperties(List<PropertyEntity> properties) {
            this.properties = properties;
            return this;
        }

        public AmenitiesEntity build() {
            return new AmenitiesEntity(
                    this.id,
                    this.gasPipeline,
                    this.swimmingPool,
                    this.gym,
                    this.lift,
                    this.gatedCommunity,
                    this.parking,
                    this.petsAllowed,
                    this.properties
            );
        }

        public AmenitiesEntity build(Amenities amenities) {
            amenities.getId().ifPresent(this::setId);
            amenities.getGasPipeline().ifPresent(this::setGasPipeline);
            amenities.getSwimmingPool().ifPresent(this::setSwimmingPool);
            amenities.getGym().ifPresent(this::setGym);
            amenities.getLift().ifPresent(this::setLift);
            amenities.getGatedCommunity().ifPresent(this::setGatedCommunity);
            amenities.getParking().ifPresent(this::setParking);
            amenities.getPetsAllowed().ifPresent(this::setPetsAllowed);
            amenities.getProperties().ifPresent(it -> this.setProperties(PropertyEntityBuilder.listFrom(it)));
            return this.build();
        }

        public static List<AmenitiesEntity> listFrom(List<Amenities> amenitiess) {
            if (amenitiess == null) return new LinkedList<>();
            List<AmenitiesEntity> entities = new LinkedList<>();
            amenitiess.forEach(amenities -> {
                AmenitiesEntity amenitiesEntity = new EntityFactory.AmenitiesEntityBuilder().build(amenities);
                if (amenitiesEntity != null) {
                    entities.add(amenitiesEntity);
                }
            });
            return entities;
        }

    }

    public static class SellerEntityBuilder {
        @Getter
        private long id;
        @Getter
        private String firstName;
        @Getter
        private String lastName;
        @Getter
        private String phoneNumber;
        @Getter
        private SellerEntity.SELLER_TYPE sellerType;
        @Getter
        List<PropertyEntity> propertyEntities;

        public SellerEntityBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public SellerEntityBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public SellerEntityBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public SellerEntityBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public SellerEntityBuilder setSellerType(SellerEntity.SELLER_TYPE sellerType) {
            this.sellerType = sellerType;
            return this;
        }

        public SellerEntityBuilder setPropertyEntities(List<PropertyEntity> propertyEntities) {
            this.propertyEntities = propertyEntities;
            return this;
        }

        public SellerEntity build() {
            return new SellerEntity(
                    this.id,
                    this.firstName,
                    this.lastName,
                    this.phoneNumber,
                    this.sellerType,
                    this.propertyEntities
            );
        }

        public SellerEntity build(Seller seller) {
            seller.getId().ifPresent(this::setId);
            seller.getFirstName().ifPresent(this::setFirstName);
            seller.getLastName().ifPresent(this::setLastName);
            seller.getPhoneNumber().ifPresent(this::setPhoneNumber);
            seller.getSellerType().ifPresent(this::setSellerType);
            seller.getProperties().ifPresent(it -> this.setPropertyEntities(PropertyEntityBuilder.listFrom(it)));
            return this.build();
        }

        public List<SellerEntity> listFrom(List<Seller> sellers) {
            if (sellers == null) return new LinkedList<>();
            List<SellerEntity> entities = new LinkedList<>();
            sellers.forEach(seller -> {
                SellerEntity sellerEntity = new EntityFactory.SellerEntityBuilder().build(seller);
                if (sellerEntity != null) {
                    entities.add(sellerEntity);
                }
            });
            return entities;
        }
    }
}
