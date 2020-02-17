package com.daffodil.renters.core.service.pooledactions.listingcreator;

import com.daffodil.renters.core.model.beans.ListingSkeletal;
import com.daffodil.renters.core.model.beans.postables.Property;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class GenerateListingsTask extends RecursiveTask<List<ListingSkeletal>> {

    /**
     * Properties should already have their parent buildings mapped.
     */
    private List<Property> propertyList;
    private boolean minListing;

    public GenerateListingsTask(List<Property> propertyList, boolean minListing) {
        this.propertyList = propertyList;
        this.minListing = minListing;
    }

    @Override
    protected List<ListingSkeletal> compute() {

        List<ListingSkeletal> listings = new LinkedList<>();

        if (minListing) {
            List<MinListingFromPropertyCreator> taskList = generateMinListingsTaskList();
            taskList.forEach(t -> t.fork());
            taskList.forEach(t -> listings.add(t.join()));
        } else {
            List<ListingFromPropertyCreator> taskList = generateListingsTaskList();
            taskList.forEach(t -> t.fork());
            taskList.forEach(t -> listings.add(t.join()));
        }

        return listings;
    }

    private List<MinListingFromPropertyCreator> generateMinListingsTaskList() {
        List<MinListingFromPropertyCreator> list = new LinkedList<>();

        if (propertyList != null) {
            propertyList.forEach(p -> {
                list.add(new MinListingFromPropertyCreator(p));
            });
        }

        return list;
    }

    private List<ListingFromPropertyCreator> generateListingsTaskList() {

        List<ListingFromPropertyCreator> list = new LinkedList<>();

        if (propertyList != null) {
            propertyList.forEach(p -> {
                list.add(new ListingFromPropertyCreator(p));
            });
        }

        return list;
    }

}
