package com.daffodil.renters.core.repo;

import com.daffodil.renters.core.model.entities.OccupantEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface OccupantRepository extends CrudRepository<OccupantEntity, Long> {

//    List<OccupantEntity> findByRoomId(long roomId);
//
//    Iterable<OccupantEntity> findOccupantByRoomId(long roomId);
//
//    @Transactional
//    @Modifying
//    @Query("UPDATE OccupantEntity o SET o.firstName = :f_na, o.lastName = :l_na, o.phoneNumber = :ph, o.dateMovedIn = :dmi, o.timeLastRentPaid = :tlrp WHERE o.id = :id")
//    void updateOccupantById(@Param("id") long occupantId, @Param("f_na") String firstName, @Param("l_na") String lastName, @Param("ph") String phoneNumber, @Param("dmi") Date dateMovedIn, @Param("tlrp") Date timeLastRentPaid);
//
//    void deleteByRoomId(long roomId);
}