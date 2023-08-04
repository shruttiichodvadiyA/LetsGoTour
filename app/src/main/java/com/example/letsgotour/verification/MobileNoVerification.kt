package com.example.letsgotour.verification

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.letsgotour.R
import com.example.letsgotour.databinding.ActivityMobileVerificationBinding
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class MobileNoVerification : AppCompatActivity() {
    private lateinit var mAuth : FirebaseAuth
    lateinit var verificationCode: String
    var mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    lateinit var binding: ActivityMobileVerificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMobileVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth= FirebaseAuth.getInstance()
        ads()
        clickEvent()
        initFirebaseCredentiale()
    }

    private fun ads() {

    }

    private fun initFirebaseCredentiale() {
        mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                Toast.makeText(this@MobileNoVerification, "verification completed", Toast.LENGTH_SHORT).show()
                val code = phoneAuthCredential.smsCode
                if (code != null) {
//                    tv_otp.text = code
                    getOTP(code)
                } else {
//                    signInWithCredential(phoneAuthCredential)
                }
            }
            override fun onVerificationFailed(e: FirebaseException) {

                Log.d("TAG___", "onVerificationFailed: ${e.message}")
                Toast.makeText(this@MobileNoVerification, e.localizedMessage, Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(s: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)
                Log.d("TAG___", "onCodeSentSSS: $s")
                verificationCode = s
                Log.d("TAG___", "onCodeSent: $forceResendingToken")
                Toast.makeText(this@MobileNoVerification, "OTP sent successful", Toast.LENGTH_SHORT).show()

                var intent= Intent(this@MobileNoVerification,OTPVerification::class.java)
                intent.putExtra("OTP",s)

                startActivity(intent)
                finish()
            }
        }

    }

    private fun clickEvent() {
        binding.btnMobileVerified.setOnClickListener {
            if(binding.edtMobileNumber.text.toString().trim().isEmpty() ){
                binding.edtMobileNumber.error = "Please enter number"
            }else if (binding.edtMobileNumber.text.toString().trim().length==10){
                var phoneNumber = "+91"+binding.edtMobileNumber.text.toString()
                getOTP(phoneNumber)
            }else{
                binding.edtMobileNumber.error = "Please enter valid number"
            }
        }
        binding.imgBack.setOnClickListener {
            var intent = Intent(this,SingUp::class.java)
            startActivity(intent)
            finish()
        }

    }
    private fun getOTP(phoneNumber : String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            this,
            mCallback!!);
        Log.d("TAG___", "addMobileNumber:$phoneNumber")
    }
}