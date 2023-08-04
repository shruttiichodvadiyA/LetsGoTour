package com.example.letsgotour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.letsgotour.databinding.ActivityPackageDetailsBinding
import com.example.letsgotour.modal.ItemModal
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class PackageDetailsActivity : AppCompatActivity() {
    lateinit var mDbRef: DatabaseReference
    lateinit var binding: ActivityPackageDetailsBinding
    lateinit var mMap: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPackageDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mDbRef = FirebaseDatabase.getInstance().reference
        initView()
        binding.imgBack.setOnClickListener {
            finish()
        }
    }

    private fun mapload(latitude: Float?, longitude: Float?) {
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
                            .title("Goa")

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

    private fun initView() {
        var uid = intent.getStringExtra("TourPackage")
        mDbRef.child("Item").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val Item = postSnapshot.getValue(ItemModal::class.java)

                    if (Item?.uid == uid) {

                        setData(
                            Item?.name,
                            Item?.price,
                            Item?.day,
                            Item?.startDes,
                            Item?.endDes,
                            Item?.person,
                            Item?.description,
                            Item?.profile
                        )
                        mapload(Item?.latitude,Item?.longitude)
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun setData(
        name: String?,
        price: String?,
        day: String?,
        startDes: String?,
        endDes: String?,
        person: String?,
        description: String?,
        profile: String?
    ) {
        binding.TourTitle.text = name
        binding.txtNameDetails.text = name
        binding.txtDayDetails.text = day
        binding.txtDesDetails.text = description
        binding.txtEndDetails.text = endDes
        binding.txtPriceDetails.text = price
        binding.txtPersonDetails.text = person
        binding.txtStartDetails.text = startDes
        var storageReference: StorageReference =
            FirebaseStorage.getInstance().reference.child("Profile/$profile")
        storageReference.downloadUrl.addOnSuccessListener(OnSuccessListener { uri ->
            Glide.with(this)
                .load(uri)
                .into(binding.imgTourPost)
        })

    }
}