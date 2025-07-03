package com.example.servicebooking.repository

import com.example.servicebooking.data.local.DatabaseHelper
import com.example.servicebooking.data.remote.ApiClient
import com.example.servicebooking.models.Service
import com.example.servicebooking.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ServiceRepository(
    private val apiClient: ApiClient,
    private val databaseHelper: DatabaseHelper
) {

    suspend fun getPopularServices(): Resource<List<Service>> = withContext(Dispatchers.IO) {
        try {
            // Try to get from local database first
            val localServices = databaseHelper.getPopularServices()
            if (localServices.isNotEmpty()) {
                return@withContext Resource.Success(localServices)
            }

            // If no local data, fetch from API
            val apiServices = apiClient.getPopularServices()
            
            // Save to local database
            databaseHelper.insertServices(apiServices)
            
            Resource.Success(apiServices)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error occurred")
        }
    }

    suspend fun getServiceById(serviceId: String): Resource<Service> = withContext(Dispatchers.IO) {
        try {
            // Try local first
            val localService = databaseHelper.getServiceById(serviceId)
            if (localService != null) {
                return@withContext Resource.Success(localService)
            }

            // Fetch from API
            val apiService = apiClient.getServiceById(serviceId)
            Resource.Success(apiService)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Service not found")
        }
    }

    suspend fun searchServices(query: String): Resource<List<Service>> = withContext(Dispatchers.IO) {
        try {
            // Search locally first
            val localResults = databaseHelper.searchServices(query)
            if (localResults.isNotEmpty()) {
                return@withContext Resource.Success(localResults)
            }

            // Search via API
            val apiResults = apiClient.searchServices(query)
            Resource.Success(apiResults)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Search failed")
        }
    }
}
