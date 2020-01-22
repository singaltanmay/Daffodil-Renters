package com.daffodil.renters.core.model.beans;

import com.daffodil.renters.core.model.entities.PropertyEntity;

public class PropertySkeletal {




    long id;
    double latitude;
    double longitude;
    String address;
    short bedrooms;
    PropertyEntity.PROPERTY_TYPE propertyType;
    PropertyEntity.FURNISHING_TYPE furnishing;
    int area;
    long rent;
    boolean roommates;

}