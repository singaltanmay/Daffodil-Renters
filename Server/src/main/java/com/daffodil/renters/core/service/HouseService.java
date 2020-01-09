package com.daffodil.renters.core.service;

import com.daffodil.renters.core.model.beans.Filter;
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

    public void test() {
//        manager.getEntityManagerFactory();
//        new FilteredQueries().filteredHouses(new Filter());
    }

    // Helper method that injects child rooms into a iterable of HouseEntities
    //TODO implement EntityManager??
    public Object getHousesFiltered(Map<String, String> params) {

        Long SELECT_ID = null;

        if (params.containsKey("id")) {
            SELECT_ID = Long.parseLong(params.get("id"));
        }

        StringBuilder query = new StringBuilder();
        query.append("SELECT ");

        short cols = 0;

        if (SELECT_ID != null) {
            query.append("id");
            cols++;
        }

        if (cols == 0) query.append("*");

        return null;
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

    private List<House> roomsInjector(Iterable<HouseEntity> entities) {
        List<House> houses = House.listFrom(entities);
        houses.forEach(house -> house.setRooms(roomService.getAllRooms(house.getId())));
        return houses;
    }

    private class QueryUtils {

        EntityManagerFactory factory;
        EntityManager manager;

        public QueryUtils(EntityManagerFactory factory) {
            this.factory = factory;
        }

        public List<HouseEntity> filteredHouses(Filter filter) {
            Query query = trn().createQuery("SELECT h FROM HouseEntity AS h WHERE h.id=1", HouseEntity.class);
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
