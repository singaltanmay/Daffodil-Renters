package com.daffodil.renters.model.postables

data class Amenities(
    var id: Long?,
    var gasPipeline: Boolean?,
    var swimmingPool: Boolean?,
    var gym: Boolean?,
    var lift: Boolean?,
    var gatedCommunity: Boolean?,
    var parking: Boolean?,
    var petsAllowed: Boolean?,
    var properties: List<Property>?
)
