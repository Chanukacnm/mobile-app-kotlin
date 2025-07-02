package com.example.servicebooking.repository

import com.example.servicebooking.data.local.DatabaseHelper
import com.example.servicebooking.data.remote.ApiClient
import com.example.servicebooking.models.Booking
import com.example.servicebooking.models.BookingRequest
import com.example.servicebooking.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookingRepository(
    private val apiClient: ApiClient,
    private val databaseHelper: DatabaseHelper
) {
    
    suspend fun getUserBookings(): Resource<List<Booking>> {
        return withContext(Dispatchers.IO) {
            try {
                val bookings = apiClient.getUserBookings()
                databaseHelper.insertBookings(bookings)
                Resource.Success(bookings)
            } catch (e: Exception) {
                try {
                    val localBookings = databaseHelper.getUserBookings()
                    Resource.Success(localBookings)
                } catch (localException: Exception) {
                    Resource.Error("Failed to load bookings: ${e.message}", emptyList())
                }
            }
        }
    }
    
    suspend fun createBooking(bookingRequest: BookingRequest): Resource<Booking> {
        return withContext(Dispatchers.IO) {
            try {
                val booking = apiClient.createBooking(bookingRequest)
                databaseHelper.insertBooking(booking)
                Resource.Success(booking)
            } catch (e: Exception) {
                Resource.Error("Failed to create booking: ${e.message}", null)
            }
        }
    }
    
    suspend fun updateBookingStatus(bookingId: String, status: String): Resource<Booking> {
        return withContext(Dispatchers.IO) {
            try {
                val booking = apiClient.updateBookingStatus(bookingId, status)
                databaseHelper.updateBooking(booking)
                Resource.Success(booking)
            } catch (e: Exception) {
                Resource.Error("Failed to update booking: ${e.message}", null)
            }
        }
    }
    
    suspend fun cancelBooking(bookingId: String): Resource<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val success = apiClient.cancelBooking(bookingId)
                if (success) {
                    databaseHelper.deleteBooking(bookingId)
                }
                Resource.Success(success)
            } catch (e: Exception) {
                Resource.Error("Failed to cancel booking: ${e.message}", false)
            }
        }
    }
}
