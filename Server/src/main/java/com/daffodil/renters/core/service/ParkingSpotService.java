package com.daffodil.renters.core.service;

import com.daffodil.renters.core.model.beans.House;
import com.daffodil.renters.core.model.beans.Occupant;
import com.daffodil.renters.core.model.beans.ParkingSpot;
import com.daffodil.renters.core.model.entities.EntityFactory;
import com.daffodil.renters.core.model.entities.OccupantEntity;
import com.daffodil.renters.core.model.entities.ParkingSpotEntity;
import com.daffodil.renters.core.repo.HouseRepository;
import com.daffodil.renters.core.repo.OccupantRepository;
import com.daffodil.renters.core.repo.ParkingSpotRepository;
import com.daffodil.renters.core.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingSpotService {

    HouseRepository houseRepository;
    RoomRepository roomRepository;
    OccupantRepository occupantRepository;
    ParkingSpotRepository parkingSpotRepository;

    @Autowired
    public ParkingSpotService(HouseRepository houseRepository, RoomRepository roomRepository, OccupantRepository occupantRepository, ParkingSpotRepository parkingSpotRepository) {
        this.houseRepository = houseRepository;
        this.roomRepository = roomRepository;
        this.occupantRepository = occupantRepository;
        this.parkingSpotRepository = parkingSpotRepository;
    }

    public List<ParkingSpot> getAllParkingSpots() {
        return foreignRelationsInjector(parkingSpotRepository.findAll());
    }

    public List<ParkingSpot> getAllParkingSpotsByHouseId(long houseId) {
        return foreignRelationsInjector(parkingSpotRepository.findParkingSpotByHouseId(houseId));
    }

    public List<ParkingSpot> getAllParkingSpotsByOccupantId(long occupantId) {
        return foreignRelationsInjector(parkingSpotRepository.findParkingSpotByOccupantId(occupantId));
    }

    @Transactional
    public void insertParkingSpot(ParkingSpot parkingSpot, Long houseId, Long occupantID) {

        Optional<OccupantEntity> optionalOccupantEntity = occupantRepository.findById(occupantID);
        if (houseId == null && optionalOccupantEntity.isPresent()) {
            houseId = optionalOccupantEntity.get().getRoom().getHouse().getId();
        }

        ParkingSpotEntity build = new EntityFactory.ParkingSpotEntityBuilder().build(parkingSpot);
        build.setHouse(houseRepository.findHouseById(houseId));
        if (occupantID != null) {
            Optional<OccupantEntity> occupantEntityOptional = optionalOccupantEntity;
            occupantEntityOptional.ifPresent(build::setOccupant);
        }
        parkingSpotRepository.save(build);
    }


    private List<ParkingSpot> foreignRelationsInjector(Iterable<ParkingSpotEntity> entities) {
        List<ParkingSpot> parkingSpots = ParkingSpot.listFrom(entities);
        parkingSpots.forEach(parkingSpot -> {
            Occupant occupant = parkingSpot.getOccupant();
            if (parkingSpot.getHouse() == null && occupant != null) {
                long roomId = occupant.getRoom().getId();
                House build = new House.Builder().build(roomRepository.findRoomById(roomId).getHouse());
                parkingSpot.setHouse(build);
            }
        });
        return parkingSpots;
    }

    public void deleteAllParkingSpotsOfOccupant(long occupantId) {
        List<ParkingSpotEntity> parkingSpotByOccupantId = parkingSpotRepository.findParkingSpotByOccupantId(occupantId);
        parkingSpotByOccupantId.forEach(parkingSpotEntity -> {
            parkingSpotRepository.deleteById(parkingSpotEntity.getId());
        });
    }
}
