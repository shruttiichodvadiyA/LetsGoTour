package com.example.letsgotour

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.letsgotour.databinding.ActivityDrawLineMapBinding
import com.example.letsgotour.modal.ItemModal
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.firebase.database.*

class DrawLineMap : AppCompatActivity(), OnMapReadyCallback {
    lateinit var binding: ActivityDrawLineMapBinding
    lateinit var mMap: GoogleMap
    private var locationManager: LocationManager? = null
    private var location: Location? = null
    lateinit var mDbRef: DatabaseReference
    var curr_latLng: LatLng? = null
    var polyline: Polyline? = null
    private val LOCATION_MIN_UPDATE_TIME = 10
    private val LOCATION_MIN_UPDATE_DISTANCE = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDrawLineMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mDbRef = FirebaseDatabase.getInstance().reference
        initView()
        binding.toolbar.setOnClickListener {
            finish()
        }
    }

    private fun initView() {
        var uid = intent.getStringExtra("TourPackage")
        mDbRef.child("Item").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val Item = postSnapshot.getValue(ItemModal::class.java)

                    if (Item?.uid == uid) {
//                        mapload(Item?.latitude,Item?.longitude,Item?.name)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
//    fun getCurrentLocation() {
//        try {
//            if (ContextCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//
//                val isGPSEnabled =
//                    locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
//                val isNetworkEnabled =
//                    locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
//
//
//                if (!isGPSEnabled && !isNetworkEnabled) {
//                    //                    showToast(getString(R.string.please_on_your_GPS_location));
//                    Toast.makeText(this, "please_on_your_GPS_location", Toast.LENGTH_SHORT)
//                        .show()
//                } else {
//                    location = null
//                    if (isGPSEnabled) {
//                        locationManager!!.requestLocationUpdates(
//                            LocationManager.GPS_PROVIDER,
//                            LOCATION_MIN_UPDATE_TIME.toLong(),
//                            LOCATION_MIN_UPDATE_DISTANCE.toFloat(),
//                            locationListener
//                        )
//                        location =
//                            locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//                    }
//                    if (isNetworkEnabled) {
//                        locationManager!!.requestLocationUpdates(
//                            LocationManager.NETWORK_PROVIDER,
//                            LOCATION_MIN_UPDATE_TIME.toLong(),
//                            LOCATION_MIN_UPDATE_DISTANCE.toFloat(),
//                            locationListener
//                        )
//                        location =
//                            locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
//                    }
//                    if (location != null) {
//                        curr_latLng = LatLng(location!!.latitude, location!!.longitude)
//                        drawMarker(curr_latLng!!, false)
//
//
////                        drawline(21.226920897072215, 72.83169425889456, TransportMode.DRIVING)
//                        return
//                    } else {
//                        getCurrentLocation()
//                    }
//                }
//            } else {
//                if (ContextCompat.checkSelfPermission(
//                        this,
//                        Manifest.permission.ACCESS_FINE_LOCATION
//                    ) != PackageManager.PERMISSION_GRANTED
//                ) {
//                    ActivityCompat.requestPermissions(
//                        this,
//                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                        12
//                    )
//                }
//                if (ContextCompat.checkSelfPermission(
//                        this,
//                        Manifest.permission.ACCESS_COARSE_LOCATION
//                    ) != PackageManager.PERMISSION_GRANTED
//                ) {
//                    ActivityCompat.requestPermissions(
//                        this,
//                        arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
//                        13
//                    )
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            onBackPressed()
//        }
//    }

   /* private fun mapload(latitude: Float?, longitude: Float?, name: String?) {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.LoadMap) as SupportMapFragment
        mapFragment.getMapAsync(object : SupportMapFragment(),
            OnMapReadyCallback {
            override fun onMapReady(p0: GoogleMap) {
                mMap = p0
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
//                val destination =
//                    modelObj.lang.toDouble()
//                        .let {
//                            modelObj.long.toDouble()
//                                .let { it1 -> LatLng(it, it1) }
//                        }

                val sydney = LatLng(latitude!!.toDouble(), longitude!!.toDouble())
                mMap.addMarker(

                    MarkerOptions()
                        .position(sydney)
                        .title(name)

                )
                mMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        sydney,
                        14f
                    )
                )
            }
        })
    }
*/
    override fun onMapReady(p0: GoogleMap) {

    }

}