package com.daffodil.renters.model.postables

import java.util.Date

data class Property(
    var id: Long?,
    var unit: String?,
    var description: String?,
    var propertyType: PROPERTY_TYPE? = PROPERTY_TYPE.APARTMENT,
    var furnishingType: FURNISHING_TYPE? = FURNISHING_TYPE.UNFURNISHED,
    var area: Int?,
    var rent: Long?,
    var roommates: Boolean?,
    var securityDeposit: Long?,
    var brokerage: Long?,
    var lockInPeriod: Int?,
    var listedOn: Date?,
    var building: Building?,
    var seller: Seller?,
    var amenities: Amenities?,
    var rooms: List<Room>?,
    var parkingSpots: List<ParkingSpot>?
) {


    enum class PROPERTY_TYPE {
        APARTMENT,
        INDEPENDENT_FLOOR,
        INDEPENDENT_HOUSE,
        PG
    }

    enum class FURNISHING_TYPE {
        FULLY_FURNISHED,
        SEMI_FURNISHED,
        UNFURNISHED
    }
}
