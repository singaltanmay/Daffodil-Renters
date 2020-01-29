package com.daffodil.renters.core.service;

import com.daffodil.renters.core.model.beans.postables.Building;
import com.daffodil.renters.core.model.beans.postables.PostableFactory;
import com.daffodil.renters.core.model.entities.BuildingEntity;
import com.daffodil.renters.core.model.entities.EntityFactory;
import com.daffodil.renters.core.model.entities.PropertyEntity;
import com.daffodil.renters.core.repo.BuildingRepository;
import com.daffodil.renters.core.repo.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class BuildingService {

    private BuildingRepository buildingRepository;
    private PropertyRepository propertyRepository;

    @Autowired
    public BuildingService(BuildingRepository buildingRepository, PropertyRepository propertyRepository) {
        this.buildingRepository = buildingRepository;
        this.propertyRepository = propertyRepository;
    }

    public void insertBuilding(Building building) {
        BuildingEntity build = new EntityFactory.BuildingEntityBuilder().build(building);
        buildingRepository.save(build);
    }

    @Transactional
    public Building getBuildingByPropertyId(long property_id) {
        System.out.println(propertyRepository);
        Optional<PropertyEntity> propertyEntity = propertyRepository.findById(property_id);
        if (propertyEntity.isPresent()) {
            return new PostableFactory.BuildingBuilder().build(propertyEntity.get().getBuilding());
        }
        return null;
    }
}
