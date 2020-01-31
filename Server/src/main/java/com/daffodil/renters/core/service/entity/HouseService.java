//package com.daffodil.renters.core.service;
//
//import com.daffodil.renters.core.model.beans.postables.Building;
//import com.daffodil.renters.core.model.beans.postables.Room;
//import com.daffodil.renters.core.model.entities.*;
//import com.daffodil.renters.core.repo.HouseRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Query;
//import javax.transaction.Transactional;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class HouseService {
//
//    private final HouseRepository houseRepository;
//
//    private final RoomService roomService;
//    private final ParkingSpotService parkingSpotService;
//
//    @Autowired
//    EntityManagerFactory factory;
//
//    public HouseService(HouseRepository houseRepository, RoomService roomService, ParkingSpotService parkingSpotService) {
//        this.houseRepository = houseRepository;
//        this.roomService = roomService;
//        this.parkingSpotService = parkingSpotService;
//    }
//
//    public List<Building> getAllHouses(int page) {
//        return foreignRelationsInjector(houseRepository.findAll());
//    }
//
//    public Optional<Building> getHouseById(long id) {
//        PropertyEntity houseById = houseRepository.findHouseById(id);
//        if (houseById != null) {
//            List<Building> buildings = foreignRelationsInjector(List.of(houseById));
//            if (buildings.isEmpty() || buildings.get(0) == null) return Optional.empty();
//            else return Optional.of(buildings.get(0));
//        } else return Optional.empty();
//    }
//
//    public List<Building> getHousesUsingFilteredQuery(Building.Filter filter) {
//        return foreignRelationsInjector(new QueryUtils(factory).executeFilteredQuery(filter));
//    }
//
//    @Transactional
//    public void insertHouse(Building building) {
//        PropertyEntity build = new EntityFactory.PropertyEntityBuilder().build(building);
//        occupantParkingSpotsHouseInjector(build);
//        houseRepository.save(build);
//    }
//
//    @Transactional
//    public void updateHouseById(long houseId, Building building) {
//        if (!houseRepository.existsById(houseId)) {
//            return;
//        }
//        roomService.deleteAllRoomsOfHouse(houseId);
//
//        List<Room> rooms = building.getRooms();
//        rooms.forEach(room -> {
//            roomService.insertRoom(room, houseId);
//        });
//
//        houseRepository.updateHouseById(
//                houseId,
//                building.getAddress(),
//                building.getLatitude(),
//                building.getLongitude()
//        );
//    }
//
//    @Transactional
//    public void deleteAllHouses() {
//        houseRepository.deleteAll();
//    }
//
//    @Transactional
//    public void deleteHouseById(long id) {
//        if (houseRepository.existsById(id)) houseRepository.deleteById(id);
//    }
//
//    private void occupantParkingSpotsHouseInjector(PropertyEntity build) {
//        for (RoomEntity roomEntity : build.getRooms()) {
//            for (OccupantEntity occupantEntity : roomEntity.getOccupants()) {
//                for (ParkingSpotEntity parkingSpotEntity : occupantEntity.getParkingSpots()) {
//                    parkingSpotEntity.setBuilding(build);
//                }
//            }
//        }
//    }
//
//    private List<Building> foreignRelationsInjector(Iterable<PropertyEntity> entities) {
//        List<Building> buildings = Building.listFrom(entities);
//        buildings.forEach(house -> {
//            long houseId = house.getId();
//            house.setRooms(roomService.getAllRoomsByHouseId(houseId));
//            house.setParkingSpots(parkingSpotService.getAllParkingSpotsByHouseId(houseId));
//        });
//        return buildings;
//    }
//
//    // Debug method used to get query string
//    public String getFilteredQueryGeneratedString(Building.Filter filter) {
//        return new QueryUtils(factory).createQueryFromFilter(filter);
//    }
//
//    private class QueryUtils {
//
//        private EntityManagerFactory factory;
//        private EntityManager manager;
//
//        public QueryUtils(EntityManagerFactory factory) {
//            this.factory = factory;
//        }
//
//        public String createQueryFromFilter(Building.Filter filter) {
//            String COLUMN_ID = "h.id";
//            String COLUMN_ADDRESS = "h.address";
//            String COLUMN_LATITUDE = "h.latitude";
//            String COLUMN_LONGITUDE = "h.longitude";
//
//            StringBuilder builder = new StringBuilder("SELECT h FROM HouseEntity AS h");
//
//            Optional<Long> id = filter.getId();
//            Optional<String> address = filter.getAddress();
//            Optional<Double> latitude = filter.getLatitude();
//            Optional<Double> longitude = filter.getLongitude();
//            Optional<Double> rangeKm = filter.getRangeKm();
//
//            boolean unfiltered = filter.isUnfiltered();
//
//            boolean idPresent = id.isPresent();
//            boolean addressPresent = address.isPresent();
//            boolean latitudePresent = latitude.isPresent();
//            boolean longitudePresent = longitude.isPresent();
//            boolean rangeKmPresent = rangeKm.isPresent();
//
//
//            boolean not_first_entry = false;
//
//            // WHERE clause builder
//            if (!unfiltered) {
//                builder.append(" WHERE ");
//                if (idPresent) {
//                    builder.append(COLUMN_ID + " = " + id.get().toString());
//                    not_first_entry = true;
//                }
//
//                if (addressPresent) {
//                    if (not_first_entry) {
//                        builder.append(" AND ");
//                    } else not_first_entry = true;
//                    builder.append(COLUMN_ADDRESS + " = " + "'" + address.get() + "'");
//                }
//
//                if (latitudePresent && longitudePresent) {
//
//                    if (rangeKmPresent) {
//
//                        GeoLocationService location = GeoLocationService.fromDegrees(latitude.get(), longitude.get());
//                        GeoLocationService[] boundingCoordinates = location.boundingCoordinates(rangeKm.get(), null);
//
//                        Double minLat = boundingCoordinates[0].getLatitudeInDegrees();
//                        Double minLon = boundingCoordinates[0].getLongitudeInDegrees();
//                        Double maxLat = boundingCoordinates[1].getLatitudeInDegrees();
//                        Double maxLon = boundingCoordinates[1].getLongitudeInDegrees();
//
//                        if (not_first_entry) {
//                            builder.append(" AND ");
//                        } else not_first_entry = true;
//                        builder.append(COLUMN_LATITUDE + " >= " + minLat.toString());
//                        builder.append(" AND ");
//                        builder.append(COLUMN_LATITUDE + " <= " + maxLat.toString());
//                        builder.append(" AND ");
//                        builder.append(COLUMN_LONGITUDE + " >= " + minLon.toString());
//                        builder.append(" AND ");
//                        builder.append(COLUMN_LONGITUDE + " <= " + maxLon.toString());
//                    } else {
//                        if (not_first_entry) {
//                            builder.append(", ");
//                        } else not_first_entry = true;
//                        builder.append(COLUMN_LATITUDE + " = " + latitude.get().toString());
//                        builder.append(" AND ");
//                        builder.append(COLUMN_LONGITUDE + " = " + longitude.get().toString());
//                    }
//                }
//            }
//
//            return builder.toString();
//        }
//
//        public List<PropertyEntity> executeFilteredQuery(Building.Filter filter) {
//            Query query = trn().createQuery(createQueryFromFilter(filter), PropertyEntity.class);
//            List<PropertyEntity> resultList = query.getResultList();
//            cmt();
//            return resultList;
//        }
//
//        private EntityManager trn() {
//            manager = factory.createEntityManager();
//            manager.getTransaction().begin();
//            return manager;
//        }
//
//        private void cmt() {
//            manager.getTransaction().commit();
//            manager.close();
//        }
//
//    }
//
//}
