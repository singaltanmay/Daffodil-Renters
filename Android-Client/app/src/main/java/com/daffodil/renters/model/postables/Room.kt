package com.daffodil.renters.model.postables

data class Room(
    var id: Long?,
    var capacity: Long?,
    var rent: Long?,
    var occupants: List<Occupant>?,
    var property: Property?
)
