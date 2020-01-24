package com.daffodil.renters.core.service;

import com.daffodil.renters.core.model.beans.postables.Building;
import com.daffodil.renters.core.model.beans.postables.PostableFactory;
import com.daffodil.renters.core.model.entities.BuildingEntity;
import com.daffodil.renters.core.model.entities.EntityFactory;
import com.daffodil.renters.core.repo.BuildingRepository;
import com.daffodil.renters.core.repo.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingService {

    private BuildingRepository buildingRepository;
    private PropertyRepository propertyRepository;

    @Autowired
    public BuildingService(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    public void insertBuilding(Building building) {
        BuildingEntity build = new EntityFactory.BuildingEntityBuilder().build(building);
        buildingRepository.save(build);
    }

    public Building getBuildingByPropertyId(long property_id) {

        BuildingEntity buildingEntity = propertyRepository.findBuildingById(property_id);
        if (buildingEntity != null) {
            return new PostableFactory.BuildingBuilder().build(buildingEntity);
        }
        return null;
    }
}
