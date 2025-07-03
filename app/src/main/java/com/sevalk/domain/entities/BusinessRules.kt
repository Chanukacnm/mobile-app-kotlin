package com.sevalk.domain.entities

object BusinessRules {
    
    // Service booking rules
    const val MAX_BOOKINGS_PER_USER = 10
    const val MIN_BOOKING_ADVANCE_HOURS = 2
    const val MAX_BOOKING_ADVANCE_DAYS = 30
    
    // Provider rules
    const val MIN_PROVIDER_RATING = 1.0f
    const val MAX_PROVIDER_RATING = 5.0f
    const val MIN_PROVIDER_DISTANCE_KM = 0.1
    const val MAX_PROVIDER_DISTANCE_KM = 50.0
    
    // Service rules
    const val MIN_SERVICE_PRICE = 10.0
    const val MAX_SERVICE_PRICE = 1000.0
    
    // User validation rules
    const val MIN_USER_AGE = 18
    const val MAX_USER_AGE = 100
    
    // Business hours
    const val BUSINESS_START_HOUR = 8
    const val BUSINESS_END_HOUR = 20
    
    // Booking status transitions
    fun canTransitionBookingStatus(from: String, to: String): Boolean {
        return when (from) {
            "PENDING" -> to in listOf("CONFIRMED", "CANCELLED")
            "CONFIRMED" -> to in listOf("IN_PROGRESS", "CANCELLED")
            "IN_PROGRESS" -> to in listOf("COMPLETED", "CANCELLED")
            "COMPLETED" -> false
            "CANCELLED" -> false
            else -> false
        }
    }
    
    // Provider availability rules
    fun isProviderAvailable(rating: Float, reviewCount: Int): Boolean {
        return rating >= MIN_PROVIDER_RATING && reviewCount >= 5
    }
    
    // Service pricing validation
    fun isValidServicePrice(price: Double): Boolean {
        return price in MIN_SERVICE_PRICE..MAX_SERVICE_PRICE
    }
    
    // Distance validation
    fun isWithinServiceArea(distanceKm: Double): Boolean {
        return distanceKm in MIN_PROVIDER_DISTANCE_KM..MAX_PROVIDER_DISTANCE_KM
    }
}
