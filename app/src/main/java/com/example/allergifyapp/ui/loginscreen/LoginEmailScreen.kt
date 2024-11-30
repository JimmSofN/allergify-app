package com.example.allergifyapp.ui.loginscreen

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.allergifyapp.R
import com.example.allergifyapp.databinding.ActivityLoginEmailScreenBinding
import com.example.allergifyapp.ui.forgotpasswordscreen.ForgotPasswordEmailScreen
import com.example.allergifyapp.ui.main.BaseActivity
import com.example.allergifyapp.ui.main.MainActivity

class LoginEmailScreen : BaseActivity() {
    private lateinit var binding: ActivityLoginEmailScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginEmailScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButton()
        setupTextWatchers()
    }

    private fun setupTextWatchers() {
        binding.emailLoginEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateEmail(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.passwordLoginEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePassword(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun validateEmail(email: String) {
        if (email.isEmpty()) {
            binding.emailLoginTextField.error = getString(R.string.activity_login_screen_error_empty_email)
        } else if (!isValidEmail(email)) {
            binding.emailLoginTextField.error = getString(R.string.activity_login_screen_invalid_email)
        } else {
            binding.emailLoginTextField.error = null
        }
    }

    private fun validatePassword(password: String) {
        if (password.isEmpty()) {
            binding.passwordLoginTextField.error = getString(R.string.activity_login_screen_error_empty_password)
        } else if (password.length < 8) {
            binding.passwordLoginTextField.error = getString(R.string.activity_login_screen_error_password_min_length)
        } else {
            binding.passwordLoginTextField.error = null
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    private fun setupButton() {
        binding.forgotPasswordButton.setOnClickListener {
            val intent = Intent(this, ForgotPasswordEmailScreen::class.java)
            startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
        }

        binding.backButton.setOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailLoginEditText.text.toString()
            val password = binding.passwordLoginEditText.text.toString()


            if (binding.emailLoginTextField.error == null && binding.passwordLoginTextField.error == null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
            }

            validateEmail(email)
            validatePassword(password)

        }
    }
}