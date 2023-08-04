package com.example.letsgotour.verification

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.letsgotour.Ads
import com.example.letsgotour.MainActivity
import com.example.letsgotour.databinding.ActivitySingInBinding
import com.example.letsgotour.modal.UserSignUp
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.rewarded.ServerSideVerificationOptions
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*
import java.util.*


class SingUp : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {
    lateinit var binding: ActivitySingInBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbFb: DatabaseReference


    private var googleApiClient: GoogleApiClient? = null

    lateinit var callbackManager: CallbackManager
    private var rewardedAd: RewardedAd? = null
    private final var TAG = "MainActivity"
//    lateinit var myEdit : SharedPreferences.Editer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        callbackManager = CallbackManager.Factory.create()
        FirebaseApp.initializeApp(this)
        mAuth = FirebaseAuth.getInstance()
        mDbFb = FirebaseDatabase.getInstance().reference
        GoogleAuth()
        FacebookAuth()
        clickEvent()

        var RewardedAdRequest=Ads.getRewardedAd()

        RewardedAd.load(
            this,
            "ca-app-pub-3940256099942544/5224354917",
            RewardedAdRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError.toString())
                    rewardedAd = null
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    Log.d("TAG__________", "Ad was loaded.")
                    rewardedAd = ad

                }
            })
    }

    private fun FacebookAuth() {
    }


    private fun clickEvent() {
        binding.btnSignIn.setOnClickListener {
            val name = binding.edtName.text.toString()
            var email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                binding.edtEmail.error = "Enter Email"
                binding.edtPassword.error = "Enter Password"
                binding.edtName.error = "Enter UserName"
            } else {
                signin(name, email, password)
            }
        }
        binding.btnLogin.setOnClickListener {
            var intent = Intent(this, LogIn::class.java)
            startActivity(intent)
        }
        binding.btnMobileSignIn.setOnClickListener {
            if (rewardedAd != null) {
                rewardedAd!!.show(this@SingUp, OnUserEarnedRewardListener {
                    Log.d(TAG, "clickEvent______: " + it.amount)
                    Log.d(TAG, "clickEvent______: " + it.type)
                    var alertDialog = AlertDialog.Builder(applicationContext).create()
                    alertDialog.setTitle("Congrats")
                    alertDialog.setMessage("Nice Job")
                    alertDialog.setButton(
                        AlertDialog.BUTTON_NEUTRAL,
                        "ok",
                        DialogInterface.OnClickListener { dialog, which ->
                            alertDialog.dismiss()
                        })
                    alertDialog.show()

                })
            } else {
                var intent = Intent(this@SingUp, MobileNoVerification::class.java)
                startActivity(intent)
                finish()
            }
            rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdClicked() {
                    // Called when a click is recorded for an ad.
                    Log.d(TAG, "Ad was clicked.")
                }

                override fun onAdDismissedFullScreenContent() {
                    // Called when ad is dismissed.
                    // Set the ad reference to null so you don't show the ad a second time.
                    Log.d(TAG, "Ad dismissed fullscreen content.")
                    rewardedAd = null
                    var intent = Intent(this@SingUp, MobileNoVerification::class.java)
                    startActivity(intent)
                    finish()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    // Called when ad fails to show.
                    Log.e(TAG, "Ad failed to show fullscreen content.")
                    rewardedAd = null
                }

                override fun onAdImpression() {
                    // Called when an impression is recorded for an ad.
                    Log.d(TAG, "Ad recorded an impression.")
                }

                override fun onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                    Log.d(TAG, "Ad showed fullscreen content.")
                }
            }
        }

//        binding.loginButton.setReadPermissions(listOf("email","public_profile"))
    }


    private fun signin(name: String, email: String, password: String) {
        if (isNetworkConnected()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Successfully Singed Up", Toast.LENGTH_SHORT).show()
                        var intent = Intent(this, LogIn::class.java)
                        startActivity(intent)
                        finish()
                        uploadData(name, email)

                    } else {
                        Log.e("TAG", "signUpUser: $it")
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        applicationContext, exception.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
        } else {
            Toast.makeText(this, "Please connect Internet", Toast.LENGTH_SHORT).show()
        }

    }

    private fun uploadData(name: String, email: String) {
        var uid = UUID.randomUUID()
        mDbFb.child("user").child(uid.toString()).setValue(UserSignUp(name, email, uid))
    }


    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    private fun GoogleAuth() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("762246197376-gmu2j6r38vn4ci4lsmp1jl2ebl3ogc9s.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
        binding.btnGoogleSignIn.setOnClickListener {
            val intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient!!)
            startActivityForResult(intent, 100)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            val signInAccountTask: Task<GoogleSignInAccount> = GoogleSignIn
                .getSignedInAccountFromIntent(data)
            if (signInAccountTask.isSuccessful) {
                try {
                    val googleSignInAccount = signInAccountTask
                        .getResult(ApiException::class.java)
                    if (googleSignInAccount != null) {

                        val authCredential = GoogleAuthProvider
                            .getCredential(
                                googleSignInAccount.idToken, null
                            )
                        mAuth.signInWithCredential(authCredential)
                            .addOnCompleteListener(this,
                                OnCompleteListener<AuthResult?> { task ->
                                    if (task.isSuccessful) {

                                        startActivity(
                                            Intent(this, MainActivity::class.java)
                                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        )
                                        Toast.makeText(
                                            this,
                                            "Google Sign Up Success",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    } else {

                                        Toast.makeText(
                                            this,
                                            "Google Sign Up Fail",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                })
                    }
                } catch (e: ApiException) {
                    e.printStackTrace()
                }

            }
        }

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    Log.e("TAG", "onSuccess----:$result ")
                    val accessToken = AccessToken.getCurrentAccessToken()
                    val isLoggedIn = accessToken != null && !accessToken.isExpired
                    Log.e("TAG", "onSuccess=-=-:$isLoggedIn ")
//                    loginStatus()
                    Toast.makeText(this@SingUp, "Facebook login are success", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onCancel() {
                    Toast.makeText(this@SingUp, "Facebook login are canceled", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onError(error: FacebookException) {
                    Toast.makeText(this@SingUp, error.localizedMessage, Toast.LENGTH_SHORT).show()
                    Log.d("TAG", "onError----:${error.localizedMessage} ")
                }
            })


    }

    private fun loginStatus() {
        LoginManager.getInstance()
            .logInWithReadPermissions(this, listOf("email", "public_profile"));
        LoginManager.getInstance().retrieveLoginStatus(this, object : LoginStatusCallback {
            override fun onCompleted(accessToken: AccessToken) {
                Toast.makeText(this@SingUp, "success.....", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure() {
                Toast.makeText(this@SingUp, "fail....", Toast.LENGTH_SHORT).show()
            }

            override fun onError(exception: Exception) {
                Toast.makeText(this@SingUp, "error....", Toast.LENGTH_SHORT).show()
            }
        })

    }


}