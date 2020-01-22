package com.daffodil.renters.core.model.beans.postables;

import com.daffodil.renters.core.model.beans.Property;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

public class Amenities {

    @Getter
    @Setter
    private Optional<Long> id;

    @Getter
    @Setter
    private Optional<Boolean> gasPipeline;

    @Getter
    @Setter
    private Optional<Boolean> swimmingPool;

    @Getter
    @Setter
    private Optional<Boolean> gym;

    @Getter
    @Setter
    private Optional<Boolean> lift;

    @Getter
    @Setter
    private Optional<Boolean> gatedCommunity;

    @Getter
    @Setter
    private Optional<Boolean> parking;

    @Getter
    @Setter
    private Optional<Boolean> parkingAllowed;

    @Getter
    @Setter
    private Optional<List<com.daffodil.renters.core.model.beans.Property>> properties;

    public Amenities(Optional<Long> id, Optional<Boolean> gasPipeline, Optional<Boolean> swimmingPool, Optional<Boolean> gym, Optional<Boolean> lift, Optional<Boolean> gatedCommunity, Optional<Boolean> parking, Optional<Boolean> parkingAllowed, Optional<List<Property>> properties) {
        this.id = id;
        this.gasPipeline = gasPipeline;
        this.swimmingPool = swimmingPool;
        this.gym = gym;
        this.lift = lift;
        this.gatedCommunity = gatedCommunity;
        this.parking = parking;
        this.parkingAllowed = parkingAllowed;
        this.properties = properties;
    }

}
