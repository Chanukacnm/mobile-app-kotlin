package com.example.servicebooking.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val id: String,
    val userId: String,
    val userName: String,
    val providerId: String,
    val serviceId: String,
    val rating: Float,
    val comment: String,
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable

@Parcelize
data class ServiceCategory(
    val id: String,
    val name: String,
    val iconRes: Int,
    val services: List<Service> = emptyList()
) : Parcelable

@Parcelize
data class Location(
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val city: String,
    val state: String,
    val zipCode: String
) : Parcelable

@Parcelize
data class BusinessProfile(
    val id: String,
    val businessName: String,
    val ownerName: String,
    val description: String,
    val services: List<String>,
    val location: Location,
    val phone: String,
    val email: String,
    val website: String? = null,
    val profileImageUrl: String? = null,
    val coverImageUrl: String? = null,
    val isVerified: Boolean = false,
    val rating: Float = 0f,
    val reviewCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable

@Parcelize
data class Notification(
    val id: String,
    val userId: String,
    val title: String,
    val message: String,
    val type: NotificationType,
    val isRead: Boolean = false,
    val data: Map<String, String> = emptyMap(),
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable

enum class NotificationType {
    BOOKING_CONFIRMED,
    BOOKING_CANCELLED,
    BOOKING_COMPLETED,
    NEW_MESSAGE,
    PAYMENT_RECEIVED,
    REVIEW_RECEIVED,
    SYSTEM_UPDATE
}

@Parcelize
data class Message(
    val id: String,
    val conversationId: String,
    val senderId: String,
    val receiverId: String,
    val content: String,
    val messageType: MessageType = MessageType.TEXT,
    val isRead: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable

enum class MessageType {
    TEXT,
    IMAGE,
    LOCATION,
    BOOKING_REQUEST,
    SYSTEM
}

@Parcelize
data class Conversation(
    val id: String,
    val participants: List<String>,
    val lastMessage: Message? = null,
    val unreadCount: Int = 0,
    val updatedAt: Long = System.currentTimeMillis()
) : Parcelable
