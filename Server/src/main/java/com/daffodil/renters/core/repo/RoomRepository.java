package com.daffodil.renters.core.repo;

import com.daffodil.renters.core.model.entities.RoomEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends CrudRepository<RoomEntity, Byte> {

    List<RoomEntity> findAllByRentBetween(long i, long i1);

    @Query("Select r from RoomEntity r where r.id = :h_id and r.house = :r_id")
    Optional<RoomEntity> getRoomById(@Param("r_id") short room_id, @Param("h_id") long house_id);

    List<RoomEntity> findByHouseId(long houseId);

    //        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("daffodil_renters_db");
//        EntityManager manager = entityManagerFactory.createEntityManager();
//        Query query = manager.createQuery("SELECT * FROM Room r");
//
//        List<Object[]> resultList = query.getResultList();
//        resultList.forEach(arr -> System.out.println(Arrays.toString(arr)));
//        manager.close();
//


}
