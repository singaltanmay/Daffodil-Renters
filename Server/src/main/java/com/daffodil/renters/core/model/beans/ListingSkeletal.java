package com.daffodil.renters.core.model.beans;

import com.daffodil.renters.core.model.entities.PropertyEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ListingSkeletal {

    @Getter
    @Setter
    private long propertyId;
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
    @Getter
    @Setter
    private double distanceKm;

    public static class Filter {

        /**
         * Used while querying to determine depth of recursive queries required.
         */
        public boolean minListing = false;

        public Optional<Long> buildingId = Optional.empty();
        public Optional<Long> propertyId = Optional.empty();

        public Optional<Double> latitude = Optional.empty();
        public Optional<Double> longitude = Optional.empty();
        public Optional<Double> rangeKm = Optional.empty();

        public Filter() {
        }

        public Filter setBuildingId(Optional<Long> buildingId) {
            this.buildingId = buildingId;
            return this;
        }

        public Filter setPropertyId(Optional<Long> propertyId) {
            this.propertyId = propertyId;
            return this;
        }

        public Filter setLatitude(Optional<Double> latitude) {
            this.latitude = latitude;
            return this;
        }

        public Filter setLongitude(Optional<Double> longitude) {
            this.longitude = longitude;
            return this;
        }

        public Filter setRangeKm(Optional<Double> rangeKm) {
            this.rangeKm = rangeKm;
            return this;
        }

    }


}