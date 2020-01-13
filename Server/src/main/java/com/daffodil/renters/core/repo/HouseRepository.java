package com.daffodil.renters.core.repo;

import com.daffodil.renters.core.model.entities.HouseEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface HouseRepository extends CrudRepository<HouseEntity, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE HouseEntity h SET h.address = :add, h.latitude = :lat, h.longitude = :lon WHERE h.id = :id")
    void updateHouseById(@Param("id") long house_id, @Param("add") String address, @Param("lat") double latitude, @Param("lon") double longitude);

    HouseEntity findHouseById(long id);

}
