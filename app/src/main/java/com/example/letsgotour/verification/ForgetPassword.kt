package com.example.letsgotour.verification

import android.content.ContentValues.TAG
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.letsgotour.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import soup.neumorphism.NeumorphButton


class ForgetPassword : AppCompatActivity() {
    lateinit var forgetKey: NeumorphButton
    lateinit var edtMobileNumber: EditText
    lateinit var ResendMassage: TextView
    lateinit var imgForgetToBack: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        forgetKey = findViewById(R.id.btnEmailForget)
        ResendMassage = findViewById(R.id.ResendMassage)
        edtMobileNumber = findViewById(R.id.edtMobileNumber)
        imgForgetToBack = findViewById(R.id.imgForgetToBack)
        forgetKey.setOnClickListener {
           initView()
        }
        ResendMassage.setOnClickListener {
            initView()
        }
        imgForgetToBack.setOnClickListener {
            finish()
        }
    }

    private fun initView() {
        val auth = FirebaseAuth.getInstance()
        var emailAddress = edtMobileNumber.text.toString()
        if (emailAddress.isEmpty()) {
            edtMobileNumber.error = "Enter Email Address"
        } else {
            Toast.makeText(this, "Please wait a massage in your emailAddress", Toast.LENGTH_LONG).show()
            auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Email sent.")
                    }
                }

        }
    }
}