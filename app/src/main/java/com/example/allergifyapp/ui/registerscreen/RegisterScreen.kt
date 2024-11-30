package com.example.allergifyapp.ui.registerscreen

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.example.allergifyapp.databinding.ActivityRegisterScreenBinding
import com.example.allergifyapp.ui.main.BaseActivity

class RegisterScreen : BaseActivity() {
    private lateinit var binding: ActivityRegisterScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(enabled = true) {
            override fun handleOnBackPressed() {
            }
        })

        setupButton()
    }

    private fun setupButton() {
        binding.registerWithEmailButton.setOnClickListener {
            val intent = Intent(this, RegisterEmailScreen::class.java)
            startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
        }

    }
}