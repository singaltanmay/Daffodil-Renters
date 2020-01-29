package com.daffodil.renters.model.postables

data class ParkingSpot(
    var id: Long?,
    var electric: Boolean?,
    var parkingSize: PARKING_SIZE? = PARKING_SIZE.CAR,
    var parkingType: PARKING_TYPE? = PARKING_TYPE.GENERAL,
    var price: Int?,
    var building: Building?,
    var property: Property?,
    var occupant: Occupant?
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
