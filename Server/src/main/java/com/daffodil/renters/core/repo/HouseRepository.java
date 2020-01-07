package com.daffodil.renters.core.repo;

import com.daffodil.renters.core.model.entities.HouseEntity;
import org.springframework.data.repository.CrudRepository;

public interface HouseRepository extends CrudRepository<HouseEntity, Long> {
}
