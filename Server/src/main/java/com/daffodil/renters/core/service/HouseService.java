package com.daffodil.renters.core.service;

import com.daffodil.renters.core.model.beans.House;
import com.daffodil.renters.core.model.beans.Room;
import com.daffodil.renters.core.model.entities.HouseEntity;
import com.daffodil.renters.core.repo.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HouseService {
    private final HouseRepository repository;
    private final RoomService roomService;

    @Autowired
    EntityManagerFactory factory;

    @Autowired
    public HouseService(HouseRepository repository, RoomService roomService) {
        this.repository = repository;
        this.roomService = roomService;
    }

    public String test(House.Filter filter) {
        return new QueryUtils(factory).createQueryFromFilter(filter);
    }

    public List<House> getHousesWithin(double latitude, double longitude, double distance) {
        GeoLocationService location = GeoLocationService.fromDegrees(latitude, longitude);
        GeoLocationService[] boundingCoordinates = location.boundingCoordinates(distance, null);

        double minLat = boundingCoordinates[0].getLatitudeInDegrees();
        double minLon = boundingCoordinates[0].getLongitudeInDegrees();
        double maxLat = boundingCoordinates[1].getLatitudeInDegrees();
        double maxLon = boundingCoordinates[1].getLongitudeInDegrees();

        return roomsInjector(repository.getAllHousesWithinCoordinates(minLat, minLon, maxLat, maxLon));
    }

    public List<House> getAllHouses() {
        return roomsInjector(repository.findAll());
    }

    public Optional<House> getHouseById(long id) {
        HouseEntity houseById = repository.findHouseById(id);
        if (houseById != null) {
            List<House> houses = roomsInjector(List.of(houseById));
            if (houses.isEmpty() || houses.get(0) == null) return Optional.empty();
            else return Optional.of(houses.get(0));
        } else return Optional.empty();
    }

    public List<House> getHousesUsingFilteredQuery(House.Filter filter) {
        return roomsInjector(new QueryUtils(factory).executeFilteredQuery(filter));
    }

    @Transactional
    public void insertHouse(House house) {
        repository.save(new HouseEntity.Builder().build(house));
    }

    public void updateHouseById(long id, House house) {
        if (!repository.existsById(id)) {
            return;
        }

        List<Room> rooms = house.getRooms();
        rooms.forEach(room -> {
            roomService.deleteAllRoomsOfHouse(id);
            roomService.insertRoom(room, id);
        });

        repository.updateHouseById(
                id,
                house.getAddress(),
                house.getLatitude(),
                house.getLongitude()
        );
    }

    public void deleteAllHouses() {
        repository.deleteAll();
    }

    public void deleteHouseById(long id) {
        if (repository.existsById(id)) repository.deleteById(id);
    }

    // Helper method that injects child rooms into a iterable of HouseEntities
    private List<House> roomsInjector(Iterable<HouseEntity> entities) {
        List<House> houses = House.listFrom(entities);
        houses.forEach(house -> house.setRooms(roomService.getAllRooms(house.getId())));
        return houses;
    }

    private class QueryUtils {

        private EntityManagerFactory factory;
        private EntityManager manager;

        public QueryUtils(EntityManagerFactory factory) {
            this.factory = factory;
        }

        public String createQueryFromFilter(House.Filter filter) {
            String COLUMN_ID = "h.id";
            String COLUMN_ADDRESS = "h.address";
            String COLUMN_LATITUDE = "h.latitude";
            String COLUMN_LONGITUDE = "h.longitude";

            StringBuilder builder = new StringBuilder("SELECT h FROM HouseEntity AS h");

            Optional<Long> id = filter.getId();
            Optional<String> address = filter.getAddress();
            Optional<Double> latitude = filter.getLatitude();
            Optional<Double> longitude = filter.getLongitude();
            Optional<Double> rangeKm = filter.getRangeKm();

            boolean unfiltered = filter.isUnfiltered();

            boolean idPresent = id.isPresent();
            boolean addressPresent = address.isPresent();
            boolean latitudePresent = latitude.isPresent();
            boolean longitudePresent = longitude.isPresent();
            boolean rangeKmPresent = rangeKm.isPresent();


            boolean not_first_entry = false;

            // WHERE clause builder
            if (!unfiltered) {
                builder.append(" WHERE ");
                if (idPresent) {
                    builder.append(COLUMN_ID + " = " + id.get().toString());
                    not_first_entry = true;
                }

                if (addressPresent) {
                    if (not_first_entry) {
                        builder.append(" AND ");
                    } else not_first_entry = true;
                    builder.append(COLUMN_ADDRESS + " = " + "'" + address.get() + "'");
                }

                if (latitudePresent && longitudePresent) {

                    if (rangeKmPresent) {

                        GeoLocationService location = GeoLocationService.fromDegrees(latitude.get(), longitude.get());
                        GeoLocationService[] boundingCoordinates = location.boundingCoordinates(rangeKm.get(), null);

                        Double minLat = boundingCoordinates[0].getLatitudeInDegrees();
                        Double minLon = boundingCoordinates[0].getLongitudeInDegrees();
                        Double maxLat = boundingCoordinates[1].getLatitudeInDegrees();
                        Double maxLon = boundingCoordinates[1].getLongitudeInDegrees();

                        if (not_first_entry) {
                            builder.append(" AND ");
                        } else not_first_entry = true;
                        builder.append(COLUMN_LATITUDE + " >= " + minLat.toString());
                        builder.append(" AND ");
                        builder.append(COLUMN_LATITUDE + " <= " + maxLat.toString());
                        builder.append(" AND ");
                        builder.append(COLUMN_LONGITUDE + " >= " + minLon.toString());
                        builder.append(" AND ");
                        builder.append(COLUMN_LONGITUDE + " <= " + maxLon.toString());
                    } else {
                        if (not_first_entry) {
                            builder.append(", ");
                        } else not_first_entry = true;
                        builder.append(COLUMN_LATITUDE + " = " + latitude.get().toString());
                        builder.append(" AND ");
                        builder.append(COLUMN_LONGITUDE + " = " + longitude.get().toString());
                    }
                }
            }

            return builder.toString();
        }

        public List<HouseEntity> executeFilteredQuery(House.Filter filter) {
            Query query = trn().createQuery(createQueryFromFilter(filter), HouseEntity.class);
            List<HouseEntity> resultList = query.getResultList();
            cmt();
            return resultList;
        }

        private EntityManager trn() {
            manager = factory.createEntityManager();
            manager.getTransaction().begin();
            return manager;
        }

        private void cmt() {
            manager.getTransaction().commit();
            manager.close();
        }

    }

}
