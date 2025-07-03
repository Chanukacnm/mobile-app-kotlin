package com.sevalk.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Service(
    val id: String = "",
    val name: String,
    val iconRes: Int,
    val backgroundColorRes: Int,
    val description: String = "",
    val priceRange: String = ""
) : Parcelable

@Parcelize
data class Booking(
    val id: String = "",
    val service: String,
    val provider: String,
    val time: String,
    val status: BookingStatus,
    val providerInitial: String,
    val userId: String = "",
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable

enum class BookingStatus {
    PENDING,
    CONFIRMED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}

@Parcelize
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
    val phone: String? = null,
    val email: String? = null
) : Parcelable

@Parcelize
data class BookingRequest(
    val serviceId: String,
    val serviceName: String,
    val providerId: String,
    val providerName: String,
    val userId: String,
    val scheduledTime: String,
    val notes: String = ""
) : Parcelable

@Parcelize
data class User(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val profileImageUrl: String? = null,
    val location: String? = null
) : Parcelable
