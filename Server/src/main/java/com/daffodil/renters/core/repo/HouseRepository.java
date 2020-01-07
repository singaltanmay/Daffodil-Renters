package com.daffodil.renters.core.repo;

import com.daffodil.renters.core.model.House;
import org.springframework.data.repository.CrudRepository;

public interface HouseRepository extends CrudRepository<House, Long> {
}
