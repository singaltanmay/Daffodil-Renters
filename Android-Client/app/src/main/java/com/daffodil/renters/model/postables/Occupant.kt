package com.daffodil.renters.model.postables

import java.util.Date

data class Occupant(
    var id: Long?,
    var firstName: String?,
    var lastName: String?,
    var phoneNumber: String?,
    var dateMovedIn: Date?,
    var timeLastRentPaid: Date?,
    var room: Room?,
    var rent: Long?,
    var dateRentDue: Date?,
    var parkingSpots: List<ParkingSpot>?
)
