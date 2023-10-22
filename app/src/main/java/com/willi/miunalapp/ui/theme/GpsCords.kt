package com.willi.miunalapp.ui.theme

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine

class GpsCords {

    @OptIn(ExperimentalCoroutinesApi::class)
    @SuppressLint("MissingPermission")
    suspend fun getUserLocation(context:Context):Location?{
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        val isUserPermissionProvider = true
        val locationManager:LocationManager =context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnable:Boolean =locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if(!isGpsEnable || !isUserPermissionProvider){
            return null
        }

        return suspendCancellableCoroutine { cont ->
            fusedLocationProviderClient.lastLocation.apply {
                if(isComplete){
                    if(isSuccessful){
                        cont.resume(result){}
                    }else{
                        cont.resume(null){}
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    cont.resume(it){}
                }

            } }
    }
}