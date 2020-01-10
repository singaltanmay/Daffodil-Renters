package com.daffodil.renters.core.service;

import com.daffodil.renters.core.model.beans.Occupant;
import com.daffodil.renters.core.model.beans.Room;
import com.daffodil.renters.core.model.entities.RoomEntity;
import com.daffodil.renters.core.repo.HouseRepository;
import com.daffodil.renters.core.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    HouseRepository houseRepository;
    RoomRepository roomRepository;
    OccupantService occupantService;

    @Autowired
    public RoomService(HouseRepository houseRepository, RoomRepository roomRepository, OccupantService occupantService) {
        this.houseRepository = houseRepository;
        this.roomRepository = roomRepository;
        this.occupantService = occupantService;
    }

    public List<Room> findRentBetween(long from, long to) {
        List<RoomEntity> allByRentBetween = roomRepository.findAllByRentBetween(from, to);
        return allByRentBetween.stream().map(entity -> new Room.Builder().build(entity)).collect(Collectors.toList());
    }

    public List<Room> getAllRoomsByHouseId() {
        return occupantsInjector(roomRepository.findAll());
    }

    public List<Room> getAllRoomsByHouseId(long houseId) {
        return occupantsInjector(roomRepository.findRoomByHouseId(houseId));
    }

    public Optional<Room> getRoomById(long room_id, long house_id) {
        Optional<RoomEntity> room = roomRepository.getRoomById(room_id, house_id);
        if (room.isPresent()) {
            List<Room> rooms = occupantsInjector(List.of(room.get()));
            if (rooms.size() > 0) {
                return Optional.of(rooms.get(0));
            }
        }
        return Optional.empty();
    }

    public void insertRoom(Room room, long house_id) {
        RoomEntity build = new RoomEntity.Builder().build(room);
        build.setHouse(houseRepository.findHouseById(house_id));
        roomRepository.save(build);
    }

    public void updateRoomById(long roomId, Room room) {
        if (!roomRepository.existsById(roomId)) return;
        occupantService.deleteAllOccupantsOfRoom(roomId);

        List<Occupant> occupants = room.getOccupants();
        occupants.forEach(occupant -> {
            occupantService.insertOccupant(room, roomId);
        });

        roomRepository.updateRoomById(
                roomId,
                room.getCapacity(),
                room.getRent()
        );

    }


    public void deleteAllRooms() {
        roomRepository.deleteAll();
    }

    public void deleteAllRoomsOfHouse(long id) {
        roomRepository.deleteByHouseId(id);
    }

    public void deleteRoomById(long room_id) {
        if (roomRepository.existsById(room_id)) roomRepository.deleteById(room_id);
    }

    private List<Room> occupantsInjector(Iterable<RoomEntity> entities) {
        List<Room> rooms = Room.listFrom(entities);
        rooms.forEach(room -> room.setOccupants(occupantService.getAllOccupants(room.getId())));
        return rooms;
    }

}
