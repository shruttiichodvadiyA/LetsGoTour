package com.example.letsgotour

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.akexorcist.googledirection.DirectionCallback
import com.akexorcist.googledirection.GoogleDirection
import com.akexorcist.googledirection.constant.AvoidType
import com.akexorcist.googledirection.constant.TransportMode
import com.akexorcist.googledirection.model.Direction
import com.akexorcist.googledirection.model.Route
import com.akexorcist.googledirection.util.DirectionConverter
import com.example.letsgotour.modal.ItemModal
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.database.*

class MapsPlaceDrawActivity : FragmentActivity(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null
    private var locationManager: LocationManager? = null
    private var location: Location? = null
    var curr_latLng: LatLng? = null
    var polyline: Polyline? = null
    lateinit var mDbRef: DatabaseReference
    private val LOCATION_MIN_UPDATE_TIME = 10
    private val LOCATION_MIN_UPDATE_DISTANCE = 1000
    lateinit var imgBack : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapsdraw)
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        mDbRef = FirebaseDatabase.getInstance().reference
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.LoadMap) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        imgBack=findViewById(R.id.imgBack)
        imgBack.setOnClickListener {
            finish()
        }

    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
//            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//            drawMarker(latLng);
//            locationManager.removeUpdates(locationListener);
        }

        override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {}
        override fun onProviderEnabled(s: String) {}
        override fun onProviderDisabled(s: String) {}
    }

    //                    showToast(getString(R.string.please_on_your_GPS_location));


    private fun drawMarker(latLng: LatLng, flag: Boolean) {
        if (mMap != null) {
//            mMap!!.clear()
            val markerOptions = MarkerOptions()
            markerOptions.position(latLng)
            //            markerOptions.title(title);
            if (flag) {
//                markerOptions.icon(BitmapFromVector(this, R.drawable.ic_map_event));
                markerOptions.icon(BitmapFromVector(this, R.drawable.bottomtoolmap))
            } else {
                Log.e("TAG", "drawMarker: mark")
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            }
            mMap!!.addMarker(markerOptions)
            //            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
            mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
        }
    }

    private fun BitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        getCurrentLocation()

        var uid = intent.getStringExtra("TourPackage")
        if (uid != null) {
            mDbRef.child("Item").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (postSnapshot in snapshot.children) {
                        val Item = postSnapshot.getValue(ItemModal::class.java)

                        if (Item?.uid == uid) {
                            var latitude = Item?.latitude
                            var longitude = Item?.longitude


                            var latlng = LatLng(latitude!!.toDouble(), longitude!!.toDouble())
                            drawMarker(latlng, false)

                            drawline(latlng.latitude, latlng.longitude, TransportMode.DRIVING)
//                        mapload(Item?.latitude,Item?.longitude,Item?.name)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }

    }
    fun getCurrentLocation() {
        try {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {

                val isGPSEnabled =
                    locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
                val isNetworkEnabled =
                    locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)


                if (!isGPSEnabled && !isNetworkEnabled) {
                    //                    showToast(getString(R.string.please_on_your_GPS_location));
                    Toast.makeText(this, "please_on_your_GPS_location", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    location = null
                    if (isGPSEnabled) {
                        locationManager!!.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            LOCATION_MIN_UPDATE_TIME.toLong(),
                            LOCATION_MIN_UPDATE_DISTANCE.toFloat(),
                            locationListener
                        )
                        location =
                            locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    }
                    if (isNetworkEnabled) {
                        locationManager!!.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            LOCATION_MIN_UPDATE_TIME.toLong(),
                            LOCATION_MIN_UPDATE_DISTANCE.toFloat(),
                            locationListener
                        )
                        location =
                            locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    }
                    if (location != null) {
                        curr_latLng = LatLng(location!!.latitude, location!!.longitude)
                        drawMarker(curr_latLng!!, false)


//                        drawline(21.226920897072215, 72.83169425889456, TransportMode.DRIVING)
                        return
                    } else {
                        getCurrentLocation()
                    }
                }
            } else {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        12
                    )
                }
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                        13
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onBackPressed()
        }
    }
    private fun drawline(lat: Double, longt: Double, mode: String) {
        try {
//            if (isfromDetailScreen) {
            drawMarker(LatLng(lat, longt), false)
            //            }
            GoogleDirection.withServerKey("AIzaSyBv6cUUv3hbIEDcG69F297b37KqrTjepSg")
                .from(LatLng(curr_latLng!!.latitude, curr_latLng!!.longitude))
                .to(LatLng(lat, longt))
                .avoid(AvoidType.FERRIES)
                .avoid(AvoidType.HIGHWAYS)
                .transportMode(mode)
                .execute(object : DirectionCallback {
                    override fun onDirectionSuccess(direction: Direction?) {
                        directionsuccess(direction)
                    }

                    override fun onDirectionFailure(t: Throwable) {}
                })
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("TAG", "drawline:exce " + e.message)
        }
    }

    private fun directionsuccess(direction: Direction?) {
        try {
            if (direction!!.isOK) {
                val route = direction.routeList[0]
                if (route != null && !route.legList.isEmpty()) {
                    val distance = route.legList[0].distance
                    val duration = route.legList[0].duration
                    val directionPositionList = route.legList[0].directionPoint
                    if (!directionPositionList.isEmpty()) {
                        if (polyline != null) {
                            polyline!!.remove()
                        }
                        polyline = mMap!!.addPolyline(
                            DirectionConverter.createPolyline(
                                this,
                                directionPositionList,
                                4,
                                ContextCompat.getColor(this, R.color.purple_200)
                            )
                        )
                        setCameraWithCoordinationBounds(route)
                    } else {
                        Toast.makeText(this, "noroute_available", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "noroute_available", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "noroute_available", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setCameraWithCoordinationBounds(route: Route) {
        val southwest = route.bound.southwestCoordination.coordination
        val northeast = route.bound.northeastCoordination.coordination
        val bounds = LatLngBounds(southwest, northeast)
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
    }
}

