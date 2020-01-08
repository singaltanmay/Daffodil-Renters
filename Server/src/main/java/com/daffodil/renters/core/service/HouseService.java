package com.daffodil.renters.core.service;

import com.daffodil.renters.core.model.beans.House;
import com.daffodil.renters.core.model.beans.Room;
import com.daffodil.renters.core.model.entities.HouseEntity;
import com.daffodil.renters.core.repo.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public HouseService(HouseRepository repository, RoomService roomService) {
        this.repository = repository;
        this.roomService = roomService;
    }

    public List<House> getHousesWithin(double latitude, double longitude, double distance) {
        GeoLocationService location = GeoLocationService.fromDegrees(latitude, longitude);
        GeoLocationService[] boundingCoordinates = location.boundingCoordinates(distance, null);

        double minLat = boundingCoordinates[0].getLatitudeInDegrees();
        double minLon = boundingCoordinates[0].getLongitudeInDegrees();
        double maxLat = boundingCoordinates[1].getLatitudeInDegrees();
        double maxLon = boundingCoordinates[1].getLongitudeInDegrees();

        List<HouseEntity> houseEntities = repository.getAllHousesWithinCoordinates(minLat, minLon, maxLat, maxLon);

        List<House> houses = new LinkedList<>();
        for (HouseEntity entity : houseEntities) {
            House build = new House.Builder().build(entity);
            build.setRooms(roomService.getAllRooms(build.getId()));
            houses.add(build);
        }

        return houses;
    }

    public List<House> getAllHouses() {
        Iterable<HouseEntity> all = repository.findAll();
        List<House> houses = new LinkedList<>();
        for (HouseEntity entity : all) {
            House build = new House.Builder().build(entity);
            build.setRooms(roomService.getAllRooms(build.getId()));
            houses.add(build);
        }
        return houses;
    }

    public Optional<House> getHouseById(long id) {
        HouseEntity houseEntity = repository.findHouseById(id);
        if (houseEntity != null) {
            House build = new House.Builder().build(houseEntity);
            build.setRooms(roomService.getAllRooms(build.getId()));
            return Optional.of(build);
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
}
