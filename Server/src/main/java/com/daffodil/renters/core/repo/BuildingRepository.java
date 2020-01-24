package com.daffodil.renters.core.repo;

import com.daffodil.renters.core.model.entities.BuildingEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface BuildingRepository extends CrudRepository<BuildingEntity, Long> {

//    @Transactional
//    @Modifying
//    @Query("UPDATE BuildingEntity b SET b.address = :add, b.latitude = :lat, b.longitude = :lon WHERE b.id = :id")
//    void updateBuildingById(@Param("id") long building_id, @Param("add") String address, @Param("lat") double latitude, @Param("lon") double longitude);
//
//    BuildingEntity findBuildingById(long id);

}
