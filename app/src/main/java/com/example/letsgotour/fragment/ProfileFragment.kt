package com.example.letsgotour.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.letsgotour.R
import com.example.letsgotour.databinding.FragmentProfileBinding
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {

    }
    fun displayNativeAd(parent: ViewGroup, ad : NativeAd) {

        // Inflate a layout and add it to the parent ViewGroup.
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                as LayoutInflater
        val adView = inflater.inflate(R.layout.nativeads_item, parent) as NativeAdView

        // Locate the view that will hold the headline, set its text, and use the
        // NativeAdView's headlineView property to register it.
        val headlineView = adView.findViewById<TextView>(R.id.ad_headline)
        headlineView.text = ad.headline
        adView.headlineView = headlineView


        // Repeat the above process for the other assets in the NativeAd using
        // additional view objects (Buttons, ImageViews, etc).

        val mediaView = adView.findViewById<MediaView>(R.id.ad_media)
        adView.mediaView = mediaView

        // Call the NativeAdView's setNativeAd method to register the
        // NativeAdObject.
        adView.setNativeAd(ad)

        // Ensure that the parent view doesn't already contain an ad view.
        parent.removeAllViews()

        // Place the AdView into the parent.
        parent.addView(adView)
    }
}