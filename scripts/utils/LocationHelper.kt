package com.example.servicebooking.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import java.util.*

class LocationHelper(private val context: Context) {
    
    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private val geocoder = Geocoder(context, Locale.getDefault())
    
    fun getCurrentLocation(callback: (String) -> Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            callback("Location permission not granted")
            return
        }
        
        try {
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            
            if (location != null) {
                getAddressFromLocation(location) { address ->
                    callback(address)
                }
            } else {
                callback("Wellgama, Southern Province") // Default location
            }
        } catch (e: Exception) {
            callback("Wellgama, Southern Province") // Default location
        }
    }
    
    private fun getAddressFromLocation(location: Location, callback: (String) -> Unit) {
        try {
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (addresses?.isNotEmpty() == true) {
                val address = addresses[0]
                val locationString = "${address.locality ?: ""}, ${address.adminArea ?: ""}"
                callback(locationString.trim().removePrefix(",").trim())
            } else {
                callback("Wellgama, Southern Province")
            }
        } catch (e: Exception) {
            callback("Wellgama, Southern Province")
        }
    }
    
    fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Float {
        val results = FloatArray(1)
        Location.distanceBetween(lat1, lon1, lat2, lon2, results)
        return results[0] / 1000 // Convert to kilometers
    }
}
