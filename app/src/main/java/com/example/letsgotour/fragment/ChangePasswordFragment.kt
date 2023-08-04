package com.example.letsgotour.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import com.example.letsgotour.R
import com.example.letsgotour.databinding.FragmentChangePasswordBinding
import com.example.letsgotour.databinding.FragmentDisplayItemBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth


class ChangePasswordFragment : Fragment() {
    lateinit var binding: FragmentChangePasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false);
        val view = binding.root
        initView()
        return view
    }

    private fun initView() {
        binding.btnChangePass.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            val email = binding.edtChangePsEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val rePassword = binding.edtRePassword.text.toString()
            if (email.isEmpty()) {
                binding.edtChangePsEmail.error = " Enter email"
            } else if (password != rePassword) {
                Toast.makeText(context, "password is not same", Toast.LENGTH_SHORT).show()
            } else {
                val credential = EmailAuthProvider
                    .getCredential(email, password)

                user?.reauthenticate(credential)?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(context, "password change successfully", Toast.LENGTH_SHORT)
                            .show()
                    } else {

                    }
                }

            }
        }

    }
}