package com.daffodil.renters.core.service;

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

    public List<RoomEntity> getAllRooms() {
        Iterable<RoomEntity> all = roomRepository.findAll();
        List<RoomEntity> list = new LinkedList();
        all.forEach(list::add);
        return list;
    }

    public List<RoomEntity> getAllRooms(long houseId) {
        Optional<HouseEntity> house = houseService.getHouseById(houseId);

        if (house.isPresent()) {
            return house.get().getRoomEntities();
        } else return new LinkedList<>();
    }

    // TODO fix
    public Optional<RoomEntity> getRoomById(short room_id, long house_id) {
        return roomRepository.getRoomById(room_id, house_id);
    }

    public Optional<RoomEntity> getRoomById(byte room_id, HouseEntity houseEntity) {
        return houseEntity.getRoomEntities().stream().filter(r -> r.getId() == room_id).findFirst();
    }

    public List<RoomEntity> findRentBetween(long from, long to) {
        return roomRepository.findAllByRentBetween(from, to);
    }

}
