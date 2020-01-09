package com.daffodil.renters.core.repo;

import com.daffodil.renters.core.model.entities.OccupantEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OccupantRepository extends CrudRepository<OccupantEntity, Long> {

    List<OccupantEntity> findByRoomId(long roomId);
}
