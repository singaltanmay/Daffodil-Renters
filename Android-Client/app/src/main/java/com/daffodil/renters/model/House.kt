package com.daffodil.renters.model

data class House(
    val id: Long?,
    val address: String?,
    val latitude: Double?,
    val longitude: Double?,
    val rooms: List<Room>?,
    val parkingSpots: List<ParkingSpot>? = null
)