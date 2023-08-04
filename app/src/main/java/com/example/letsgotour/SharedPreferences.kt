package com.example.letsgotour

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.android.gms.ads.AdRequest

object SharedPreferences {


    const val InterstitialAds="InterstitialAds"

    fun setInterstitialAds(context: Context, date : AdRequest) {
        val sharedPreferences = context.getSharedPreferences("", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
//        editor.putStringSet(InterstitialAds, date)
        editor.apply()
    }

    fun getInterstitialAds(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("1", MODE_PRIVATE)
        val data=sharedPreferences.getString(InterstitialAds,"")
        return data
    }

}