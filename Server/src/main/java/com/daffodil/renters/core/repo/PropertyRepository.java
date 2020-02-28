package com.daffodil.renters.core.repo;

import com.daffodil.renters.core.model.entities.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {

    List<PropertyEntity> findPropertyByBuildingId(long buildingId);

//    PropertyEntity findBuildingById(long property_id);

}
