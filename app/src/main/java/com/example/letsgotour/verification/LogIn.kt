package com.example.letsgotour.verification

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.letsgotour.Ads
import com.example.letsgotour.MainActivity
import com.example.letsgotour.databinding.ActivityLogInBinding
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.firebase.auth.FirebaseAuth


class LogIn : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    lateinit var binding: ActivityLogInBinding
    private var mInterstitialAd: InterstitialAd? = null
    var TAG______ = ""
    lateinit var adLoader : AdLoader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MobileAds.initialize(this) {}
        mAuth = FirebaseAuth.getInstance()

        var BannerAd = Ads.getBannerAds()
        binding.adView.loadAd(BannerAd)
        var InterstitialAd = Ads.getInterstitialAds()
        if (InterstitialAd!=null) {
            adMob(InterstitialAd)
        }

        var getEmail = intent.getStringExtra("Email")
        if (getEmail != null) {
            val editable = Editable.Factory.getInstance().newEditable(getEmail)
            binding.edtEmail.text = editable
        }
        clickEvent()

    }

    private fun adMob(ad: AdRequest) {

        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", ad,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    // The mInterstitialAd reference will be null until
                    // an ad is loaded.
                    mInterstitialAd = interstitialAd
                    Log.i(TAG______, "onAdLoaded")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error
                    Log.d(TAG______, loadAdError.toString())
                    mInterstitialAd = null
                }
            })


    }

    private fun clickEvent() {
        binding.btnLogin.setOnClickListener {
            login()
        }
        binding.btnSignIn.setOnClickListener {
            var intent = Intent(this, SingUp::class.java)
            startActivity(intent)
            finish()
        }
        binding.ForgetPassword.setOnClickListener {
            var intent = Intent(this, ForgetPassword::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun login() {
        val email = binding.edtEmail.text.toString()
        val pass = binding.edtPassword.text.toString()
        if (email.isEmpty() || pass.isEmpty()) {
            binding.edtEmail.error = "Enter Email"
            binding.edtPassword.error = "Enter password"
        } else {
            if (isNetworkConnected()) {
                Toast.makeText(this, "Please wait check your data", Toast.LENGTH_SHORT).show()
                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
                    if (it.isSuccessful) {

                        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
                        val myEdit = sharedPreferences.edit()
                        myEdit.putString("user", binding.edtEmail.text.toString())
                        myEdit.apply()

                        if (mInterstitialAd != null) {
                            mInterstitialAd!!.show(this)
                            mInterstitialAd?.fullScreenContentCallback =
                                object : FullScreenContentCallback() {
                                    override fun onAdClicked() {
                                        // Called when a click is recorded for an ad.
                                        Log.d(TAG______, "______Ad was clicked.")
                                    }

                                    override fun onAdDismissedFullScreenContent() {
                                        // Called when ad is dismissed.
                                        Log.d(TAG______, "______Ad dismissed fullscreen content.")
                                        mInterstitialAd = null
                                        val intent = Intent(this@LogIn, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }

                                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                                        // Called when ad fails to show.
                                        Log.d(
                                            TAG______,
                                            "______Ad failed to show fullscreen content."
                                        )
                                        mInterstitialAd = null
                                    }

                                    override fun onAdImpression() {
                                        // Called when an impression is recorded for an ad.
                                        Log.d(TAG______, "______Ad recorded an impression.")
                                    }

                                    override fun onAdShowedFullScreenContent() {
                                        // Called when ad is shown.
                                        Log.d(TAG______, "______Ad showed fullscreen content.")
                                    }
                                }
                        } else {
                            val intent = Intent(this@LogIn, MainActivity::class.java)
                            startActivity(intent)
                            Log.d("TAG", "The interstitial ad wasn't ready yet.");
                        }

                        finish()
                    }
                }.addOnFailureListener {
                    Toast.makeText(
                        applicationContext, it.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(this, "Please connect Internet", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }
}