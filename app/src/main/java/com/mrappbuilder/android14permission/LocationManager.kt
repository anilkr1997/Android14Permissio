package com.mrappbuilder.android14permission

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

open class LocationManager(
  private val context: Context,
    private var timeInterval: Long,
    private var minimalDistance: Float
) : LocationCallback() {

    private var request: LocationRequest
    private var locationClient: FusedLocationProviderClient
    companion object{
        val locationdata=MutableLiveData<LocationResult>()
    }

    init {
        // getting the location client
        locationClient = LocationServices.getFusedLocationProviderClient(context)
        request = createRequest()
    }

    private fun createRequest(): LocationRequest =
        // New builder
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, timeInterval).apply {
            setMinUpdateDistanceMeters(minimalDistance)
            setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
            setWaitForAccurateLocation(true)
        }.build()

    fun changeRequest(timeInterval: Long, minimalDistance: Float) {
        this.timeInterval = timeInterval
        this.minimalDistance = minimalDistance
        createRequest()
        stopLocationTracking()
        startLocationTracking()
    }

    @SuppressLint("MissingPermission")
    fun startLocationTracking() =
        locationClient.requestLocationUpdates(request, this, Looper.getMainLooper())


    fun stopLocationTracking() {
        locationClient.flushLocations()
        locationClient.removeLocationUpdates(this)
    }

    override fun onLocationResult(location: LocationResult) {
        locationdata.postValue(location)
        Log.e("TAG", "onLocationResult: ${location.locations.get(0).latitude}   ${location.locations.get(0).longitude}", )
    }

    override fun onLocationAvailability(availability: LocationAvailability) {
        // TODO: react on the availability change
    }

}

public interface Getlocation{
    fun onLocationResult(location: LocationResult)
    fun onLocationAvailability(availability: LocationAvailability)
}