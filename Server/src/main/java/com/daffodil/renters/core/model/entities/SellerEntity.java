package com.daffodil.renters.core.model.entities;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

public class SellerEntity {

    public enum SELLER_TYPE {
        OWNER,
        AGENT
    }

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    @Getter
    String firstName;
    @Getter
    String lastName;
    @Getter
    String phoneNumber;

    @Getter
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    List<PropertyEntity> propertyEntities;

    public SellerEntity() {
    }

    public SellerEntity setId(long id) {
        this.id = id;
        return this;
    }

    public SellerEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public SellerEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public SellerEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public SellerEntity setPropertyEntities(List<PropertyEntity> propertyEntities) {
        this.propertyEntities = propertyEntities;
        return this;
    }
}
