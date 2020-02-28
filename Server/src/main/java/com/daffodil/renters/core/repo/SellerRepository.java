package com.daffodil.renters.core.repo;

import com.daffodil.renters.core.model.entities.SellerEntity;
import org.springframework.data.repository.CrudRepository;

public interface SellerRepository extends CrudRepository<SellerEntity, Long> {
}
