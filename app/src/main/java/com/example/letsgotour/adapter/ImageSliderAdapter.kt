package com.example.letsgotour.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.letsgotour.R

class ImageSliderAdapter(var context: Context, var imagelist: ArrayList<Int>,var titleList: ArrayList<String>,var desList: ArrayList<String>,var Click: (Int) -> Unit) : PagerAdapter() {
    override fun getCount(): Int {
        return imagelist.size or titleList.size or desList.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var v = LayoutInflater.from(context).inflate(R.layout.pager_item, container, false)
        var imgslider=v.findViewById<ImageView>(R.id.imgslider)
        var SetText=v.findViewById<TextView>(R.id.SetText)
        var Setdes=v.findViewById<TextView>(R.id.Setdes)

        imgslider.setImageResource(imagelist[position])
        SetText.text = titleList[position]
        Setdes.text = desList[position]
        Log.d("TAG", "instantiateItem-:${titleList[position]} ")
        Click.invoke(position)
        container.addView(v)
        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj : Any) {
        container.removeView(obj as View?)
    }
}
