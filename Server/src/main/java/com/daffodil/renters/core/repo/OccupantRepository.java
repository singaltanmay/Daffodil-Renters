package com.daffodil.renters.core.repo;

import com.daffodil.renters.core.model.entities.OccupantEntity;
import org.springframework.data.repository.CrudRepository;

public interface OccupantRepository extends CrudRepository<OccupantEntity, Long> {

}
