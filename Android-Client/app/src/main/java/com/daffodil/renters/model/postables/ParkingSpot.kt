package com.daffodil.renters.model.postables

open class ParkingSpot(
    var id: Long? = null,
    var electric: Boolean? = false,
    var parkingSize: PARKING_SIZE? = PARKING_SIZE.CAR,
    var parkingType: PARKING_TYPE? = PARKING_TYPE.GENERAL,
    var price: Int? = 0,
    var building: Building? = null,
    var property: Property? = null,
    var occupant: Occupant? = null
) {

    enum class PARKING_SIZE {
        BICYCLE,
        SCOOTER,
        CAR,
        MINI_TRUCK
    }

    enum class PARKING_TYPE {
        GENERAL,
        RESERVED,
        HANDICAPPED,
        EMERGENCY_VEHICLE
    }


}
