package com.daffodil.renters.core.model.beans.postables;

import com.daffodil.renters.core.model.entities.SellerEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Seller {

    @Getter
    private Optional<Long> id;
    @Getter
    private Optional<String> firstName;
    @Getter
    private Optional<String> lastName;
    @Getter
    private Optional<String> phoneNumber;
    @Getter
    private Optional<SellerEntity.SELLER_TYPE> sellerType;
    @Getter
    private Optional<List<Property>> properties;

    public Seller(Optional<Long> id, Optional<String> firstName, Optional<String> lastName, Optional<String> phoneNumber, Optional<SellerEntity.SELLER_TYPE> sellerType, Optional<List<Property>> properties) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.sellerType = sellerType;
        this.properties = properties;
    }
}
