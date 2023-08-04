package com.example.letsgotour

import com.google.android.gms.ads.AdRequest

object Ads {

    lateinit var BannerAds: AdRequest
    lateinit var Interstitial: AdRequest
    lateinit var Native: AdRequest
    lateinit var RewardedAd: AdRequest
    fun BannerAds(BannerAds: AdRequest) {
        this.BannerAds = BannerAds
    }

    @JvmName("getBannerAds1")
    fun getBannerAds(): AdRequest {
        return BannerAds
    }

    fun InterstitialAds(InterstitialAd: AdRequest) {
        this.Interstitial = InterstitialAd
    }

    @JvmName("getInterstitial1")
    fun getInterstitialAds(): AdRequest {
        return Interstitial
    }

    fun NativeAds() {

    }

    fun getNativeAds(): AdRequest {
        return Native
    }

    fun RewardedAd(RewardedAd: AdRequest) {
        this.RewardedAd = RewardedAd
    }

    @JvmName("getRewardedAd1")
    fun getRewardedAd(): AdRequest {
        return RewardedAd
    }
}