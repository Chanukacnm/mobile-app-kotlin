package com.example.servicebooking.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.servicebooking.repository.BookingRepository
import com.example.servicebooking.repository.ProviderRepository
import com.example.servicebooking.repository.ServiceRepository

class MainViewModelFactory(
    private val serviceRepository: ServiceRepository,
    private val bookingRepository: BookingRepository,
    private val providerRepository: ProviderRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(serviceRepository, bookingRepository, providerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
