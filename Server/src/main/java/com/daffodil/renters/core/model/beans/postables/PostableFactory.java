package com.daffodil.renters.core.model.beans.postables;

import com.daffodil.renters.core.model.entities.*;
import lombok.Getter;

import java.util.*;

public class PostableFactory {

    private static <T> Optional<T> NullableOptional(T data) {
        if (data == null) {
            return Optional.empty();
        } else return Optional.of(data);
    }

    public static class BuildingBuilder {

        @Getter
        private Long id;
        @Getter
        private String addressBuildingName;
        @Getter
        private String addressLocalityName;
        @Getter
        private String addressSubdivision;
        @Getter
        private String addressCity;
        @Getter
        private String addressState;
        @Getter
        private String addressPinCode;
        @Getter
        private Date buildingConstructed;
        @Getter
        private Double latitude;
        @Getter
        private Double longitude;
        @Getter
        private List<Property> properties;
        @Getter
        private List<ParkingSpot> sharedParkingSpots;

        public static List<Building> listFrom(List<BuildingEntity> entities) {
            List<Building> buildings = new LinkedList<>();
            if (entities != null) {
                for (BuildingEntity entity : entities) {
                    Building build = new BuildingBuilder().build(entity);
                    buildings.add(build);
                }
            }
            return buildings;
        }

        public BuildingBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public BuildingBuilder setAddressBuildingName(String addressBuildingName) {
            this.addressBuildingName = addressBuildingName;
            return this;
        }

        public BuildingBuilder setAddressLocalityName(String addressLocalityName) {
            this.addressLocalityName = addressLocalityName;
            return this;
        }

        public BuildingBuilder setAddressSubdivision(String addressSubdivision) {
            this.addressSubdivision = addressSubdivision;
            return this;
        }

        public BuildingBuilder setAddressCity(String addressCity) {
            this.addressCity = addressCity;
            return this;
        }

        public BuildingBuilder setAddressState(String addressState) {
            this.addressState = addressState;
            return this;
        }

        public BuildingBuilder setAddressPinCode(String addressPinCode) {
            this.addressPinCode = addressPinCode;
            return this;
        }

        public BuildingBuilder setBuildingConstructed(Date buildingConstructed) {
            this.buildingConstructed = buildingConstructed;
            return this;
        }

        public BuildingBuilder setLatitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public BuildingBuilder setLongitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public BuildingBuilder setProperties(List<Property> properties) {
            this.properties = properties;
            return this;
        }

        public BuildingBuilder setSharedParkingSpots(List<ParkingSpot> sharedParkingSpots) {
            this.sharedParkingSpots = sharedParkingSpots;
            return this;
        }

        public Building build() {
            return new Building(
                    NullableOptional(this.id),
                    NullableOptional(this.addressBuildingName),
                    NullableOptional(this.addressLocalityName),
                    NullableOptional(this.addressSubdivision),
                    NullableOptional(this.addressCity),
                    NullableOptional(this.addressState),
                    NullableOptional(this.addressPinCode),
                    NullableOptional(this.buildingConstructed),
                    NullableOptional(this.latitude),
                    NullableOptional(this.longitude),
                    NullableOptional(this.properties),
                    NullableOptional(this.sharedParkingSpots)
            );
        }

        public Building build(BuildingEntity entity) {
            this.id = entity.getId();
            this.addressBuildingName = entity.getAddressBuildingName();
            this.addressLocalityName = entity.getAddressLocalityName();
            this.addressSubdivision = entity.getAddressSubdivision();
            this.addressCity = entity.getAddressCity();
            this.addressState = entity.getAddressState();
            this.addressPinCode = entity.getAddressPinCode();
            this.buildingConstructed = entity.getBuildingConstructed();
            this.latitude = entity.getLatitude();
            this.longitude = entity.getLongitude();
            this.properties = PropertyBuilder.listFrom(entity.getProperties());
            this.sharedParkingSpots = ParkingSpotBuilder.listFrom(entity.getParkingSpots());
            return this.build();
        }

    }

    public static class PropertyBuilder {

        @Getter
        private long id;
        @Getter
        private String unit;
        @Getter
        private String description;
        @Getter
        private PropertyEntity.PROPERTY_TYPE propertyType;
        @Getter
        private PropertyEntity.FURNISHING_TYPE furnishingType;
        @Getter
        private int area;
        @Getter
        private long rent;
        @Getter
        private boolean roommates;
        @Getter
        private long securityDeposit;
        @Getter
        private long brokerage;
        @Getter
        private int lockInPeriod;
        @Getter
        private Date listedOn;
        @Getter
        private Building building;
        @Getter
        private Seller seller;
        @Getter
        private Amenities amenities;
        @Getter
        private List<Room> rooms;
        @Getter
        private List<ParkingSpot> parkingSpots;

        public static List<Property> listFrom(List<PropertyEntity> entities) {
            List<Property> properties = new LinkedList<>();
            if (entities != null) {
                for (PropertyEntity entity : entities) {
                    Property build = new PropertyBuilder().build(entity);
                    properties.add(build);
                }
            }
            return properties;
        }

        public PropertyBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public PropertyBuilder setUnit(String unit) {
            this.unit = unit;
            return this;
        }

        public PropertyBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public PropertyBuilder setPropertyType(PropertyEntity.PROPERTY_TYPE propertyType) {
            this.propertyType = propertyType;
            return this;
        }

        public PropertyBuilder setFurnishingType(PropertyEntity.FURNISHING_TYPE furnishingType) {
            this.furnishingType = furnishingType;
            return this;
        }

        public PropertyBuilder setArea(int area) {
            this.area = area;
            return this;
        }

        public PropertyBuilder setRent(long rent) {
            this.rent = rent;
            return this;
        }

        public PropertyBuilder setRoommates(boolean roommates) {
            this.roommates = roommates;
            return this;
        }

        public PropertyBuilder setSecurityDeposit(long securityDeposit) {
            this.securityDeposit = securityDeposit;
            return this;
        }

        public PropertyBuilder setBrokerage(long brokerage) {
            this.brokerage = brokerage;
            return this;
        }

        public PropertyBuilder setLockInPeriod(int lockInPeriod) {
            this.lockInPeriod = lockInPeriod;
            return this;
        }

        public PropertyBuilder setListedOn(Date listedOn) {
            this.listedOn = listedOn;
            return this;
        }

        public PropertyBuilder setBuilding(Building building) {
            this.building = building;
            return this;
        }

        public PropertyBuilder setSeller(Seller seller) {
            this.seller = seller;
            return this;
        }

        public PropertyBuilder setAmenities(Amenities amenities) {
            this.amenities = amenities;
            return this;
        }

        public PropertyBuilder setRooms(List<Room> rooms) {
            this.rooms = rooms;
            return this;
        }

        public PropertyBuilder setParkingSpots(List<ParkingSpot> parkingSpots) {
            this.parkingSpots = parkingSpots;
            return this;
        }

        public Property build() {
            return new Property(
                    NullableOptional(this.id),
                    NullableOptional(this.unit),
                    NullableOptional(this.description),
                    NullableOptional(this.propertyType),
                    NullableOptional(this.furnishingType),
                    NullableOptional(this.area),
                    NullableOptional(this.rent),
                    NullableOptional(this.roommates),
                    NullableOptional(this.securityDeposit),
                    NullableOptional(this.brokerage),
                    NullableOptional(this.lockInPeriod),
                    NullableOptional(this.listedOn),
                    this.building == null ? Optional.empty() : NullableOptional(this.building),
                    NullableOptional(this.seller),
                    NullableOptional(this.amenities),
                    NullableOptional(this.rooms),
                    NullableOptional(this.parkingSpots)
            );
        }

        public Property build(PropertyEntity property) {
            try {

                this.id = property.getId();
                this.unit = property.getUnit();
                this.description = property.getDescription();
                this.propertyType = property.getPropertyType();
                this.furnishingType = property.getFurnishingType();
                this.area = property.getArea();
                this.rent = property.getRent();
                this.roommates = property.isRoommates();
                this.securityDeposit = property.getSecurityDeposit();
                this.brokerage = property.getBrokerage();
                this.lockInPeriod = property.getLockInPeriod();
                this.listedOn = property.getListedOn();
                this.seller = new SellerBuilder().build(property.getSeller());
                this.amenities = new AmenitiesBuilder().build(property.getAmenities());
                this.rooms = RoomBuilder.listFrom(property.getRooms());
                this.parkingSpots = ParkingSpotBuilder.listFrom(property.getParkingSpots());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.build();
        }
    }

    public static class RoomBuilder {
        @Getter
        private long id;
        @Getter
        private long capacity;
        @Getter
        private long rent;
        @Getter
        private List<Occupant> occupants;
        @Getter
        private Property property;

        public static List<Room> listFrom(List<RoomEntity> entities) {
            List<Room> rooms = new LinkedList<>();
            if (entities != null) {
                for (RoomEntity entity : entities) {
                    Room build = new RoomBuilder().build(entity);
                    rooms.add(build);
                }
            }
            return rooms;
        }

        public RoomBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public RoomBuilder setCapacity(long capacity) {
            this.capacity = capacity;
            return this;
        }

        public RoomBuilder setRent(long rent) {
            this.rent = rent;
            return this;
        }

        public RoomBuilder setOccupants(List<Occupant> occupants) {
            this.occupants = occupants;
            return this;
        }

        public RoomBuilder setProperty(Property property) {
            this.property = property;
            return this;
        }

        public Room build() {
            return new Room(
                    NullableOptional(this.id),
                    NullableOptional(this.capacity),
                    NullableOptional(this.rent),
                    NullableOptional(this.occupants),
                    this.property == null ? Optional.empty() : NullableOptional(this.property)
            );
        }

        public Room build(RoomEntity entity) {
            this.id = entity.getId();
            this.capacity = entity.getCapacity();
            this.rent = entity.getRent();
            this.occupants = OccupantBuilder.listFrom(entity.getOccupants());
//            this.property = new PropertyBuilder().build(entity.getProperty());
            return this.build();
        }
    }

    public static class OccupantBuilder {

        @Getter
        private long id;
        @Getter
        private String firstName;
        @Getter
        private String lastName;
        @Getter
        private String phoneNumber;
        @Getter
        private Date dateMovedIn;
        @Getter
        private Date timeRentLastPaid;
        @Getter
        private Room room;
        @Getter
        private long rent;
        @Getter
        private Date dateRentDue;
        @Getter
        private List<ParkingSpot> parkingSpots;

        public static List<Occupant> listFrom(Iterable<OccupantEntity> entities) {
            List<Occupant> occupants = new LinkedList<>();
            if (entities != null) {
                for (OccupantEntity entity : entities) {
                    Occupant build = new OccupantBuilder().build(entity);
                    occupants.add(build);
                }
            }
            return occupants;
        }

        public OccupantBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public OccupantBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public OccupantBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public OccupantBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public OccupantBuilder setDateMovedIn(Date dateMovedIn) {
            this.dateMovedIn = dateMovedIn;
            return this;
        }

        public OccupantBuilder setTimeRentLastPaid(Date timeRentLastPaid) {
            this.timeRentLastPaid = timeRentLastPaid;
            return this;
        }

        public OccupantBuilder setRoom(Room room) {
            this.room = room;
            return this;
        }

        public OccupantBuilder setRent(long rent) {
            this.rent = rent;
            return this;
        }

        public OccupantBuilder setDateRentDue(Date dateRentDue) {
            this.dateRentDue = dateRentDue;
            return this;
        }

        public OccupantBuilder setParkingSpots(List<ParkingSpot> parkingSpots) {
            this.parkingSpots = parkingSpots;
            return this;
        }

        public Occupant build() {
            return new Occupant(
                    NullableOptional(this.id),
                    NullableOptional(this.firstName),
                    NullableOptional(this.lastName),
                    NullableOptional(this.phoneNumber),
                    NullableOptional(this.dateMovedIn),
                    NullableOptional(this.timeRentLastPaid),
                    this.room == null ? Optional.empty() : NullableOptional(this.room),
                    NullableOptional(this.rent),
                    NullableOptional(this.dateRentDue),
                    NullableOptional(this.parkingSpots)
            );
        }

        public Occupant build(OccupantEntity occupant) {
            this.id = occupant.getId();
            this.firstName = occupant.getFirstName();
            this.lastName = occupant.getLastName();
            this.phoneNumber = occupant.getPhoneNumber();
            this.dateMovedIn = occupant.getDateMovedIn();
            this.timeRentLastPaid = occupant.getTimeLastRentPaid();
//            this.room = new RoomBuilder().build(occupant.getRoom());
            this.rent = occupant.getRent();
            this.dateRentDue = occupant.getDateRentDue();
            this.parkingSpots = ParkingSpotBuilder.listFrom(occupant.getParkingSpots());
            return this.build();
        }
    }

    public static class ParkingSpotBuilder {
        private long id;
        private boolean electric;
        private ParkingSpotEntity.PARKING_SIZE parkingSize;
        private ParkingSpotEntity.PARKING_TYPE parkingType;
        private int price;
        private Building building;
        private Property property;
        private Occupant occupant;

        public static List<ParkingSpot> listFrom(Iterable<ParkingSpotEntity> entities) {
            List<ParkingSpot> parkingSpots = new LinkedList<>();
            if (entities != null) {
                for (ParkingSpotEntity entity : entities) {
                    ParkingSpot build = new ParkingSpotBuilder().build(entity);
                    parkingSpots.add(build);
                }
            }
            return parkingSpots;
        }

        public ParkingSpotBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public ParkingSpotBuilder setElectric(boolean electric) {
            this.electric = electric;
            return this;
        }

        public ParkingSpotBuilder setParkingSize(ParkingSpotEntity.PARKING_SIZE parkingSize) {
            this.parkingSize = parkingSize;
            return this;
        }

        public ParkingSpotBuilder setParkingType(ParkingSpotEntity.PARKING_TYPE parkingType) {
            this.parkingType = parkingType;
            return this;
        }

        public ParkingSpotBuilder setPrice(int price) {
            this.price = price;
            return this;
        }

        public ParkingSpotBuilder setBuilding(Building building) {
            this.building = building;
            return this;
        }

        public ParkingSpotBuilder setProperty(Property property) {
            this.property = property;
            return this;
        }

        public ParkingSpotBuilder setOccupant(Occupant occupant) {
            this.occupant = occupant;
            return this;
        }

        public ParkingSpot build() {
            return new ParkingSpot(
                    NullableOptional(this.id),
                    NullableOptional(this.electric),
                    NullableOptional(this.parkingSize),
                    NullableOptional(this.parkingType),
                    NullableOptional(this.price),
                    this.building == null ? Optional.empty() : NullableOptional(this.building),
                    this.property == null ? Optional.empty() : NullableOptional(this.property),
                    this.occupant == null ? Optional.empty() : NullableOptional(this.occupant)
            );
        }

        public ParkingSpot build(ParkingSpotEntity entity) {
            if (entity == null) return null;
            this.id = entity.getId();
            this.electric = entity.isElectric();
            this.parkingSize = entity.getParkingSize();
            this.parkingType = entity.getParkingType();
            this.price = entity.getPrice();
//            this.building = new BuildingBuilder().build(entity.getBuilding());
//            this.property = new PropertyBuilder().build(entity.getProperty());
//            this.occupant = new OccupantBuilder().build(entity.getOccupant());
            return this.build();
        }

    }

    public static class SellerBuilder {

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
        private List<Property> properties;

        public static List<Seller> listFrom(Iterable<SellerEntity> entities) {
            List<Seller> sellers = new LinkedList<>();
            if (entities != null) {
                for (SellerEntity entity : entities) {
                    Seller build = new SellerBuilder().build(entity);
                    sellers.add(build);
                }
            }
            return sellers;
        }

        public SellerBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public SellerBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public SellerBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public SellerBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public SellerBuilder setSellerType(SellerEntity.SELLER_TYPE sellerType) {
            this.sellerType = sellerType;
            return this;
        }

        public SellerBuilder setProperties(List<Property> properties) {
            this.properties = properties;
            return this;
        }

        public Seller build() {

            return new Seller(
                    NullableOptional(this.id),
                    NullableOptional(this.firstName),
                    NullableOptional(this.lastName),
                    NullableOptional(this.phoneNumber),
                    NullableOptional(this.sellerType),
                    NullableOptional(Objects.requireNonNullElseGet(this.properties, LinkedList::new))
            );
        }

        public Seller build(SellerEntity entity) {
            this.id = entity.getId();
            this.firstName = entity.getFirstName();
            this.lastName = entity.getLastName();
            this.phoneNumber = entity.getPhoneNumber();
            this.sellerType = entity.getSellerType();
//            this.properties = PropertyBuilder.listFrom(entity.getPropertyEntities());
            return this.build();
        }

    }

    public static class AmenitiesBuilder {

        @Getter
        private long id;
        @Getter
        private boolean gasPipeline;
        @Getter
        private boolean swimmingPool;
        @Getter
        private boolean gym;
        @Getter
        private boolean lift;
        @Getter
        private boolean gatedCommunity;
        @Getter
        private boolean parking;
        @Getter
        private boolean petsAllowed;
        @Getter
        private List<Property> properties;

        public static List<Amenities> listFrom(Iterable<AmenitiesEntity> entities) {
            List<Amenities> amenitiess = new LinkedList<>();
            if (entities != null) {
                for (AmenitiesEntity entity : entities) {
                    Amenities build = new AmenitiesBuilder().build(entity);
                    amenitiess.add(build);
                }
            }
            return amenitiess;
        }

        public AmenitiesBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public AmenitiesBuilder setGasPipeline(boolean gasPipeline) {
            this.gasPipeline = gasPipeline;
            return this;
        }

        public AmenitiesBuilder setSwimmingPool(boolean swimmingPool) {
            this.swimmingPool = swimmingPool;
            return this;
        }

        public AmenitiesBuilder setGym(boolean gym) {
            this.gym = gym;
            return this;
        }

        public AmenitiesBuilder setLift(boolean lift) {
            this.lift = lift;
            return this;
        }

        public AmenitiesBuilder setGatedCommunity(boolean gatedCommunity) {
            this.gatedCommunity = gatedCommunity;
            return this;
        }

        public AmenitiesBuilder setParking(boolean parking) {
            this.parking = parking;
            return this;
        }

        public AmenitiesBuilder setPetsAllowed(boolean petsAllowed) {
            this.petsAllowed = petsAllowed;
            return this;
        }

        public AmenitiesBuilder setProperties(List<Property> properties) {
            this.properties = properties;
            return this;
        }

        public Amenities build() {
            return new Amenities(
                    NullableOptional(this.id),
                    NullableOptional(this.gasPipeline),
                    NullableOptional(this.swimmingPool),
                    NullableOptional(this.gym),
                    NullableOptional(this.lift),
                    NullableOptional(this.gatedCommunity),
                    NullableOptional(this.parking),
                    NullableOptional(this.petsAllowed),
                    NullableOptional(Objects.requireNonNullElseGet(this.properties, LinkedList::new))
            );
        }

        public Amenities build(AmenitiesEntity entity) {
            this.id = entity.getId();
            this.gasPipeline = entity.isGasPipeline();
            this.swimmingPool = entity.isSwimmingPool();
            this.gym = entity.isGym();
            this.lift = entity.isLift();
            this.gasPipeline = entity.isGatedCommunity();
            this.parking = entity.isParking();
            this.petsAllowed = entity.isPetsAllowed();
//            this.properties = PropertyBuilder.listFrom(entity.getProperties());
            return this.build();
        }

    }

}
