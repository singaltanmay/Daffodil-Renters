package com.daffodil.renters.core.service.forkjoinpol;

import com.daffodil.renters.core.model.beans.Listing;
import com.daffodil.renters.core.model.beans.postables.Property;
import com.daffodil.renters.core.service.AppService;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.RecursiveTask;

public class PropertyFilteredQuery extends RecursiveTask<List<Property>> {

    /**
     * Used by calling task to store index of parent in list
     */
    public int buildingTag;

    private Listing.Filter filter;
    private AppService.ServiceBundle serviceBundle;

    public PropertyFilteredQuery(Listing.Filter filter, AppService.ServiceBundle serviceBundle) {
        this.filter = filter;
        this.serviceBundle = serviceBundle;
    }

    @Override
    protected List<Property> compute() {

        List<Property> properties = serviceBundle.getPropertyService().runFilteredQuery(filter);

        List<RoomFilteredQuery> queries = createRoomsFilteredQueriesList(properties);

        queries.forEach(q -> q.fork());

        queries.forEach(q -> {
            properties.get(q.propertyTag).setRooms(Optional.of(q.join()));
        });

        return null;
    }

    private List<RoomFilteredQuery> createRoomsFilteredQueriesList(List<Property> properties) {

        List<RoomFilteredQuery> tasks = new LinkedList<>();

        if (properties != null) {
            for (int i = 0; i < properties.size(); i++) {
                Property property = properties.get(i);
                RoomFilteredQuery filteredQuery = new RoomFilteredQuery((Listing.Filter) filter.setPropertyId(property.getId()), serviceBundle);
                filteredQuery.propertyTag = i;
                tasks.add(filteredQuery);
            }
        }


        return tasks;

    }
}
