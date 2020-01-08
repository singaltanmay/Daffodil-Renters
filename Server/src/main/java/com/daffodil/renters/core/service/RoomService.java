package com.daffodil.renters.core.service;

import com.daffodil.renters.core.model.beans.House;
import com.daffodil.renters.core.model.beans.Room;
import com.daffodil.renters.core.model.entities.HouseEntity;
import com.daffodil.renters.core.model.entities.RoomEntity;
import com.daffodil.renters.core.repo.HouseRepository;
import com.daffodil.renters.core.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    HouseRepository houseRepository;
    RoomRepository roomRepository;

    @Autowired
    public RoomService(HouseRepository houseRepository, RoomRepository roomRepository) {
        this.houseRepository = houseRepository;
        this.roomRepository = roomRepository;
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

        List<RoomEntity> roomEntities = roomRepository.findByHouseId(houseId);

        List<Room> rooms = new LinkedList<>();

        if (roomEntities != null) {
            for (RoomEntity e : roomEntities) {
                Room build = new Room.Builder().build(e);
                if (build != null) {
                    rooms.add(build);
                }
            }
        }

        return rooms;
    }

    public Optional<Room> getRoomById(long room_id, long house_id) {
        return roomRepository.getRoomById(room_id, house_id).map(room -> new Room.Builder().build(room));
    }

    public Optional<Room> getRoomById(long room_id, House house) {
        return house.getRooms().stream().filter(r -> r.getId() == room_id).findFirst();
    }

    public void insertRoom(Room room, long house_id) {
        RoomEntity build = new RoomEntity.Builder().build(room);
        build.setHouse(houseRepository.findHouseById(house_id));
        roomRepository.save(build);
    }

    public void deleteRoomById(long room_id) {
        if (roomRepository.existsById(room_id)) roomRepository.deleteById(room_id);
    }

    public void deleteAllRoomsOfHouse(long id) {
        roomRepository.deleteByHouseId(id);
    }

    public List<Room> findRentBetween(long from, long to) {
        List<RoomEntity> allByRentBetween = roomRepository.findAllByRentBetween(from, to);
        return allByRentBetween.stream().map(entity -> new Room.Builder().build(entity)).collect(Collectors.toList());
    }
}
