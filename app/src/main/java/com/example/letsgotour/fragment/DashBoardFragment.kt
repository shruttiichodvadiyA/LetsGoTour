package com.example.letsgotour.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letsgotour.EventActivity
import com.example.letsgotour.HotelsActivity
import com.example.letsgotour.TransportActivity
import com.example.letsgotour.TripsActivity
import com.example.letsgotour.adapter.AddDashBoardPackageAdapter
import com.example.letsgotour.databinding.FragmentDashBoardBinding
import com.example.letsgotour.modal.ItemModal
import com.google.firebase.database.*

class DashBoardFragment : Fragment() {
    lateinit var binding: FragmentDashBoardBinding
    lateinit var imgTour: ArrayList<Int>
    lateinit var addDashBoardPackage : AddDashBoardPackageAdapter
    lateinit var mDbRef: DatabaseReference
    var ItemList: ArrayList<ItemModal> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashBoardBinding.inflate(inflater, container, false)
        val view = binding.root
        mDbRef = FirebaseDatabase.getInstance().reference

        clickEvent()
        addRecycler()
        return view
    }

    private fun clickEvent() {
        binding.Trips.setOnClickListener {
            var intent = Intent(context, TripsActivity::class.java)
            startActivity(intent)
        }
        binding.Hotels.setOnClickListener {
            var intent = Intent(context, HotelsActivity::class.java)
            startActivity(intent)
        }
        binding.Transport.setOnClickListener {
            var intent = Intent(context, TransportActivity::class.java)
            startActivity(intent)
        }
        binding.Events.setOnClickListener {
            var intent = Intent(context, EventActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addRecycler() {

        addDashBoardPackage = AddDashBoardPackageAdapter(requireContext(), ItemList)
        val manger = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.RcvTourImg.layoutManager = manger
        binding.RcvTourImg.adapter = addDashBoardPackage
        mDbRef.child("Item").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                ItemList.clear()
                for (postSnapshot in snapshot.children) {
                    val Item = postSnapshot.getValue(ItemModal::class.java)
                    if (Item != null) {
                        binding.progressbarMainItem.visibility=View.GONE
                    }
                    ItemList.add(Item!!)
                    Log.d("TAG", "onDataChange:${Item.name} ")

                }
                addDashBoardPackage.notifyDataSetChanged()

            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

    }



}