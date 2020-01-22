package com.daffodil.renters.core.model.beans;

import com.daffodil.renters.core.model.entities.PropertyEntity;

public class PropertySkeletal {

    private long id;
    private double latitude;
    private double longitude;

    private String addressBuildingName;
    private String addressLocalityName;
    private String addressSubdivision;
    private String addressCity;
    private String addressState;
    private String addressPinCode;

    private short bedrooms;
    private PropertyEntity.PROPERTY_TYPE propertyType;
    private PropertyEntity.FURNISHING_TYPE furnishing;
    private int area;
    private long rent;
    private boolean roommates;

}