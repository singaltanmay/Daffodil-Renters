package com.daffodil.renters.core.service.forkjoinpol;

import com.daffodil.renters.core.model.beans.Listing;
import com.daffodil.renters.core.model.beans.ListingSkeletal;
import com.daffodil.renters.core.model.beans.postables.Building;
import com.daffodil.renters.core.service.AppService;

import java.util.List;
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

        BuildingFilteredQuery task = new BuildingFilteredQuery(filter, serviceBundle);
        task.fork();

        List<Building> join = task.join();

        // TODO convert list of buildings into a list of Listing / SkeletalListing

        return null;
    }

}
