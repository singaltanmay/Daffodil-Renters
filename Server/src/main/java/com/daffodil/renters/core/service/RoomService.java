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
        Optional<HouseEntity> house = houseService.getHouseById(houseId);

        if (house.isPresent()) {
            House build = new House.Builder().build(house.get());
            return build.getRooms();
        } else return new LinkedList<>();
    }

    // TODO fix
    public Optional<RoomEntity> getRoomById(short room_id, long house_id) {
        return roomRepository.getRoomById(room_id, house_id);
    }

    public Optional<RoomEntity> getRoomById(byte room_id, HouseEntity houseEntity) {
        return houseEntity.getRooms().stream().filter(r -> r.getId() == room_id).findFirst();
    }

    public List<RoomEntity> findRentBetween(long from, long to) {
        return roomRepository.findAllByRentBetween(from, to);
    }

}
