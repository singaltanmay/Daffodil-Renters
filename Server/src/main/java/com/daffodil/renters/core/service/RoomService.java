package com.daffodil.renters.core.service;

import com.daffodil.renters.core.model.House;
import com.daffodil.renters.core.model.Room;
import com.daffodil.renters.core.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.spi.PersistenceProvider;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    RoomRepository roomRepository;
    HouseService houseService;

    @Autowired
    public RoomService(RoomRepository roomRepository, HouseService houseService) {
        this.roomRepository = roomRepository;
        this.houseService = houseService;
    }

    public List<Room> getAllRooms() {
        Iterable<Room> all = roomRepository.findAll();
        List<Room> list = new LinkedList();
        all.forEach(list::add);
        return list;
    }

    public List<Room> getAllRooms(long houseId) {
        Optional<House> house = houseService.getHouseById(houseId);

        if (house.isPresent()) {
            return house.get().getRooms();
        } else return new LinkedList<>();
    }

    // TODO fix
    public Optional<Room> getRoomById(byte room_id, long house_id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("daffodil_renters_db");
        EntityManager manager = entityManagerFactory.createEntityManager();
        Query query = manager.createQuery("SELECT * FROM Room r");

        List<Object[]> resultList = query.getResultList();
        resultList.forEach(arr -> System.out.println(Arrays.toString(arr)));
        manager.close();

        return Optional.empty();
    }

    public Optional<Room> getRoomById(byte room_id, House house) {
        Optional<Room> first = house.getRooms().stream().filter(r -> r.getId() == room_id).findFirst();
        return first;
    }

    public long filterRentBetween(int from, int to) {
        return roomRepository.findAllByRentBetween(from, to);
    }

}
