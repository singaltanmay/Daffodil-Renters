package com.daffodil.renters.controller;

import com.daffodil.renters.core.model.beans.postables.Occupant;
import com.daffodil.renters.core.model.beans.postables.ParkingSpot;
import com.daffodil.renters.core.model.beans.postables.Room;
import com.daffodil.renters.core.service.HouseService;
import com.daffodil.renters.core.service.OccupantService;
import com.daffodil.renters.core.service.ParkingSpotService;
import com.daffodil.renters.core.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("daffodil/admin")
@RestController
public class AdminController {

//              Dummy DB
//    {
//        "address":"Unitech Uniworld City South",
//            "latitude":28.458742,
//            "longitude":77.056645,
//            "parkingSpots":[{
//        "electric":true
//    }],
//        "rooms":[{
//        "capacity":2,
//                "rent": 20000,
//                "occupants":[{
//            "firstName":"Shiv",
//                    "lastName":"Sharma",
//                    "phoneNumber":"+919836452738"
//        },
//        {
//            "firstName":"Kanika",
//                "lastName":"Shethi",
//                "phoneNumber":"+919543234567"
//        }]
//    },
//        {
//            "capacity":1,
//                "rent": 10000,
//                "occupants":[{
//            "firstName":"Ashok",
//                    "lastName":"Yadav",
//                    "phoneNumber":"+4498263514"
//        }]
//        },
//        {
//            "capacity":3,
//                "rent": 30000,
//                "occupants":[{
//            "firstName":"Vipin",
//                    "lastName":"Kumaar",
//                    "phoneNumber":"+123476768",
//                    "rent":15000
//        }]
//        }]
//    }

    private final HouseService houseService;
    private final RoomService roomService;
    private final OccupantService occupantService;
    private final ParkingSpotService parkingSpotService;

    @Autowired
    public AdminController(HouseService houseService, RoomService roomService, OccupantService occupantService, ParkingSpotService parkingSpotService) {
        this.houseService = houseService;
        this.roomService = roomService;
        this.occupantService = occupantService;
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping(path = "/house")
    public void insertHouse(@RequestBody Building building) {
        houseService.insertHouse(building);
    }

    @PutMapping(path = "/house")
    public void updateHouseById(@RequestParam("id") long id, @RequestBody Building insertable) {
        houseService.updateHouseById(id, insertable);
    }

    @DeleteMapping(value = "house")
    public void deleteHouseById(@RequestParam("id") Optional<Long> id) {
        if (id.isPresent()) {
            houseService.deleteHouseById(id.get());
        } else houseService.deleteAllHouses();
    }

    @PostMapping(value = "debug/house/query")
    public String getFilteredQueryString(@RequestBody Building.Filter filter) {
        return houseService.getFilteredQueryGeneratedString(filter);
    }

    @PostMapping(path = "/room")
    public void insertRoom(@RequestBody Room room, @RequestParam("house_id") long house_id) {
        roomService.insertRoom(room, house_id);
    }

    @PutMapping(path = "/room")
    public void updateRoomById(@RequestParam("id") long id, @RequestBody Room insertable) {
        roomService.updateRoomById(id, insertable);
    }

    @DeleteMapping(value = "room")
    public void deleteRoomById(@RequestParam("id") Optional<Long> id) {
        if (id.isPresent()) {
            roomService.deleteRoomById(id.get());
        } else roomService.deleteAllRooms();
    }

    @PostMapping(value = "debug/room/query")
    public String getFilteredQueryString(@RequestBody Room.Filter filter) {
        return roomService.getFilteredQueryGeneratedString(filter);
    }

    @PostMapping(path = "/occupant")
    public void insertOccupant(@RequestBody Occupant occupant, @RequestParam("house_id") long houseId) {
        occupantService.insertOccupant(occupant, houseId);
    }

    @PutMapping(path = "/occupant")
    public void updateOccupantById(@RequestParam("id") long id, @RequestBody Occupant insertable) {
        occupantService.updateOccupantById(id, insertable);
    }

    @DeleteMapping(value = "occupant")
    public void deleteOccupantById(@RequestParam("id") Optional<Long> id) {
        if (id.isPresent()) {
            occupantService.deleteOccupantById(id.get());
        } else occupantService.deleteAllOccupants();
    }

    @PostMapping(value = "debug/occupant/query")
    public String getFilteredQueryString(@RequestBody Occupant.Filter filter) {
        return occupantService.getFilteredQueryGeneratedString(filter);
    }

    @PostMapping(value = "parkingSpot")
    public void insertParkingSpotForOccupant(@RequestBody ParkingSpot parkingSpot, @RequestParam("occupant_id") long occupantId) {
        parkingSpotService.insertParkingSpot(parkingSpot, null, occupantId);
    }


}
