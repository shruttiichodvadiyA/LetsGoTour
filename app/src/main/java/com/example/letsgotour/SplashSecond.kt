package com.example.letsgotour

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.letsgotour.databinding.ActivitySplashSecondBinding
import com.example.letsgotour.verification.LogIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SplashSecond : AppCompatActivity() {
    lateinit var binding : ActivitySplashSecondBinding
    private lateinit var mAuth: FirebaseAuth
    var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashSecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        firebaseUser = mAuth.currentUser

        binding.btnExplore.setOnClickListener {
            if (firebaseUser == null) {
                val intent = Intent(this, LogIn::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}