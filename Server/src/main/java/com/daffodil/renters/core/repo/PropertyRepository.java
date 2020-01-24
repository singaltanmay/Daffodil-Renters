package com.daffodil.renters.core.repo;

import com.daffodil.renters.core.model.entities.BuildingEntity;
import com.daffodil.renters.core.model.entities.PropertyEntity;
import org.springframework.data.repository.CrudRepository;

public interface PropertyRepository extends CrudRepository<PropertyEntity, Long> {

    BuildingEntity findBuildingById(long property_id);

}
