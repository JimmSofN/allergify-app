package com.example.allergifyapp.ui.registerscreen

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.allergifyapp.R
import com.example.allergifyapp.databinding.ActivityRegisterEmailScreenBinding
import com.example.allergifyapp.ui.main.BaseActivity
import com.example.allergifyapp.ui.main.MainActivity

class RegisterEmailScreen : BaseActivity() {
    private lateinit var binding: ActivityRegisterEmailScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterEmailScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButton()
        setupTextWatchers()
    }

    private fun setupTextWatchers() {
        binding.emailRegisterEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateEmail(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.passwordRegisterEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePassword(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun validateEmail(email: String) {
        if (email.isEmpty()) {
            binding.emailRegisterTextField.error = getString(R.string.activity_register_screen_error_empty_email)
        } else if (!isValidEmail(email)) {
            binding.emailRegisterTextField.error = getString(R.string.activity_register_screen_invalid_email)
        } else {
            binding.emailRegisterTextField.error = null
        }
    }

    private fun validatePassword(password: String) {
        if (password.isEmpty()) {
            binding.passwordRegisterTextField.error = getString(R.string.activity_register_screen_error_empty_password)
        } else if (password.length < 8) {
            binding.passwordRegisterTextField.error = getString(R.string.activity_register_screen_error_password_min_length)
        } else if (!password.any { it.isLowerCase() }) {
            binding.passwordRegisterTextField.error = getString(R.string.activity_register_screen_error_password_lowercase)
        } else if (!password.any { it.isUpperCase() }) {
            binding.passwordRegisterTextField.error = getString(R.string.activity_register_screen_error_password_uppercase)
        } else if (!password.any { it.isDigit() }) {
            binding.passwordRegisterTextField.error = getString(R.string.activity_register_screen_error_password_digit)
        } else {
            binding.passwordRegisterTextField.error = null
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    private fun setupButton() {
        binding.registerButton.setOnClickListener {
            val email = binding.emailRegisterEditText.text.toString()
            val password = binding.passwordRegisterEditText.text.toString()

            validateEmail(email)
            validatePassword(password)

            if (binding.emailRegisterTextField.error == null && binding.passwordRegisterTextField.error == null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
            }
        }

        binding.backButton.setOnClickListener {
            val intent = Intent(this, RegisterScreen::class.java)
            startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
        }
    }
}