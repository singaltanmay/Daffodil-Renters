package com.daffodil.renters.model

import com.daffodil.renters.model.postables.Amenities
import com.daffodil.renters.model.postables.ParkingSpot
import com.daffodil.renters.model.postables.Seller
import java.util.*

class Listing : ListingSkeletal() {

    var description: String? = null
    var amenities: Amenities? = null
    var seller: Seller? = null
    var securityDeposit: Long = 0
    var brokerage: Long = 0
    var ageOfPropertyMonths: Int = 0
    var lockInPeriod: Int = 0
    var listedOn: Date? = null
    var sharedParkingSpots: List<ParkingSpot>? = null

    inner class FormattedStrings : ListingSkeletal.FormattedStrings() {

        fun getSellerFullNameSentenceCase() =
            "${seller?.firstName?.toLowerCase()?.capitalize()} ${seller?.lastName?.toLowerCase()?.capitalize()}"

        fun getSellerTypeSentenceCase() =
            seller?.sellerType?.toString()?.toLowerCase()?.capitalize()

    }

}
