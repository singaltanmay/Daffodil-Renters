package com.daffodil.renters.core.model.beans.postables;

import com.daffodil.renters.core.model.beans.Property;
import com.daffodil.renters.core.model.entities.SellerEntity;

import java.util.List;
import java.util.Optional;

public class Seller {

    private Optional<Long> id;
    private Optional<String> firstName;
    private Optional<String> lastName;
    private Optional<String> phoneNumber;
    private Optional<SellerEntity.SELLER_TYPE> sellerType;
    private Optional<List<com.daffodil.renters.core.model.beans.Property>> properties;

    public Seller(Optional<Long> id, Optional<String> firstName, Optional<String> lastName, Optional<String> phoneNumber, Optional<SellerEntity.SELLER_TYPE> sellerType, Optional<List<Property>> properties) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.sellerType = sellerType;
        this.properties = properties;
    }
}
