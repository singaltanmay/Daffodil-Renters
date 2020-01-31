package com.daffodil.renters.core.repo;

import com.daffodil.renters.core.model.entities.RoomEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoomRepository extends CrudRepository<RoomEntity, Long> {

    //    List<RoomEntity> findAllByRentBetween(long i, long i1);
//
//    @Query("Select r from RoomEntity r where r.id = :r_id")
//    Optional<RoomEntity> getRoomById(@Param("r_id") long room_id);
//
    List<RoomEntity> findRoomByPropertyId(long propertyId);

    int countByPropertyId(long properyId);
//
//    RoomEntity findRoomById(long roomId);
//
//    @Transactional
//    void deleteByHouseId(long id);
//
//    @Transactional
//    @Modifying
//    @Query("UPDATE RoomEntity r SET r.capacity = :capacity, r.rent = :rent WHERE r.id = :id")
//    void updateRoomById(@Param("id") long roomId, @Param("capacity") long capacity, @Param("rent") long rent);

}