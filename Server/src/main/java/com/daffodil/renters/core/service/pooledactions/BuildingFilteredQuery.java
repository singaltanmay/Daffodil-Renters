package com.daffodil.renters.core.service.pooledactions;

import com.daffodil.renters.core.model.beans.Listing;
import com.daffodil.renters.core.model.beans.postables.Building;
import com.daffodil.renters.core.model.beans.postables.Property;
import com.daffodil.renters.core.service.AppService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

public class BuildingFilteredQuery extends GenericFilteredQuery<Building> {

    private Listing.Filter filter;
    private AppService.ServiceBundle serviceBundle;

    public BuildingFilteredQuery(Listing.Filter filter, AppService.ServiceBundle serviceBundle) {
        this.filter = filter;
        this.serviceBundle = serviceBundle;
    }

    @Override
    Callable<GenericFilteredQuery> childQueryGenerator(Building building) {
        return () -> {
            return new PropertyFilteredQuery((Listing.Filter) filter.setBuildingId(building.getId()), serviceBundle);
        };
    }

    @Override
    Callable<List<Building>> executeSelfFilteredQuery() {
        return () -> {
            return serviceBundle.getBuildingService().runFilteredQuery(filter);
        };
    }

    @Override
    Runnable attachChildListToParent(Building parent, Object childList) {
        return () -> parent.setProperties(Optional.of((List<Property>) childList));
    }
}
