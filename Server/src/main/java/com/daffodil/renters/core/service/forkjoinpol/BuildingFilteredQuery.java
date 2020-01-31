package com.daffodil.renters.core.service.forkjoinpol;

import com.daffodil.renters.core.model.beans.Listing;
import com.daffodil.renters.core.model.beans.postables.Building;
import com.daffodil.renters.core.service.AppService;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.RecursiveTask;

public class BuildingFilteredQuery extends RecursiveTask<List<Building>> {


    private Listing.Filter filter;
    private AppService.ServiceBundle serviceBundle;

    public BuildingFilteredQuery(Listing.Filter filter, AppService.ServiceBundle serviceBundle) {
        this.filter = filter;
        this.serviceBundle = serviceBundle;
    }

    @Override
    protected List<Building> compute() {

        List<Building> buildings = serviceBundle.getBuildingService().runFilteredQuery(filter);

        List<PropertyFilteredQuery> queries = createPropertiesFilteredQueriesList(buildings);

        queries.forEach(q -> q.fork());

        queries.forEach(q -> {
            buildings.get(q.buildingTag).setProperties(Optional.of(q.join()));
//            List<Property> properties = q.join();
//
//            if (properties != null && properties.size() > 0) {
////                Optional<Building> building = properties.get(0).getBuilding();
////                building.ifPresent(b -> {
////                    int i = buildings.indexOf(b);
////                    buildings.get(i).setProperties(Optional.of(properties));
////                });
//
//            }
        });

        return buildings;
    }

    private List<PropertyFilteredQuery> createPropertiesFilteredQueriesList(List<Building> buildings) {

        List<PropertyFilteredQuery> tasks = new LinkedList<>();

        if (buildings != null) {
            for (int i = 0; i < buildings.size(); i++) {
                Building building = buildings.get(i);
                PropertyFilteredQuery filteredQuery = new PropertyFilteredQuery((Listing.Filter) filter.setBuildingId(building.getId()), serviceBundle);
                filteredQuery.buildingTag = i;
                tasks.add(filteredQuery);
            }
        }

        return tasks;

    }

}
