package com.daffodil.renters.model.postables

import java.util.Date

data class Building(
    var id: Long?,
    var addressBuildingName: String?,
    var addressLocalityName: String?,
    var addressSubdivision: String?,
    var addressCity: String?,
    var addressState: String?,
    var addressPinCode: String?,
    var buildingConstructed: Date?,
    var latitude: Double?,
    var longitude: Double?,
    var properties: List<Property>?,
    var sharedParkingSpots: List<ParkingSpot>?
)
