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
    
    suspend fun getNearbyProviders(location: String? = null): Resource<List<Provider>> {
        return withContext(Dispatchers.IO) {
            try {
                val providers = if (location != null) {
                    apiClient.getNearbyProviders(location)
                } else {
                    apiClient.getNearbyProviders()
                }
                databaseHelper.insertProviders(providers)
                Resource.Success(providers)
            } catch (e: Exception) {
                try {
                    val localProviders = databaseHelper.getNearbyProviders()
                    Resource.Success(localProviders)
                } catch (localException: Exception) {
                    Resource.Error("Failed to load providers: ${e.message}", emptyList())
                }
            }
        }
    }
    
    suspend fun getProviderById(providerId: String): Resource<Provider> {
        return withContext(Dispatchers.IO) {
            try {
                val provider = apiClient.getProviderById(providerId)
                Resource.Success(provider)
            } catch (e: Exception) {
                try {
                    val localProvider = databaseHelper.getProviderById(providerId)
                    if (localProvider != null) {
                        Resource.Success(localProvider)
                    } else {
                        Resource.Error("Provider not found", null)
                    }
                } catch (localException: Exception) {
                    Resource.Error("Failed to load provider: ${e.message}", null)
                }
            }
        }
    }
    
    suspend fun searchProviders(query: String, serviceType: String? = null): Resource<List<Provider>> {
        return withContext(Dispatchers.IO) {
            try {
                val providers = apiClient.searchProviders(query, serviceType)
                Resource.Success(providers)
            } catch (e: Exception) {
                val localProviders = databaseHelper.searchProviders(query, serviceType)
                Resource.Success(localProviders)
            }
        }
    }
}
