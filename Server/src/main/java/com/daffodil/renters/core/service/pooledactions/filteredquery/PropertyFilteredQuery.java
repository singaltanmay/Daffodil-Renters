package com.daffodil.renters.core.service.pooledactions.filteredquery;

import com.daffodil.renters.core.model.beans.Listing;
import com.daffodil.renters.core.model.beans.postables.Property;
import com.daffodil.renters.core.model.beans.postables.Room;
import com.daffodil.renters.core.service.AppService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

class PropertyFilteredQuery extends GenericFilteredQuery<Property> {

    private Listing.Filter filter;
    private AppService.ServiceBundle serviceBundle;

    public PropertyFilteredQuery(Listing.Filter filter, AppService.ServiceBundle serviceBundle) {
        this.filter = filter;
        this.serviceBundle = serviceBundle;
    }

    @Override
    Callable<GenericFilteredQuery> childQueryGenerator(Property property) {
        return () -> {
            return new RoomFilteredQuery((Listing.Filter) filter.setPropertyId(property.getId()), serviceBundle);
        };
    }

    @Override
    Callable<List<Property>> executeSelfFilteredQuery() {
        return () -> {
            return serviceBundle.getPropertyService().runFilteredQuery(filter);
        };
    }

    @Override
    Runnable attachChildListToParent(Property parent, Object childList) {
        return () -> parent.setRooms(Optional.of((List<Room>) childList));
    }
}
