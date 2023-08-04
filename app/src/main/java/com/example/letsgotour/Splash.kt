package com.example.letsgotour

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.example.letsgotour.adapter.ImageSliderAdapter
import com.example.letsgotour.databinding.ActivitySplashBinding
import com.example.letsgotour.verification.LogIn
import com.google.android.gms.ads.AdRequest

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class Splash : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var mAuth: FirebaseAuth
    var firebaseUser: FirebaseUser? = null
    var imageList: ArrayList<Int> = ArrayList()
    var titleList: ArrayList<String> = ArrayList()
    var desList: ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        firebaseUser = mAuth.currentUser

        var bannerAd = AdRequest.Builder().build()
        if (bannerAd != null) {
            Ads.BannerAds(bannerAd)
        }
        var InterstitialAd = AdRequest.Builder().build()
        if (InterstitialAd != null) {
            Ads.InterstitialAds(InterstitialAd)
        }
        var RewardedAd = AdRequest.Builder().build()
        if (RewardedAd != null) {
            Ads.RewardedAd(RewardedAd)
        }
        initView()
    }


    private fun initView() {
        imageList.add(R.drawable.photograf)
        imageList.add(R.drawable.destinationman)
        imageList.add(R.drawable.tourbag)

        titleList.add("Explore Destinations")
        titleList.add("Choose a Destinations")
        titleList.add("Fly to Destinations")

        desList.add(
            "Discover the places your trip in the \n" +
                    "world and feel great"
        )
        desList.add(
            "Select a place for your tip easily and know \n" +
                    "the exact cost of the tour"
        )
        desList.add(
            "Finally, get ready for the tour and go to\n" +
                    "your desine destination"
        )

        val viewPagesAdapter = ImageSliderAdapter(this, imageList, titleList, desList, Click = {

        })
        binding.ImgSlider.adapter = viewPagesAdapter
        binding.circleIndicator.setViewPager(binding.ImgSlider)

        viewPagesAdapter.registerDataSetObserver(binding.circleIndicator.dataSetObserver)


        binding.txtSkip.setOnClickListener {
            if (firebaseUser == null) {
                val intent = Intent(this@Splash, LogIn::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this@Splash, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
//
        binding.imgNext.setOnClickListener {
            if (firebaseUser == null) {
                val intent = Intent(this@Splash, LogIn::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this@Splash, SplashSecond::class.java)
                startActivity(intent)
                finish()
            }

//            SharedPreferences.setInterstitialAds(this,adRequest1)


        }

    }


}