package com.example.servicebooking.models

data class Service(
    val name: String,
    val iconRes: Int,
    val backgroundColorRes: Int
)

data class Booking(
    val id: String = "",
    val service: String,
    val provider: String,
    val time: String,
    val status: BookingStatus,
    val providerInitial: String
)

enum class BookingStatus {
    CONFIRMED,
    PENDING,
    COMPLETED,
    CANCELLED
}

data class Provider(
    val id: String = "",
    val name: String,
    val service: String,
    val rating: Float,
    val reviewCount: Int,
    val distance: String,
    val isAvailable: Boolean,
    val initial: String,
    val profileImageUrl: String? = null
)

data class User(
    val id: String,
    val name: String,
    val email: String,
    val location: String,
    val profileImageUrl: String? = null
)
