package com.daffodil.renters.controller;

import com.daffodil.renters.core.model.beans.House;
import com.daffodil.renters.core.service.HouseService;
import com.daffodil.renters.core.service.OccupantService;
import com.daffodil.renters.core.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("daffodil/admin")
@RestController
public class AdminController {

    private final HouseService houseService;
    private final RoomService roomService;
    private final OccupantService occupantService;

    @Autowired
    public AdminController(HouseService houseService, RoomService roomService, OccupantService occupantService) {
        this.houseService = houseService;
        this.roomService = roomService;
        this.occupantService = occupantService;
    }

    @PostMapping(value = "debug/house/query")
    public String getFilteredQueryString(@RequestBody House.Filter filter) {
        return houseService.getFilteredQueryGeneratedString(filter);
    }

    @PostMapping(path = "/house")
    public void insertHouse(@RequestBody House house) {
        houseService.insertHouse(house);
    }

    @PutMapping(path = "/house")
    public void updateHouseById(@RequestParam("id") long id, @RequestBody House insertable) {
        houseService.updateHouseById(id, insertable);
    }

    @DeleteMapping(value = "house")
    public void deleteHouseById(@RequestParam("id") Optional<Long> id) {
        if (id.isPresent()) {
            houseService.deleteHouseById(id.get());
        } else houseService.deleteAllHouses();
    }

}
