package com.daffodil.renters.model.postables

data class Seller(
    var id: Long?,
    var firstName: String?,
    var lastName: String?,
    var phoneNumber: String?,
    var sellerType: SELLER_TYPE? = SELLER_TYPE.OWNER,
    var properties: List<Property>?
) {

    enum class SELLER_TYPE {
        OWNER,
        AGENT
    }
}
