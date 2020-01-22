package com.daffodil.renters.core.model.beans;

public class PropertySkeletal {

    enum PROPERTY_TYPE {
        APARTMENT,
        INDEPENDENT_FLOOR,
        INDEPENDENT_HOUSE,
        PG
    }

    enum FURNISHING_TYPE {
        FULLY_FURNISHED,
        SEMI_FURNISHED,
        UNFURNISHED
    }

    long id;
    double latitude;
    double longitude;
    String address;
    short bedrooms;
    PROPERTY_TYPE propertyType;
    FURNISHING_TYPE furnishing;
    int area;
    long rent;
    boolean roommates;

}