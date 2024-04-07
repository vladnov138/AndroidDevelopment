package com.example.gpslocation24

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.gpslocation24.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), LocationListener {
    private val LOCATION_PERM_CODE = 2
    private var _locationManager: LocationManager? = null
    private var _binding: ActivityMainBinding? = null
    private val locationManager: LocationManager get() = _locationManager!!
    private val binding: ActivityMainBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        // запрашиваем разрешения на доступ к геопозиции
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            // переход в запрос разрешений
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERM_CODE)
        } else {
//            binding.updButton.setOnClickListener {
//                Log.d("my", "here")
//                startLocate()
//            }
            startLocate()
        }
    }

    private fun startLocate() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        _locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        Log.d("my", "All Location Providers: ${locationManager.allProviders}")
        Toast.makeText(this, "Available providers: ${locationManager.allProviders}", Toast.LENGTH_SHORT).show()
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)

        val prv = locationManager.getBestProvider(Criteria(), true)
        if (prv != null) {
            val location = locationManager.getLastKnownLocation(prv)
            if (location != null)
                displayCoord(location.latitude, location.longitude)
            Log.d("mytag", "location set: $location")
        }
    }

    override fun onLocationChanged(loc: Location) {
        Toast.makeText(this, "Location changed!", Toast.LENGTH_SHORT).show()
        val lat = loc.latitude
        val lng = loc.longitude
        displayCoord(lat, lng)
        Log.d("my", "lat " + lat + " long " + lng)
    }

    fun displayCoord(latitude: Double, longtitude: Double) {
        findViewById<TextView>(R.id.lat).text = String.format("%.5f", latitude)
        findViewById<TextView>(R.id.lng).text = String.format("%.5f", longtitude)
    }

    override fun onProviderEnabled(provider: String) {
        super.onProviderEnabled(provider)
        Log.d("my", "enabled")
        Toast.makeText(this, "You're online now", Toast.LENGTH_SHORT).show()
        startLocate()
    }

    override fun onProviderDisabled(provider: String) {
        super.onProviderDisabled(provider)
        Log.d("my", "disabled")
        displayCoord(-1.0, -1.0)
        Toast.makeText(this, "You're offline now", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d("my", "$requestCode, $permissions, $grantResults")
        startLocate()
//        binding.updButton.setOnClickListener {
//            Log.d("my", "here")
//            startLocate()
//        }
    }
}