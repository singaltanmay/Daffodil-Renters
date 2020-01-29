package com.daffodil.renters.model

import com.daffodil.renters.model.postables.Amenities
import com.daffodil.renters.model.postables.ParkingSpot
import com.daffodil.renters.model.postables.Seller

import java.util.Date

class Listing : ListingSkeletal() {

    var description: String? = null
    var amenities: Amenities? = null
    var seller: Seller? = null
    var securityDeposit: Long = 0
    var brokerage: Long = 0
    var ageOfProperty: Int = 0
    var lockInPeriod: Int = 0
    var listedOn: Date? = null
    var parkingSpots: List<ParkingSpot>? = null
}
