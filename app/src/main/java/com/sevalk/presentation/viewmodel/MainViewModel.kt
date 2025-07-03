package com.sevalk.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sevalk.data.models.Booking
import com.sevalk.data.models.Provider
import com.sevalk.data.models.Service
import com.sevalk.data.repositories.BookingRepository
import com.sevalk.data.repositories.ProviderRepository
import com.sevalk.data.repositories.ServiceRepository
import com.sevalk.utils.Resource
import kotlinx.coroutines.launch

class MainViewModel(
    private val serviceRepository: ServiceRepository,
    private val bookingRepository: BookingRepository,
    private val providerRepository: ProviderRepository
) : ViewModel() {

    private val _popularServices = MutableLiveData<List<Service>>()
    val popularServices: LiveData<List<Service>> = _popularServices

    private val _userBookings = MutableLiveData<List<Booking>>()
    val userBookings: LiveData<List<Booking>> = _userBookings

    private val _nearbyProviders = MutableLiveData<List<Provider>>()
    val nearbyProviders: LiveData<List<Provider>> = _nearbyProviders

    private val _userLocation = MutableLiveData<String>()
    val userLocation: LiveData<String> = _userLocation

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun loadHomeData() {
        loadPopularServices()
        loadUserBookings()
        loadNearbyProviders()
    }

    fun loadPopularServices() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                when (val result = serviceRepository.getPopularServices()) {
                    is Resource.Success -> {
                        _popularServices.value = result.data ?: emptyList()
                    }
                    is Resource.Error -> {
                        _errorMessage.value = result.message
                    }
                    is Resource.Loading -> {
                        // Handle loading state
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadUserBookings() {
        viewModelScope.launch {
            try {
                when (val result = bookingRepository.getUserBookings()) {
                    is Resource.Success -> {
                        _userBookings.value = result.data ?: emptyList()
                    }
                    is Resource.Error -> {
                        _errorMessage.value = result.message
                    }
                    is Resource.Loading -> {
                        // Handle loading state
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun loadNearbyProviders() {
        viewModelScope.launch {
            try {
                when (val result = providerRepository.getNearbyProviders()) {
                    is Resource.Success -> {
                        _nearbyProviders.value = result.data ?: emptyList()
                    }
                    is Resource.Error -> {
                        _errorMessage.value = result.message
                    }
                    is Resource.Loading -> {
                        // Handle loading state
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun onServiceSelected(service: Service) {
        // Handle service selection
    }

    fun onBookingSelected(booking: Booking) {
        // Handle booking selection
    }

    fun onProviderSelected(provider: Provider) {
        // Handle provider selection
    }

    fun updateUserLocation(location: String) {
        _userLocation.value = location
    }

    fun navigateToServices() {
        // Handle navigation to services
    }

    fun navigateToBusiness() {
        // Handle navigation to business
    }

    fun navigateToAllBookings() {
        // Handle navigation to all bookings
    }

    fun navigateToAllProviders() {
        // Handle navigation to all providers
    }

    fun navigateToNotifications() {
        // Handle navigation to notifications
    }

    fun navigateToProfile() {
        // Handle navigation to profile
    }
}
