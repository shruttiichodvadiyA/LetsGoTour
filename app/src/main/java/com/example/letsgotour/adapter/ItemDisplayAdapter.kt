package com.example.letsgotour.adapter

import android.content.Context
import android.content.Intent
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.letsgotour.PackageDetailsActivity
import com.example.letsgotour.R
import com.example.letsgotour.modal.ItemModal
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import soup.neumorphism.NeumorphCardView
import kotlin.math.log


class ItemDisplayAdapter(var context: Context, var ItemList: ArrayList<ItemModal>) :
    RecyclerView.Adapter<ItemDisplayAdapter.MyViewHolder>() {
    class MyViewHolder(initView: View) : RecyclerView.ViewHolder(initView) {
//        var name = initView.findViewById<TextView>(R.id.txtTour)
//        var price = initView.findViewById<TextView>(R.id.txtPrice)
//        var start = initView.findViewById<TextView>(R.id.txtStart)
//        var end = initView.findViewById<TextView>(R.id.txtEnd)
//        var imgTourPost = initView.findViewById<ImageView>(R.id.imgTourPost)
//        var person = initView.findViewById<TextView>(R.id.txtPerson)
//        var des = initView.findViewById<TextView>(R.id.txtDescription)
//        var item = initView.findViewById<NeumorphCardView>(R.id.Item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var v = LayoutInflater.from(parent.context).inflate(R.layout.disitem_item, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.name.text = ItemList[position].name
//        holder.price.text = ItemList[position].price
//        holder.start.text = ItemList[position].startDes
//        holder.end.text = ItemList[position].endDes

//        var storageReference : StorageReference = FirebaseStorage.getInstance().reference.child("Profile/${ItemList[position].profile}")
//        storageReference.downloadUrl.addOnSuccessListener(OnSuccessListener { uri ->
//            Glide.with(context)
//                .load(uri)
//                .into(holder.imgTourPost)
//        })
//
//        holder.item.setOnClickListener {
//            Log.e("TAG", "onBindViewHolder---: ${ItemList[position].uid}", )
//            var intent = Intent(context, PackageDetailsActivity::class.java)
//            intent.putExtra("TourPackage",ItemList[position].uid)
//            context.startActivity(intent)
//        }

    }

    override fun getItemCount(): Int {
        return ItemList.size
    }
}