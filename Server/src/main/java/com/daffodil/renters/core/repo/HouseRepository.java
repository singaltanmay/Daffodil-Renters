package com.daffodil.renters.core.repo;

import com.daffodil.renters.core.model.entities.HouseEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface HouseRepository extends CrudRepository<HouseEntity, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE HouseEntity h SET h.address = :add, h.latitude = :lat, h.longitude = :lon WHERE h.id = :id")
    void updateHouseById(@Param("id") long house_id, @Param("add") String address, @Param("lat") double latitude, @Param("lon") double longitude);

    HouseEntity findHouseById(long id);

    @Query("select h from HouseEntity h where h.latitude > :min_lat and h.latitude < :max_lat and h.longitude > :min_lon and h.longitude < :max_lon")
    List<HouseEntity> getAllHousesWithinCoordinates(@Param("min_lat") double min_lat, @Param("min_lon") double min_lon, @Param("max_lat") double max_lat, @Param("max_lon") double max_lon);

}
