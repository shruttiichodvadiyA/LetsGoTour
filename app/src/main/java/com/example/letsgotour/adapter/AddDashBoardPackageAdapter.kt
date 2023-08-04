package com.example.letsgotour.adapter

import android.content.Context
import android.content.Intent
import android.opengl.Visibility
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

class AddDashBoardPackageAdapter(var context: Context, var ItemList: ArrayList<ItemModal>) :
    RecyclerView.Adapter<AddDashBoardPackageAdapter.MyHolderView>() {
    class MyHolderView(initView: View) : RecyclerView.ViewHolder(initView) {
        var imgTourMainItem = initView.findViewById<ImageView>(R.id.ImgTourMainItem)
        var itemMain = initView.findViewById<NeumorphCardView>(R.id.ItemMain)

        var tourNameMainItem = initView.findViewById<TextView>(R.id.TourNameMainItem)
        var tourPriceMainItem = initView.findViewById<TextView>(R.id.TourPriceMainItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolderView {
        var v =
            LayoutInflater.from(parent.context).inflate(R.layout.mainpackage_item, parent, false)
        return MyHolderView(v)
    }

    override fun getItemCount(): Int {
        return ItemList.size
    }

    override fun onBindViewHolder(holder: MyHolderView, position: Int) {
        var storageReference: StorageReference =
            FirebaseStorage.getInstance().reference.child("Profile/${ItemList[position].profile}")
        storageReference.downloadUrl.addOnSuccessListener(OnSuccessListener { uri ->
            Glide.with(context)
                .load(uri)
                .into(holder.imgTourMainItem)
        })
        holder.tourNameMainItem.text = ItemList[position].name
        holder.tourPriceMainItem.text = ItemList[position].price
        holder.itemMain.setOnClickListener {
            var intent = Intent(context, PackageDetailsActivity::class.java)
            intent.putExtra("TourPackage",ItemList[position].uid)
            context.startActivity(intent)
        }
    }
}