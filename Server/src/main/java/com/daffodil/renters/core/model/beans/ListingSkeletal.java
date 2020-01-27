package com.daffodil.renters.core.model.beans;

import com.daffodil.renters.core.model.entities.PropertyEntity;
import lombok.Getter;
import lombok.Setter;

public class ListingSkeletal {

    @Getter
    @Setter
    private long id;
    @Getter
    @Setter
    private double latitude;
    @Getter
    @Setter
    private double longitude;

    @Getter
    @Setter
    private String addressBuildingName;
    @Getter
    @Setter
    private String addressLocalityName;
    @Getter
    @Setter
    private String addressSubdivision;
    @Getter
    @Setter
    private String addressCity;
    @Getter
    @Setter
    private String addressState;
    @Getter
    @Setter
    private String addressPinCode;

    @Getter
    @Setter
    private int bedrooms;
    @Getter
    @Setter
    private PropertyEntity.PROPERTY_TYPE propertyType;
    @Getter
    @Setter
    private PropertyEntity.FURNISHING_TYPE furnishing;
    @Getter
    @Setter
    private int area;
    @Getter
    @Setter
    private long rent;
    @Getter
    @Setter
    private boolean roommates;

}