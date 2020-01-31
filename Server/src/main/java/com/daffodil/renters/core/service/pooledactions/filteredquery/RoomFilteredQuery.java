package com.daffodil.renters.core.service.pooledactions.filteredquery;

import com.daffodil.renters.core.model.beans.Listing;
import com.daffodil.renters.core.model.beans.postables.Room;
import com.daffodil.renters.core.service.AppService;

import java.util.List;
import java.util.concurrent.Callable;

class RoomFilteredQuery extends GenericFilteredQuery<Room> {

    private Listing.Filter filter;
    private AppService.ServiceBundle serviceBundle;

    public RoomFilteredQuery(Listing.Filter filter, AppService.ServiceBundle serviceBundle) {
        this.filter = filter;
        this.serviceBundle = serviceBundle;
    }

    @Override
    Callable<List<Room>> executeSelfFilteredQuery() {
        return () -> {
            return serviceBundle.getRoomService().runFilteredQuery(filter);
        };
    }

    @Override
    Callable<GenericFilteredQuery> childQueryGenerator(Room room) {
        return null;
    }

    @Override
    Runnable attachChildListToParent(Room parent, Object childList) {
        return null;
//        return () -> parent.setOccupants(Optional.of((List<Occupant>) childList));
    }

}
