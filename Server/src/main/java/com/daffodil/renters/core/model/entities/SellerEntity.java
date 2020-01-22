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
    private long id;
    @Getter
    private String firstName;
    @Getter
    private String lastName;
    @Getter
    private String phoneNumber;

    @Getter
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    List<PropertyEntity> propertyEntities;

    private void mapAllProperties() {
        List<PropertyEntity> properties = this.propertyEntities;
        new Thread(() -> {
            if (properties != null) {
                properties.forEach(SellerEntity.this::mapProperty);
            }
        }).start();
    }

    private void mapProperty(PropertyEntity propertyEntity) {
        if (propertyEntity != null) {
            propertyEntity.setSeller(SellerEntity.this);
        }
    }

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
        mapAllProperties();
        return this;
    }
}
