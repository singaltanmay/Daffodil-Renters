package com.daffodil.renters.core.repo;

import com.daffodil.renters.core.model.entities.RoomEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends CrudRepository<RoomEntity, Long> {

    List<RoomEntity> findAllByRentBetween(long i, long i1);

    @Query("Select r from RoomEntity r where r.id = :h_id and r.house = :r_id")
    Optional<RoomEntity> getRoomById(@Param("r_id") long room_id, @Param("h_id") long house_id);

    List<RoomEntity> findRoomByHouseId(long houseId);

    @Transactional
    void deleteByHouseId(long id);

    @Transactional
    @Modifying
    @Query("UPDATE RoomEntity r SET r.capacity = :capacity, r.rent = :rent WHERE r.id = :id")
    void updateRoomById(@Param("id") long roomId, @Param("capacity") long capacity, @Param("rent") long rent);
}