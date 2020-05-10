package com.daffodil.renters.core.service.pooledactions.filteredquery;

import com.daffodil.renters.core.model.beans.Listing;
import com.daffodil.renters.core.model.beans.ListingSkeletal;
import com.daffodil.renters.core.model.beans.postables.Building;
import com.daffodil.renters.core.model.beans.postables.Property;
import com.daffodil.renters.core.service.AppService;
import com.daffodil.renters.core.service.pooledactions.listingcreator.GenerateListingsTask;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.RecursiveTask;

public class FilteredQueryTask extends RecursiveTask<List<ListingSkeletal>> {

    private Listing.Filter filter;
    private AppService.ServiceBundle serviceBundle;

    public FilteredQueryTask(Listing.Filter filter, AppService.ServiceBundle serviceBundle) {
        this.filter = filter;
        this.serviceBundle = serviceBundle;
    }

    @Override
    protected List<ListingSkeletal> compute() {
        List<ListingSkeletal> skeletals = new LinkedList<>();

        BuildingFilteredQuery task = new BuildingFilteredQuery(filter, serviceBundle);
        task.fork();
        // This is a list of all the filtered buildings
        List<Building> filteredBuildings = task.join();

        // Convert all buildings (with children) into listings
        List<GenerateListingsTask> listingsTasks = listingsTasks(filteredBuildings);

        listingsTasks.forEach(t -> t.fork());
        listingsTasks.forEach(t -> skeletals.addAll(t.join()));

        return skeletals;
    }

    private List<GenerateListingsTask> listingsTasks(List<Building> buildings) {
        List<GenerateListingsTask> listingsTasks = new LinkedList<>();

        if (buildings != null) {
            buildings.forEach(b -> {
                List<Property> propertyList = b.getProperties().get();
                propertyList.forEach(p -> p.setBuilding(Optional.of(b)));
                listingsTasks.add(new GenerateListingsTask(propertyList, filter.minListing));
            });
        }
        return listingsTasks;
    }

}
