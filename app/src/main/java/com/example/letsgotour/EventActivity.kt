package com.example.letsgotour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.letsgotour.databinding.ActivityEventBinding

class EventActivity : AppCompatActivity() {
    lateinit var binding: ActivityEventBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}