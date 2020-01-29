package com.daffodil.renters.model.postables

import java.util.*

class PostableFactory {

    class BuildingBuilder {

        var id: Long? = null
        var addressBuildingName: String? = null
        var addressLocalityName: String? = null
        var addressSubdivision: String? = null
        var addressCity: String? = null
        var addressState: String? = null
        var addressPinCode: String? = null
        var buildingConstructed: Date? = null
        var latitude: Double? = null
        var longitude: Double? = null
        var properties: List<Property>? = null
        var sharedParkingSpots: List<ParkingSpot>? = null

        fun build(): Building {
            return Building(
                this.id,
                this.addressBuildingName,
                this.addressLocalityName,
                this.addressSubdivision,
                this.addressCity,
                this.addressState,
                this.addressPinCode,
                this.buildingConstructed,
                this.latitude,
                this.longitude,
                this.properties,
                this.sharedParkingSpots
            )
        }

    }

    class PropertyBuilder {

        var id: Long = 0
        var unit: String? = null
        var description: String? = null
        var propertyType: Property.PROPERTY_TYPE? = null
        var furnishingType: Property.FURNISHING_TYPE? = null
        var area: Int = 0
        var rent: Long = 0
        var isRoommates: Boolean = false
        var securityDeposit: Long = 0
        var brokerage: Long = 0
        var lockInPeriod: Int = 0
        var listedOn: Date? = null
        var building: Building? = null
        var seller: Seller? = null
        var amenities: Amenities? = null
        var rooms: List<Room>? = null
        var parkingSpots: List<ParkingSpot>? = null

        fun build(): Property {
            return Property(
                this.id,
                this.unit,
                this.description,
                this.propertyType,
                this.furnishingType,
                this.area,
                this.rent,
                this.isRoommates,
                this.securityDeposit,
                this.brokerage,
                this.lockInPeriod,
                this.listedOn,
                this.building,
                this.seller,
                this.amenities,
                this.rooms,
                this.parkingSpots
            )
        }
    }

    class RoomBuilder {
        var id: Long = 0
        var capacity: Long = 0
        var rent: Long = 0
        var occupants: List<Occupant>? = null
        var property: Property? = null

        fun build(): Room {
            return Room(
                this.id,
                this.capacity,
                this.rent,
                this.occupants,
                this.property
            )
        }

    }

    class OccupantBuilder {

        var id: Long = 0
        var firstName: String? = null
        var lastName: String? = null
        var phoneNumber: String? = null
        var dateMovedIn: Date? = null
        var timeRentLastPaid: Date? = null
        var room: Room? = null
        var rent: Long = 0
        var dateRentDue: Date? = null
        var parkingSpots: List<ParkingSpot>? = null

        fun build(): Occupant {
            return Occupant(
                this.id,
                this.firstName,
                this.lastName,
                this.phoneNumber,
                this.dateMovedIn,
                this.timeRentLastPaid,
                this.room,
                this.rent,
                this.dateRentDue,
                this.parkingSpots
            )
        }

    }

    class ParkingSpotBuilder {
        var id: Long = 0
        var isElectric: Boolean = false
        var parkingSize: ParkingSpot.PARKING_SIZE? = null
        var parkingType: ParkingSpot.PARKING_TYPE? = null
        var price: Int = 0
        var building: Building? = null
        var property: Property? = null
        var occupant: Occupant? = null

        fun build(): ParkingSpot {
            return ParkingSpot(
                this.id,
                this.isElectric,
                this.parkingSize,
                this.parkingType,
                this.price,
                this.building,
                this.property,
                this.occupant
            )
        }

    }

    class SellerBuilder {

        var id: Long = 0
        var firstName: String? = null
        var lastName: String? = null
        var phoneNumber: String? = null
        var sellerType: Seller.SELLER_TYPE? = null
        var properties: List<Property>? = null

        fun build(): Seller {

            return Seller(
                this.id,
                this.firstName,
                this.lastName,
                this.phoneNumber,
                this.sellerType,
                this.properties
            )
        }

    }

    class AmenitiesBuilder {

        var id: Long = 0
        var gasPipeline: Boolean = false
        var swimmingPool: Boolean = false
        var gym: Boolean = false
        var lift: Boolean = false
        var gatedCommunity: Boolean = false
        var parking: Boolean = false
        var petsAllowed: Boolean = false
        var properties: List<Property>? = null


        fun build(): Amenities {
            return Amenities(
                this.id,
                this.gasPipeline,
                this.swimmingPool,
                this.gym,
                this.lift,
                this.gatedCommunity,
                this.parking,
                this.petsAllowed,
                this.properties
            )
        }
    }

}
