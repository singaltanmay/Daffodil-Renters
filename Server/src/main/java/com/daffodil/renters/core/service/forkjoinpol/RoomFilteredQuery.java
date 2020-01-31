package com.daffodil.renters.core.service.forkjoinpol;

import com.daffodil.renters.core.model.beans.Listing;
import com.daffodil.renters.core.model.beans.postables.Room;
import com.daffodil.renters.core.service.AppService;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class RoomFilteredQuery extends RecursiveTask<List<Room>> {

    /**
     * Used by calling task to store index of parent in list
     */
    public int propertyTag;

    private Listing.Filter filter;
    private AppService.ServiceBundle serviceBundle;

    public RoomFilteredQuery(Listing.Filter filter, AppService.ServiceBundle serviceBundle) {
        this.filter = filter;
        this.serviceBundle = serviceBundle;
    }

    @Override
    protected List<Room> compute() {
        // TODO
        return null;
    }
}
