//package com.daffodil.renters.core.service;
//
//import com.daffodil.renters.core.model.beans.postables.Occupant;
//import com.daffodil.renters.core.model.beans.postables.ParkingSpot;
//import com.daffodil.renters.core.model.entities.EntityFactory;
//import com.daffodil.renters.core.model.entities.OccupantEntity;
//import com.daffodil.renters.core.repo.OccupantRepository;
//import com.daffodil.renters.core.repo.RoomRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class OccupantService {
//
//    RoomRepository roomRepository;
//    OccupantRepository occupantRepository;
//
//    ParkingSpotService parkingSpotService;
//
//    @Autowired
//    public OccupantService(RoomRepository roomRepository, OccupantRepository occupantRepository, ParkingSpotService parkingSpotService) {
//        this.roomRepository = roomRepository;
//        this.occupantRepository = occupantRepository;
//        this.parkingSpotService = parkingSpotService;
//    }
//
//    public List<Occupant> getAllOccupants(long roomId) {
//        return foreignRelationsInjector(occupantRepository.findAll());
//    }
//
//    public List<Occupant> getAllOccupantsByRoomId(long roomId) {
//        return foreignRelationsInjector(occupantRepository.findOccupantByRoomId(roomId));
//    }
//
//    public Optional<Occupant> getOccupantById(long occupantId) {
//        Optional<OccupantEntity> occupant = occupantRepository.findById(occupantId);
//        if (occupant.isPresent()) {
//            List<Occupant> occupants = foreignRelationsInjector(List.of(occupant.get()));
//            if (occupants.size() > 0) {
//                return Optional.of(occupants.get(0));
//            }
//        }
//        return Optional.empty();
//    }
//
//    @Transactional
//    public void insertOccupant(Occupant occupant, long roomId) {
//        OccupantEntity build = new EntityFactory.OccupantEntityBuilder().build(occupant);
//        build.setRoom(roomRepository.findRoomById(roomId));
//        occupantRepository.save(build);
//    }
//
//    @Transactional
//    public void updateOccupantById(long occupantId, Occupant occupant) {
//        if (!occupantRepository.existsById(occupantId)) return;
//        parkingSpotService.deleteAllParkingSpotsOfOccupant(occupantId);
//
//        List<ParkingSpot> parkingSpots = occupant.getParkingSpots();
//        parkingSpots.forEach(parkingSpot -> {
//            long houseId = getHouseIdFromOccupant(occupant);
//            parkingSpotService.insertParkingSpot(parkingSpot, houseId, occupantId);
//        });
//
//        occupantRepository.updateOccupantById(
//                occupantId,
//                occupant.getFirstName(),
//                occupant.getLastName(),
//                occupant.getPhoneNumber(),
//                occupant.getDateMovedIn(),
//                occupant.getTimeLastRentPaid()
//        );
//
//    }
//
//    private long getHouseIdFromOccupant(Occupant occupant) {
//        long roomId = occupant.getRoom().getId();
//        return roomRepository.findRoomById(roomId).getProperty().getId();
//    }
//
//    @Transactional
//    public void deleteAllOccupants() {
//        occupantRepository.deleteAll();
//    }
//
//    @Transactional
//    public void deleteAllOccupantsOfRoom(long roomId) {
//        occupantRepository.deleteByRoomId(roomId);
//    }
//
//    @Transactional
//    public void deleteOccupantById(long occupantId) {
//        if (occupantRepository.existsById(occupantId)) {
//            occupantRepository.deleteById(occupantId);
//        }
//    }
//
//    private List<Occupant> foreignRelationsInjector(Iterable<OccupantEntity> entities) {
//        List<Occupant> occupants = Occupant.listFrom(entities);
//        occupants.forEach(occupant -> occupant.setParkingSpots(parkingSpotService.getAllParkingSpotsByOccupantId(occupant.getId())));
//        return occupants;
//    }
//
//    // TODO implement
//    public List<Occupant> getOccupantsUsingFilteredQuery(Occupant.Filter filter) {
//        return null;
//    }
//
//    // TODO implement
//    public String getFilteredQueryGeneratedString(Occupant.Filter filter) {
//        return null;
//    }
//}
