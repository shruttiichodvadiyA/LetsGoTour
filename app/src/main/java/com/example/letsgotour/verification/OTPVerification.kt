package com.example.letsgotour.verification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.letsgotour.MainActivity
import com.example.letsgotour.R
import com.example.letsgotour.databinding.ActivityOtpverificationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OTPVerification : AppCompatActivity() {
    lateinit var binding: ActivityOtpverificationBinding

    var OTP: String? = null
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpverificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        OTP = intent.getStringExtra("OTP")
        mAuth = FirebaseAuth.getInstance()
        clickEvent()
    }

    private fun clickEvent() {
        binding.btnVerifiedOTP.setOnClickListener {
            var otp=binding.firstPinView.text
            Log.d("TAG", "clickEvent--: $otp")
            if (otp?.length != 6) {
                binding.firstPinView.error = "Please Enter OTP"
            } else {
                val credential = PhoneAuthProvider.getCredential(this.OTP!!, otp.toString())
                signInWithCredential(credential)
            }
        }
    }
    private fun signInWithCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG___", "signInWithCredential:success")
                    Toast.makeText(this, "OTP verified", Toast.LENGTH_SHORT).show()
                    var intent= Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Log.d("TAG___", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(
                            this,
                            " The verification code entered was invalid",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            }
    }
}