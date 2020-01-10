package com.daffodil.renters.controller;

import com.daffodil.renters.core.model.beans.House;
import com.daffodil.renters.core.model.beans.Room;
import com.daffodil.renters.core.service.HouseService;
import com.daffodil.renters.core.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("daffodil/api")
@RestController
public class Controller {

    private final HouseService houseService;
    private final RoomService roomService;

    @Autowired
    public Controller(HouseService houseService, RoomService roomService) {
        this.houseService = houseService;
        this.roomService = roomService;
    }

    @GetMapping(value = "house")
    public ResponseEntity<?> getHouseById(@RequestParam("id") Optional<Long> id, @RequestParam("page") Optional<Integer> page) {
        if (id.isPresent()) {
            Optional<House> house = houseService.getHouseById(id.get());
            return new ResponseEntity<>(house.orElse(null), HttpStatus.OK);
        } else return new ResponseEntity<>(houseService.getAllHouses(page.orElse(1)), HttpStatus.OK);
    }

    // Kinda like a GET method but used POST to get params as a JSON file
    @PostMapping(value = "house")
    public List<House> getHousesFiltered(@RequestBody Optional<House.Filter> filter, @RequestParam("page") Optional<Integer> page) {
        if (filter.isEmpty()) return houseService.getAllHouses(page.orElse(1));
        else return houseService.getHousesUsingFilteredQuery(filter.get());
    }

    @GetMapping(value = "room")
    public ResponseEntity<?> getRoomById(@RequestParam("id") Optional<Long> id, @RequestParam("page") Optional<Integer> page) {
        if (id.isPresent()) {
            Optional<Room> room = roomService.getRoomById(id.get());
            return new ResponseEntity<>(room.orElse(null), HttpStatus.OK);
        } else return new ResponseEntity<>(roomService.getAllRooms(page.orElse(1)), HttpStatus.OK);
    }

    // Kinda like a GET method but used POST to get params as a JSON file
    @PostMapping(value = "room")
    public List<Room> getRoomsFiltered(@RequestBody Optional<Room.Filter> filter, @RequestParam("page") Optional<Integer> page) {
        if (filter.isEmpty()) return roomService.getAllRooms(page.orElse(1));
        else return roomService.getRoomsUsingFilteredQuery(filter.get());
    }

}
