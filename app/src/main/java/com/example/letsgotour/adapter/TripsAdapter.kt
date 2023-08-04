package com.example.letsgotour.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.letsgotour.DrawLineMap
import com.example.letsgotour.MapsPlaceDrawActivity
import com.example.letsgotour.R

import com.example.letsgotour.modal.ItemModal

class TripsAdapter(var context: Context, var ItemList: ArrayList<ItemModal>) :
    RecyclerView.Adapter<TripsAdapter.MyViewHolder>() {
    class MyViewHolder(initView: View) : RecyclerView.ViewHolder(initView) {
        var tripPlace = initView.findViewById<TextView>(R.id.TripPlace)
        var goPlaceMap = initView.findViewById<LinearLayout>(R.id.GoPlaceMap)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var v =
            LayoutInflater.from(parent.context).inflate(R.layout.trip_item, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return ItemList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tripPlace.text = ItemList[position].name
        holder.goPlaceMap.setOnClickListener {
            var intent = Intent(context, MapsPlaceDrawActivity::class.java)
            intent.putExtra("TourPackage",ItemList[position].uid)
            context.startActivity(intent)
        }
    }
}