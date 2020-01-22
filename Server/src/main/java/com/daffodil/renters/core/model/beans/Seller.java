package com.daffodil.renters.core.model.beans;

public class Seller {

    enum SELLER_TYPE {
        OWNER,
        AGENT
    }

    long id;
    String firstName;
    String lastName;
    String phoneNumber;

}
