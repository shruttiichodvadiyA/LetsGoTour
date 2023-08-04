package com.example.letsgotour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.letsgotour.databinding.ActivityTransportBinding


class TransportActivity : AppCompatActivity() {
    lateinit var binding: ActivityTransportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransportBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_transport)
        binding.imgBack.setOnClickListener {
            finish()
        }
    }
}