package com.daffodil.renters.core.repo;

import com.daffodil.renters.core.model.entities.BuildingEntity;
import com.daffodil.renters.core.model.entities.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {

//    PropertyEntity findBuildingById(long property_id);

}
