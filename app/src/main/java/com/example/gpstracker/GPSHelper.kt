package com.example.gpstracker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Process
import android.util.Log


class GPSHelper private constructor() : LocationListener{

    companion object {
        var showLocation: (()->Unit)? = null
        var imHere: Location? = null
        val listener by lazy { GPSHelper() }
        fun getInstance(context: Context){
            val locationManager = context.getSystemService(
                Context.LOCATION_SERVICE
            ) as LocationManager
            Log.d("LISTENER", listener.toString())
            if (
                context.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION,
                    Process.myPid(), Process.myUid()) ==
                PackageManager.PERMISSION_GRANTED
            )
            {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5L,
                    10.0F,
                    listener
                )
                imHere = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            } else {
                Log.e("GPS ERROR", "NO GPS PERMISSION GRANTED")
            }
        }
    }

    override fun onLocationChanged(location: Location?) {
        imHere = location
        showLocation?.invoke()
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }
}