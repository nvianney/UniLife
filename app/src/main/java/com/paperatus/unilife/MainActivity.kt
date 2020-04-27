package com.paperatus.unilife

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.appbar.AppBarLayout
import com.paperatus.unilife.fragments.AdvisorFragment
import com.paperatus.unilife.fragments.HomeFragment
import com.paperatus.unilife.fragments.MapFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var recentLocation: Location? = null
    val home = HomeFragment()
    val map = MapFragment(this)
    val advising = AdvisorFragment()

    val locationRequest = LocationRequest.create()?.apply {
        interval = 10000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    lateinit var active: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                recentLocation = locationResult.lastLocation
            }
        }



        Courses.read(this)

        setSupportActionBar(Toolbar)

        supportFragmentManager.beginTransaction().add(R.id.fragment_container, map).commit()
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, advising).hide(map).commit()
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, home).hide(advising).commit()
        active = home

        bottom_navigation.setOnNavigationItemSelectedListener {item ->
            when(item.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction().hide(active).show(home).commit()
                    active = home
                    true
                }
                R.id.navigation_map -> {
                    supportFragmentManager.beginTransaction().hide(active).show(map).commit()
                    active = map
                    true
                }
                R.id.navigation_help -> {
                    supportFragmentManager.beginTransaction().hide(active).show(advising).commit()
                    active = advising
                    true
                }
                else -> false
            }
        }

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }

    override fun onResume() {
        super.onResume()

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    override fun onPause() {
        super.onPause()

        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onBackPressed() {
        if (active == home) {
            home.back()
        }
    }


}
