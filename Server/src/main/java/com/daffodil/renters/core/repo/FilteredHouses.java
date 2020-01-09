package com.daffodil.renters.core.repo;

import com.daffodil.renters.core.model.beans.Filter;
import com.daffodil.renters.core.model.entities.HouseEntity;

import java.util.List;

public interface FilteredHouses {

    List<HouseEntity> filteredHouses(Filter filter);

}
