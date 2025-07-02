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
    
    suspend fun getPopularServices(): Resource<List<Service>> {
        return withContext(Dispatchers.IO) {
            try {
                // Try to get from API first
                val apiServices = apiClient.getPopularServices()
                
                // Cache in local database
                databaseHelper.insertServices(apiServices)
                
                Resource.Success(apiServices)
            } catch (e: Exception) {
                // Fallback to local database
                try {
                    val localServices = databaseHelper.getPopularServices()
                    if (localServices.isNotEmpty()) {
                        Resource.Success(localServices)
                    } else {
                        Resource.Error("No services available", null)
                    }
                } catch (localException: Exception) {
                    Resource.Error("Failed to load services: ${e.message}", null)
                }
            }
        }
    }
    
    suspend fun getServiceById(serviceId: String): Resource<Service> {
        return withContext(Dispatchers.IO) {
            try {
                val service = apiClient.getServiceById(serviceId)
                Resource.Success(service)
            } catch (e: Exception) {
                try {
                    val localService = databaseHelper.getServiceById(serviceId)
                    if (localService != null) {
                        Resource.Success(localService)
                    } else {
                        Resource.Error("Service not found", null)
                    }
                } catch (localException: Exception) {
                    Resource.Error("Failed to load service: ${e.message}", null)
                }
            }
        }
    }
    
    suspend fun searchServices(query: String): Resource<List<Service>> {
        return withContext(Dispatchers.IO) {
            try {
                val services = apiClient.searchServices(query)
                Resource.Success(services)
            } catch (e: Exception) {
                val localServices = databaseHelper.searchServices(query)
                if (localServices.isNotEmpty()) {
                    Resource.Success(localServices)
                } else {
                    Resource.Error("No services found for '$query'", null)
                }
            }
        }
    }
}
