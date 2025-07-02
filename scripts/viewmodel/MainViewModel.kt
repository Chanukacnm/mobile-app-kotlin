package com.example.servicebooking.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.servicebooking.models.Service
import com.example.servicebooking.models.Booking
import com.example.servicebooking.models.Provider
import com.example.servicebooking.repository.ServiceRepository
import com.example.servicebooking.repository.BookingRepository
import com.example.servicebooking.repository.ProviderRepository
import com.example.servicebooking.utils.Resource
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
    
    private val _navigationEvent = MutableLiveData<NavigationEvent>()
    val navigationEvent: LiveData<NavigationEvent> = _navigationEvent
    
    fun loadHomeData() {
        viewModelScope.launch {
            _isLoading.value = true
            
            try {
                // Load popular services
                when (val servicesResult = serviceRepository.getPopularServices()) {
                    is Resource.Success -> _popularServices.value = servicesResult.data
                    is Resource.Error -> _errorMessage.value = servicesResult.message
                }
                
                // Load user bookings
                when (val bookingsResult = bookingRepository.getUserBookings()) {
                    is Resource.Success -> _userBookings.value = bookingsResult.data
                    is Resource.Error -> _errorMessage.value = bookingsResult.message
                }
                
                // Load nearby providers
                when (val providersResult = providerRepository.getNearbyProviders()) {
                    is Resource.Success -> _nearbyProviders.value = providersResult.data
                    is Resource.Error -> _errorMessage.value = providersResult.message
                }
                
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun updateUserLocation(location: String) {
        _userLocation.value = location
        loadNearbyProviders(location)
    }
    
    private fun loadNearbyProviders(location: String) {
        viewModelScope.launch {
            when (val result = providerRepository.getNearbyProviders(location)) {
                is Resource.Success -> _nearbyProviders.value = result.data
                is Resource.Error -> _errorMessage.value = result.message
            }
        }
    }
    
    // Navigation methods
    fun onServiceSelected(service: Service) {
        _navigationEvent.value = NavigationEvent.ToServiceDetail(service.id)
    }
    
    fun onBookingSelected(booking: Booking) {
        _navigationEvent.value = NavigationEvent.ToBookingDetail(booking.id)
    }
    
    fun onProviderSelected(provider: Provider) {
        _navigationEvent.value = NavigationEvent.ToProviderDetail(provider.id)
    }
    
    fun navigateToServices() {
        _navigationEvent.value = NavigationEvent.ToServices
    }
    
    fun navigateToBusiness() {
        _navigationEvent.value = NavigationEvent.ToBusiness
    }
    
    fun navigateToAllBookings() {
        _navigationEvent.value = NavigationEvent.ToAllBookings
    }
    
    fun navigateToAllProviders() {
        _navigationEvent.value = NavigationEvent.ToAllProviders
    }
    
    fun navigateToNotifications() {
        _navigationEvent.value = NavigationEvent.ToNotifications
    }
    
    fun navigateToProfile() {
        _navigationEvent.value = NavigationEvent.ToProfile
    }
    
    sealed class NavigationEvent {
        object ToServices : NavigationEvent()
        object ToBusiness : NavigationEvent()
        object ToAllBookings : NavigationEvent()
        object ToAllProviders : NavigationEvent()
        object ToNotifications : NavigationEvent()
        object ToProfile : NavigationEvent()
        data class ToServiceDetail(val serviceId: String) : NavigationEvent()
        data class ToBookingDetail(val bookingId: String) : NavigationEvent()
        data class ToProviderDetail(val providerId: String) : NavigationEvent()
    }
}
