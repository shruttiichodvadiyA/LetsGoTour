package com.example.letsgotour

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letsgotour.adapter.TripsAdapter
import com.example.letsgotour.databinding.ActivityTripsBinding
import com.example.letsgotour.modal.ItemModal
import com.google.firebase.database.*

class TripsActivity : AppCompatActivity() {
    lateinit var binding: ActivityTripsBinding
    lateinit var mDbRef: DatabaseReference
    lateinit var tripsAdapter : TripsAdapter
    var ItemList: ArrayList<ItemModal> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityTripsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mDbRef = FirebaseDatabase.getInstance().reference
        initView()
        binding.imgBack.setOnClickListener {
            finish()
        }

    }

    private fun initView() {
        tripsAdapter = TripsAdapter(this, ItemList)
        val manger = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rcvMapItem.layoutManager = manger
        binding.rcvMapItem.adapter = tripsAdapter
        mDbRef.child("Item").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                ItemList.clear()
                for (postSnapshot in snapshot.children) {
                    val Item = postSnapshot.getValue(ItemModal::class.java)
                    if (Item != null) {
                        binding.PgLoadMapItem.visibility= View.GONE
                    }
                    ItemList.add(Item!!)


                }
                tripsAdapter.notifyDataSetChanged()

            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

    }
}