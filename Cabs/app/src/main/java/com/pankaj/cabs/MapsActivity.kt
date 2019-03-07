package com.pankaj.cabs

import android.annotation.SuppressLint
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.view.View
import android.widget.Toast
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_maps.*
import java.lang.reflect.Array
import java.util.jar.Manifest
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import android.R.attr.apiKey
import android.support.v4.app.FragmentActivity
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.internal.it
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import java.util.*


//import android.support.test.orchestrator.junit.BundleJUnitUtils.getResult





class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    public  fun changeMarker(it:Location)
    {
        lastMarker.remove()

        lastMarker = mMap.addMarker(
            MarkerOptions()
                .position(LatLng(it.latitude,it.longitude))
                .title("New Location")
                .draggable(true)
        )

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude,it.longitude), 17f))
    }
    val TAG = "MainActivity"
    private lateinit var locationCallback: LocationCallback
    private val locationRequest: LocationRequest by lazy {
        LocationRequest.create().apply {
            this.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
            this.interval = 1000
            this.fastestInterval = 500
        }
    }
    private lateinit var placesClient:PlacesClient
    private lateinit var mMap: GoogleMap
    private lateinit var lastMarker: Marker
    private lateinit var lastPosition: LatLng
    private val RQ_LOCATION = 12347

    private val locationProvider: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        Places.initialize(applicationContext, "AIzaSyDjhbFvqkGnTlR96aNoCi-Da60D2SW_HZ8")
        placesClient = Places.createClient(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)



        var autocompleteFragment:AutocompleteSupportFragment = getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment)
                as AutocompleteSupportFragment;

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

// Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                //Log.i("", "Place: " + place.name + ", " + place.id)
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                //Log.i(FragmentActivity.TAG, "An error occurred: $status")
            }
        })

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                RQ_LOCATION
            )
        }
        currentLocationButton.setOnClickListener(View.OnClickListener {
            locationProvider.lastLocation
                .addOnCanceledListener {
                    Toast.makeText(this, "Unable to fetch location, please retry", Toast.LENGTH_SHORT).show()
                }
                .addOnSuccessListener {
                    changeMarker(it)
                }
        })

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
        navButton.setOnClickListener(View.OnClickListener {
            drawer_layout.openDrawer(nav_view)
        })
    }


    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: kotlin.Array<out String>, grantResults: IntArray) {

        when (requestCode) {

            RQ_LOCATION -> {

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationProvider.lastLocation
                        .addOnCanceledListener {
                            Toast.makeText(this, "Unable to fetch location, please retry", Toast.LENGTH_SHORT).show()
                        }
                        .addOnSuccessListener {
                            changeMarker(it)
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Failed to fetch location, please retry", Toast.LENGTH_SHORT).show()
                        }
                        .addOnCompleteListener {
                            Toast.makeText(this, "Location listener completed", Toast.LENGTH_SHORT).show()
                        }
                } else
                    Toast.makeText(this, "Please Grant the Location Permission!", Toast.LENGTH_SHORT).show()
            }
        }

    }
    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        with(mMap.uiSettings) {
            setAllGesturesEnabled(true)
            isZoomControlsEnabled = true
            isCompassEnabled = true
        }

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)

        lastPosition = sydney

        lastMarker = mMap.addMarker(
            MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney")
                .draggable(true)
        )

        /*locationProvider.lastLocation
            .addOnSuccessListener { location : Location? ->
                Log.e("TAG",location!!.latitude.toString())
                lastMarker.remove()

                lastMarker = mMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(location!!.latitude,location.longitude))
                        .title("New Location")
                        .draggable(true)
                )

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location!!.latitude,location.longitude), 20f))

            }*/

      mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12f))
//
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12f))

        mMap.setOnMapClickListener {

            lastMarker.remove()

            lastMarker = mMap.addMarker(
                MarkerOptions()
                    .position(it)
                    .title("New Location")
                    .draggable(true)
            )

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 15f))

        }

        mMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {

            override fun onMarkerDragEnd(marker: Marker) {

                Log.e("TAG", "Last marker : ${lastMarker.position.latitude} ${lastMarker.position.longitude}")
                Log.e("TAG", "Current marker : ${marker.position.latitude} ${marker.position.longitude}")


                mMap.addCircle(
                    CircleOptions()
                        .radius(100.0)
                        .center(lastPosition)
                        .fillColor(ContextCompat.getColor(baseContext, R.color.colorAccent))
                        .strokeColor(ContextCompat.getColor(baseContext, R.color.colorPrimary))
                        .strokeWidth(2f)
                )

                lastPosition = marker.position

                lastMarker.remove()

                lastMarker = mMap.addMarker(
                    MarkerOptions()
                        .position(marker.position)
                        .title("New Location")
                        .draggable(true)
                )

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 10f))
            }

            override fun onMarkerDragStart(marker: Marker) {

//                lastMarker = marker

                Log.e("TAG", "Start marker : ${marker.position.latitude} ${marker.position.longitude}")
            }

            override fun onMarkerDrag(marker: Marker) {

            }

        })

    }

}
