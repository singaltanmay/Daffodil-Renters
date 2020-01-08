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
    public ResponseEntity<?> getHouseById(@RequestParam("id") Optional<Long> id, @RequestParam("lat") Optional<Double> lat, @RequestParam("lon") Optional<Double> lon, @RequestParam("rangeKm") Optional<Double> range) {
        if (id.isPresent()) {
            Optional<House> house = houseService.getHouseById(id.get());
            return new ResponseEntity<>(house.orElse(null), HttpStatus.OK);
        } else if (lat.isPresent() && lon.isPresent() && range.isPresent()) {
            List<House> housesWithin = houseService.getHousesWithin(lat.get(), lon.get(), range.get());
            return new ResponseEntity<>(housesWithin, HttpStatus.OK);
        } else return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping(path = "/house")
    public void insertHouse(@RequestBody House insertable) {
        houseService.insertHouse(insertable);
    }

    @PutMapping(path = "/house")
    public void replaceOrInsertHouse(@RequestParam("id") long id, @RequestBody House insertable) {
        houseService.updateHouseById(id, insertable);
    }

    @DeleteMapping(value = "house")
    public void deleteHouseById(@RequestParam("id") Optional<Long> id) {
        if (id.isPresent()) {
            houseService.deleteHouseById(id.get());
        } else houseService.deleteAllHouses();
    }

    @PostMapping(path = "/test")
    public void test() {
        List<Room> rentBetween = roomService.findRentBetween(0, 10000);

        for (Room fes : rentBetween) {
            System.out.println(fes.getId());
        }
    }

}
