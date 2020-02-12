package com.daffodil.renters.model.postables

import java.util.*

class Occupant : User() {
    var dateMovedIn: Date? = null
    var timeLastRentPaid: Date? = null
    var room: Room? = null
    var rent: Long? = null
    var dateRentDue: Date? = null
    var parkingSpots: List<ParkingSpot>? = null
}
