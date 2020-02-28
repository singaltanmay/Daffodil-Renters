package com.daffodil.renters.model

import com.daffodil.renters.model.postables.Property
import kotlin.math.roundToInt

open class ListingSkeletal {

    var propertyId: Long = 0

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

    var roommates: Boolean = false

    var distanceKm: Double = 0.0

    open inner class FormattedStrings {

        fun getRentPerMonth(symbol: String? = "₹", factor: Double? = 1.0) =
            "$symbol ${rent.times(factor ?: 1.0).toLong()} /month"

        fun getHouseSizeAndType() = "$bedrooms BHK ${propertyType.toString().toSentenceCase()}"

        fun getArea(unit: String? = "sq. ft.", factor: Double? = 1.0) =
            "${area.times(factor ?: 1.0).toInt()} $unit"

        fun getFurnishingTypeSentenceCase() = furnishing.toString().toSentenceCase()

        fun getRoundedDistanceWithUnit(): String {

            var distance = distanceKm
            val string: String
            val distInt = distance.times(1000).roundToInt()
            if (distInt < 1000) {
                string = "$distInt m"
            } else {
                // 2509
                string =
                    "${distInt.toDouble()/*2509.0*/.div(100)/*25.09*/.roundToInt()/*25*/.toDouble()/*25.0*/.div(
                        10
                    )/*2.5*/} Km"
            }
            return string
        }

        fun getFullAddress(): String = "$addressBuildingName, " +
                "$addressLocalityName, " +
                "$addressSubdivision, " +
                "$addressCity, " +
                "$addressState, " +
                "$addressPinCode"

        fun getFullAddressIndented(): String = "$addressBuildingName,\n" +
                "$addressLocalityName,\n" +
                "$addressSubdivision,\n" +
                "$addressCity,\n" +
                "$addressState,\n" +
                "$addressPinCode"



        fun String.toSentenceCase() = this.toLowerCase().capitalize()

    }


}