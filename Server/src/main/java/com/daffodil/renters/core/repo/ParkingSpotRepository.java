package com.daffodil.renters.core.repo;

import com.daffodil.renters.core.model.entities.ParkingSpotEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParkingSpotRepository extends CrudRepository<ParkingSpotEntity, Long> {


//    List<ParkingSpotEntity> findParkingSpotByHouseId(long houseId);
//
//    List<ParkingSpotEntity> findParkingSpotByOccupantId(long occupantId);
}
