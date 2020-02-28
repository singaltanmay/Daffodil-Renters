package com.daffodil.renters.model.postables

class Seller : User() {

    var sellerType: SELLER_TYPE? = SELLER_TYPE.OWNER
    var properties: List<Property>? = null

    enum class SELLER_TYPE {
        OWNER,
        AGENT
    }
}
