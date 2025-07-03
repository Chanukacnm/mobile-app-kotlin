package com.example.servicebooking.repository

import com.example.servicebooking.data.local.DatabaseHelper
import com.example.servicebooking.data.remote.ApiClient
import com.example.servicebooking.models.Provider
import com.example.servicebooking.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProviderRepository(
    private val apiClient: ApiClient,
    private val databaseHelper: DatabaseHelper
) {

    suspend fun getNearbyProviders(location: String? = null): Resource<List<Provider>> = withContext(Dispatchers.IO) {
        try {
            // Try to get from local database first
            val localProviders = databaseHelper.getNearbyProviders()
            if (localProviders.isNotEmpty()) {
                return@withContext Resource.Success(localProviders)
            }

            // If no local data, fetch from API
            val apiProviders = apiClient.getNearbyProviders(location)
            
            // Save to local database
            databaseHelper.insertProviders(apiProviders)
            
            Resource.Success(apiProviders)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to load providers")
        }
    }

    suspend fun getProviderById(providerId: String): Resource<Provider> = withContext(Dispatchers.IO) {
        try {
            // Try local first
            val localProvider = databaseHelper.getProviderById(providerId)
            if (localProvider != null) {
                return@withContext Resource.Success(localProvider)
            }

            // Fetch from API
            val apiProvider = apiClient.getProviderById(providerId)
            Resource.Success(apiProvider)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Provider not found")
        }
    }

    suspend fun searchProviders(query: String, serviceType: String? = null): Resource<List<Provider>> = withContext(Dispatchers.IO) {
        try {
            // Search locally first
            val localResults = databaseHelper.searchProviders(query, serviceType)
            if (localResults.isNotEmpty()) {
                return@withContext Resource.Success(localResults)
            }

            // Search via API
            val apiResults = apiClient.searchProviders(query, serviceType)
            Resource.Success(apiResults)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Search failed")
        }
    }
}
