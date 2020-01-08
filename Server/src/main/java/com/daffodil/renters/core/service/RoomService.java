package com.daffodil.renters.core.service;

import com.daffodil.renters.core.model.beans.House;
import com.daffodil.renters.core.model.beans.Room;
import com.daffodil.renters.core.model.entities.HouseEntity;
import com.daffodil.renters.core.model.entities.RoomEntity;
import com.daffodil.renters.core.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Iterable<RoomEntity> all = roomRepository.findAll();
        List<Room> list = new LinkedList();
        for (RoomEntity entity : all) {
            list.add(new Room.Builder().build(entity));
        }
        return list;
    }

    public List<Room> getAllRooms(long houseId) {
        Optional<House> house = houseService.getHouseById(houseId);
        if (house.isPresent()) {
            return house.get().getRooms();
        } else return new LinkedList<>();
    }

    public Optional<Room> getRoomById(short room_id, long house_id) {
        return roomRepository.getRoomById(room_id, house_id).map(room -> new Room.Builder().build(room));
    }

    public Optional<Room> getRoomById(byte room_id, House house) {
        return house.getRooms().stream().filter(r -> r.getId() == room_id).findFirst();
    }

    public List<Room> findRentBetween(long from, long to) {
        List<RoomEntity> allByRentBetween = roomRepository.findAllByRentBetween(from, to);
        return allByRentBetween.stream().map(entity -> new Room.Builder().build(entity)).collect(Collectors.toList());
    }
}
