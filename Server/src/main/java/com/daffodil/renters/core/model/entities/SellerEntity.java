package com.daffodil.renters.core.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

public class SellerEntity {

    public enum SELLER_TYPE {
        OWNER,
        AGENT
    }

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Getter
    @Setter
    private String firstName;
    @Getter
    @Setter
    private String lastName;
    @Getter
    @Setter
    private String phoneNumber;

    @Getter
    @Setter
    private SELLER_TYPE sellerType;

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

    public SellerEntity(long id, String firstName, String lastName, String phoneNumber, SELLER_TYPE sellerType, List<PropertyEntity> propertyEntities) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.sellerType = sellerType;
        setPropertyEntities(propertyEntities);
    }

    public SellerEntity setPropertyEntities(List<PropertyEntity> propertyEntities) {
        this.propertyEntities = propertyEntities;
        mapAllProperties();
        return this;
    }
}
