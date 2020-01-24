package com.daffodil.renters.core.service;

import com.daffodil.renters.core.model.beans.postables.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppService {

    private BuildingService buildingService;

    @Autowired
    public AppService(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    public void createListing(Building building) {
        buildingService.insertBuilding(building);
    }

}
