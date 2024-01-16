package com.mrappbuilder.android14permission

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.mrappbuilder.android14permission.LocationManager.Companion.locationdata
import kotlinx.coroutines.launch


class GPSActivity : AppCompatActivity(),Getlocation {



    var latitudeTextView: TextView? = null
    var longitTextView: // Initializing other items
    // from layout file
    android.widget.TextView? = null
    var PERMISSION_ID = 44
    lateinit var  locationManager:LocationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gpsactivity)
        latitudeTextView = findViewById(R.id.latTextView);
        longitTextView = findViewById(R.id.lonTextView);


       locationManager=LocationManager(this@GPSActivity,5,0f)
      if(checkPermission()){
          locationManager.startLocationTracking()
      }else{
          requestPermission()
      }
lifecycleScope.launch {


}
        locationdata.observe(this, Observer {
            Toast.makeText(this, "${it.locations.get(0).longitude}  ${it.locations.get(0).latitude}", Toast.LENGTH_SHORT).show()
        })

    }
    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(applicationContext,
           android. Manifest.permission.ACCESS_FINE_LOCATION
        )
        val result1 = ContextCompat.checkSelfPermission(applicationContext,
           android. Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(
           android. Manifest.permission.ACCESS_FINE_LOCATION,
          android.  Manifest.permission.ACCESS_COARSE_LOCATION
        ),
            200
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 200) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED &&  grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                locationManager.startLocationTracking()
            }  else {
                requestPermission()
                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onLocationResult(location: LocationResult) {
        Log.e("TAG", "onLocationResult: ", )
    }

    override fun onLocationAvailability(availability: LocationAvailability) {

    }

}