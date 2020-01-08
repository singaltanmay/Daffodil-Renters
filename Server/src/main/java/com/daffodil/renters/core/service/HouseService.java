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

    // TODO
    public List<House> getHousesWithin(double latitude, double longitude, double distance) {
        GeoLocationService location = GeoLocationService.fromDegrees(latitude, longitude);
        GeoLocationService[] boundingCoordinates = location.boundingCoordinates(distance, null);

        double minLat = boundingCoordinates[0].getLatitudeInDegrees();
        double minLon = boundingCoordinates[0].getLongitudeInDegrees();
        double maxLat = boundingCoordinates[1].getLatitudeInDegrees();
        double maxLon = boundingCoordinates[1].getLongitudeInDegrees();

        String query = "select " +
                "house0_.id as id1_0_, " +
                "house0_.address as address2_0_, " +
                "house0_.latitude as latitude3_0_, " +
                "house0_.longitude as longitud4_0_ " +
                "from house house0_ " +
                "where " +
                "house0_.latitude < " +
                maxLat +
                " and " +
                "house0_.latitude > " +
                minLat +
                " and " +
                "house0_.longitude < " +
                maxLon +
                " and " +
                "house0_.longitude > " +
                minLon +
                ";";

        return null;
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

}
