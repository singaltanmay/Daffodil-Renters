package com.daffodil.renters.model

import com.daffodil.renters.model.postables.Property

open class ListingSkeletal {

    var id: Long = 0

    var latitude: Double = 0.toDouble()

    var longitude: Double = 0.toDouble()

    var addressBuildingName: String? = null

    var addressLocalityName: String? = null

    var addressSubdivision: String? = null

    var addressCity: String? = null

    var addressState: String? = null

    var addressPinCode: String? = null

    var bedrooms: Int = 0

    var propertyType: Property.PROPERTY_TYPE? = null

    var furnishing: Property.FURNISHING_TYPE? = null

    var area: Int = 0

    var rent: Long = 0

    var isRoommates: Boolean = false

    val address : String
    get(){
        return "$addressBuildingName, " +
                "$addressLocalityName, " +
                "$addressSubdivision, " +
                "$addressCity, " +
                "$addressState, " +
                "$addressPinCode"
    }


}