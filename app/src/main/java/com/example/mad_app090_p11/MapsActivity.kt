package com.example.mad_app090_p11


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.mad_app090_p11.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private val TAG = "MapActivity"
    private lateinit var mMap: GoogleMap

    private lateinit var binding: ActivityMapsBinding
    private var lat = -34.0
    private var log = 151.0
    private var title = "Marker in Sydney"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val obj = intent.getSerializableExtra("Object") as PersonListCardModel
        Log.i(TAG, "onCreate: Object:$obj")
        log = obj.longitude
        lat = obj.latitude
        title = obj.address
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val sydney = LatLng(lat, log)

        mMap.addMarker(MarkerOptions().position(sydney).title(title))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,8.0f))
    }
}