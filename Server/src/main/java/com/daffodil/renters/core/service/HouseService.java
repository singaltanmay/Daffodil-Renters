package com.daffodil.renters.core.service;

import com.daffodil.renters.core.model.House;
import com.daffodil.renters.core.repo.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<House> getAllHouses() {
        Iterable<House> all = repository.findAll();
        List<House> houses = new LinkedList<>();
        all.forEach(houses::add);
        return houses;
    }

    public Optional<House> getHouseById(long id) {
        return repository.findById(id);
    }

    public void insertHouse(House house) {
        repository.save(house);
    }

    private void insertHouseById(long id, House house) {
        if (!repository.existsById(id)) {
            House house1 = new House(id, house.getAddress(), house.getLatitude(), house.getLongitude(), house.getRooms());
            repository.save(house1);
        }
    }

    public void updateHouseById(long id, House house) {
        if (repository.existsById(id)) {
            deleteHouseById(id);
            insertHouseById(id, house);
        }
    }

    private void deleteAllHouses() {
        repository.deleteAll();
    }

    private void deleteHouseById(long id) {
        if (repository.existsById(id)) repository.deleteById(id);
    }

}
