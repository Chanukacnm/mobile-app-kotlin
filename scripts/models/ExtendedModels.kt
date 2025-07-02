package com.example.servicebooking.models

// Extended Service model
data class Service(
    val id: String = "",
    val name: String,
    val iconRes: Int,
    val backgroundColorRes: Int,
    val description: String = "",
    val priceRange: String = ""
)

// Extended Booking model
data class Booking(
    val id: String = "",
    val service: String,
    val provider: String,
    val time: String,
    val status: BookingStatus,
    val providerInitial: String,
    val userId: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

// Extended Provider model
data class Provider(
    val id: String = "",
    val name: String,
    val service: String,
    val rating: Float,
    val reviewCount: Int,
    val distance: String,
    val isAvailable: Boolean,
    val initial: String,
    val profileImageUrl: String? = null,
    val phone: String = "",
    val email: String = ""
)

// Booking request model
data class BookingRequest(
    val serviceId: String,
    val serviceName: String,
    val providerId: String,
    val providerName: String,
    val scheduledTime: String,
    val userId: String,
    val notes: String = ""
)

// User model
data class User(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val location: String,
    val profileImageUrl: String? = null,
    val isBusinessOwner: Boolean = false
)

// Review model
data class Review(
    val id: String,
    val providerId: String,
    val userId: String,
    val userName: String,
    val rating: Float,
    val comment: String,
    val createdAt: Long
)

// Notification model
data class Notification(
    val id: String,
    val title: String,
    val message: String,
    val type: NotificationType,
    val isRead: Boolean = false,
    val createdAt: Long,
    val actionData: String? = null
)

enum class NotificationType {
    BOOKING_CONFIRMED,
    BOOKING_CANCELLED,
    BOOKING_REMINDER,
    PROVIDER_MESSAGE,
    SYSTEM_UPDATE
}

enum class BookingStatus {
    PENDING,
    CONFIRMED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}
