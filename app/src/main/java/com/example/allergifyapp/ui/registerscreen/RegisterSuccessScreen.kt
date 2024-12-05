package com.example.allergifyapp.ui.registerscreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.example.allergifyapp.databinding.ActivityRegisterSuccessScreenBinding
import com.example.allergifyapp.ui.loginscreen.LoginScreen
import com.example.allergifyapp.ui.main.BaseActivity

class RegisterSuccessScreen : BaseActivity() {
    private lateinit var binding: ActivityRegisterSuccessScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterSuccessScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.setOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
            finish()
        }

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(enabled = true) {
            override fun handleOnBackPressed() {
            }
        })

    }
}