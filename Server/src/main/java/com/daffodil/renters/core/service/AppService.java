package com.daffodil.renters.core.service;

import com.daffodil.renters.core.model.beans.postables.Building;

public class AppService {

    private BuildingService buildingService;

    public void createListing(Building building) {
        buildingService.insertBuilding(building);
    }

}
