package com.daffodil.renters.controller;

import com.daffodil.renters.core.model.House;
import com.daffodil.renters.core.service.HouseService;
import com.daffodil.renters.core.service.OccupantService;
import com.daffodil.renters.core.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(path = "/houses")
    public List<House> getAllHouses() {
        return houseService.getAllHouses();
    }

    @PostMapping(path = "/houses")
    public void insertHouse(@RequestBody House house) {
        Logger logger = LoggerFactory.getLogger(Controller.class);
        logger.info(Double.toString(house.getLatitude()));
        houseService.insertHouse(house);
    }

    @PostMapping(path = "/test")
    public void test(){
        roomService.getRoomById((byte) 0,0);
    }

}
