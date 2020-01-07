package com.daffodil.renters.core.service;

import com.daffodil.renters.core.model.House;
import com.daffodil.renters.core.model.Room;
import com.daffodil.renters.core.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Optional<Room> getRoomById(short room_id, long house_id) {
        return roomRepository.getRoomById(room_id, house_id);
    }

    public Optional<Room> getRoomById(byte room_id, House house) {
        return house.getRooms().stream().filter(r -> r.getId() == room_id).findFirst();
    }

    public List<Room> findRentBetween(long from, long to) {
        return roomRepository.findAllByRentBetween(from, to);
    }

}
