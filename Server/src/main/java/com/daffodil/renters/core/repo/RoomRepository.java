package com.daffodil.renters.core.repo;

import com.daffodil.renters.core.model.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room, Byte> {

    List<Room> findAllByRentBetween(long i, long i1);

    @Query("Select r from room r where r.house_id = :h_id and r.id = :r_id")
    Optional<Room> getRoomById(@Param("r_id") short room_id, @Param("h_id") long house_id);

    //        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("daffodil_renters_db");
//        EntityManager manager = entityManagerFactory.createEntityManager();
//        Query query = manager.createQuery("SELECT * FROM Room r");
//
//        List<Object[]> resultList = query.getResultList();
//        resultList.forEach(arr -> System.out.println(Arrays.toString(arr)));
//        manager.close();
//


}
