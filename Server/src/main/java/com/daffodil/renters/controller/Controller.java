package com.daffodil.renters.controller;

import com.daffodil.renters.core.model.House;
import com.daffodil.renters.core.model.Room;
import com.daffodil.renters.core.service.HouseService;
import com.daffodil.renters.core.service.OccupantService;
import com.daffodil.renters.core.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final OccupantService occupantService;

    @Autowired
    public Controller(HouseService houseService, RoomService roomService, OccupantService occupantService) {
        this.houseService = houseService;
        this.roomService = roomService;
        this.occupantService = occupantService;
    }

    @GetMapping(value = "house")
    public ResponseEntity<?> getHouseById(@RequestParam("id") Optional<Long> id) {
        if (id.isPresent()) {
            Optional<House> house = houseService.getHouseById(id.get());
            return new ResponseEntity<>(house.orElse(null), HttpStatus.OK);
        } else return new ResponseEntity<>(houseService.getAllHouses(), HttpStatus.OK);
    }

    @PostMapping(path = "/house")
    public void insertHouse(@RequestBody House house) {
        Logger logger = LoggerFactory.getLogger(Controller.class);
        logger.info(Double.toString(house.getLatitude()));
        houseService.insertHouse(house);
    }

    @PostMapping(path = "/test")
    public void test() {
//        roomService.getRoomById((byte) 0,0);
        List<Room> rentBetween = roomService.findRentBetween(0, 10000);

        for (Room fes : rentBetween) {
            System.out.println(fes.getId());
        }
    }

}
