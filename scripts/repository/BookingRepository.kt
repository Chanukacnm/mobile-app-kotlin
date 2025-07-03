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

    suspend fun getUserBookings(): Resource<List<Booking>> = withContext(Dispatchers.IO) {
        try {
            // Try to get from local database first
            val localBookings = databaseHelper.getUserBookings()
            if (localBookings.isNotEmpty()) {
                return@withContext Resource.Success(localBookings)
            }

            // If no local data, fetch from API
            val apiBookings = apiClient.getUserBookings()
            
            // Save to local database
            databaseHelper.insertBookings(apiBookings)
            
            Resource.Success(apiBookings)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to load bookings")
        }
    }

    suspend fun createBooking(request: BookingRequest): Resource<Booking> = withContext(Dispatchers.IO) {
        try {
            val newBooking = apiClient.createBooking(request)
            
            // Save to local database
            databaseHelper.insertBooking(newBooking)
            
            Resource.Success(newBooking)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to create booking")
        }
    }

    suspend fun updateBookingStatus(bookingId: String, status: String): Resource<Booking> = withContext(Dispatchers.IO) {
        try {
            val updatedBooking = apiClient.updateBookingStatus(bookingId, status)
            
            // Update local database
            databaseHelper.updateBooking(updatedBooking)
            
            Resource.Success(updatedBooking)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to update booking")
        }
    }

    suspend fun cancelBooking(bookingId: String): Resource<Boolean> = withContext(Dispatchers.IO) {
        try {
            val success = apiClient.cancelBooking(bookingId)
            
            if (success) {
                // Remove from local database
                databaseHelper.deleteBooking(bookingId)
            }
            
            Resource.Success(success)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to cancel booking")
        }
    }
}
