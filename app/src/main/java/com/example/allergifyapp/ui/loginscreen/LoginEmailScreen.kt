package com.example.allergifyapp.ui.loginscreen

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.allergifyapp.R
import com.example.allergifyapp.databinding.ActivityLoginEmailScreenBinding
import com.example.allergifyapp.ui.main.BaseActivity
import com.example.allergifyapp.ui.main.MainActivity
import com.example.allergifyapp.utils.DataStatus
import com.example.allergifyapp.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginEmailScreen : BaseActivity() {
    private lateinit var binding: ActivityLoginEmailScreenBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginEmailScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeLoginResult()
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
        binding.loginButton.setOnClickListener {
            val email = binding.emailLoginEditText.text.toString()
            val password = binding.passwordLoginEditText.text.toString()

            validateEmail(email)
            validatePassword(password)

            if (binding.emailLoginTextField.error == null && binding.passwordLoginTextField.error == null) {
                authViewModel.login(email, password)
            }
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun observeLoginResult() {
        authViewModel.loginStatus.observe(this) {
            when (it.status) {
                DataStatus.Status.LOADING -> {
                    binding.loginProgressIndicator.isVisible = true
                }
                DataStatus.Status.SUCCESS -> {
                    binding.loginProgressIndicator.isVisible = false
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }

                DataStatus.Status.ERROR -> {
                    binding.loginProgressIndicator.isVisible = false
                    showToast("Login Gagal: ${it.message}")
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}