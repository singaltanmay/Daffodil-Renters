package com.daffodil.renters.core.service;

import com.daffodil.renters.core.model.beans.postables.Building;
import com.daffodil.renters.core.model.entities.BuildingEntity;
import com.daffodil.renters.core.model.entities.EntityFactory;
import com.daffodil.renters.core.repo.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingService {

    private BuildingRepository buildingRepository;

    @Autowired
    public BuildingService(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    public void insertBuilding(Building building) {
        BuildingEntity build = new EntityFactory.BuildingEntityBuilder().build(building);
        buildingRepository.save(build);
    }

}
