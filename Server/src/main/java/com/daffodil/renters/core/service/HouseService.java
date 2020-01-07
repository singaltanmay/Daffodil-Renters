package com.daffodil.renters.core.service;

import com.daffodil.renters.core.model.beans.House;
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

    @Autowired
    public HouseService(HouseRepository repository) {
        this.repository = repository;
    }

    public List<HouseEntity> getAllHouses() {
        Iterable<HouseEntity> all = repository.findAll();
        List<HouseEntity> houseEntities = new LinkedList<>();
        all.forEach(houseEntities::add);
        return houseEntities;
    }

    public Optional<HouseEntity> getHouseById(long id) {
        return repository.findById(id);
    }

    public List<HouseEntity> getHousesWithin(double latitude, double longitude, double distance) {
        GeoLocationService location = GeoLocationService.fromDegrees(latitude, longitude);
        GeoLocationService[] boundingCoordinates = location.boundingCoordinates(distance, null);

        double minLat = boundingCoordinates[0].getLatitudeInDegrees();
        double minLon = boundingCoordinates[0].getLongitudeInDegrees();
        double maxLat = boundingCoordinates[1].getLatitudeInDegrees();
        double maxLon = boundingCoordinates[1].getLongitudeInDegrees();

        String query = "select house0_.id as id1_0_, house0_.address as address2_0_, house0_.latitude as latitude3_0_, house0_.longitude as longitud4_0_ " +
                "from house house0_ " +
                "where " +
                "house0_.latitude < " + maxLat +
                " and " +
                "house0_.latitude > " + minLat +
                " and " +
                "house0_.longitude < " + maxLon +
                " and " +
                "house0_.longitude > " + minLon +
                ";";

        return null;
    }

    @Transactional
    public void insertHouse(House house) {
//        // bulider pattern
//        House shah = new House("shah", 12, 12);
//
//        List<Room> rooms = List.of(new Room((short) 12, 12000).setHouse(shah), new Room((short) 12, 12000).setHouse(shah));
//        House houseRoom = shah.setRooms(rooms);

        //TODO fix!!

//        HouseInsertable.Builder()
//
//        repository.save(houseRoom);
    }

    private void insertHouseById(long id, HouseEntity houseEntity) {
        if (!repository.existsById(id)) {
            HouseEntity houseEntity1 = new HouseEntity(id, houseEntity.getAddress(), houseEntity.getLatitude(), houseEntity.getLongitude(), houseEntity.getRoomEntities());
            repository.save(houseEntity1);
        }
    }

    public void updateHouseById(long id, HouseEntity houseEntity) {
        if (repository.existsById(id)) {
            deleteHouseById(id);
            insertHouseById(id, houseEntity);
        }
    }

    private void deleteAllHouses() {
        repository.deleteAll();
    }

    private void deleteHouseById(long id) {
        if (repository.existsById(id)) repository.deleteById(id);
    }

}
