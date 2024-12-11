package com.example.allergifyapp.ui.registerscreen

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.allergifyapp.R
import com.example.allergifyapp.data.local.model.UserEmail
import com.example.allergifyapp.data.local.model.UserInfo
import com.example.allergifyapp.data.local.model.UserName
import com.example.allergifyapp.databinding.ActivityRegisterEmailScreenBinding
import com.example.allergifyapp.localdata.PreferencesManager
import com.example.allergifyapp.ui.main.BaseActivity
import com.example.allergifyapp.utils.DataStatus
import com.example.allergifyapp.viewmodel.AuthViewModel
import com.example.allergifyapp.viewmodel.UserInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class RegisterEmailScreen : BaseActivity() {
    private lateinit var binding: ActivityRegisterEmailScreenBinding
    private val authViewModel: AuthViewModel by viewModels()
    private val userInfoViewModel: UserInfoViewModel by viewModels()

    @Inject
    lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterEmailScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeRegisterResult()
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
                authViewModel.register(email, password)
            }
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun observeRegisterResult() {
        authViewModel.registrationStatus.observe(this) {
            when (it.status) {
                DataStatus.Status.LOADING -> {
                    binding.registerProgressIndicator.isVisible = true
                }
                DataStatus.Status.SUCCESS -> {
                    binding.registerProgressIndicator.isVisible = false

                    val userName = preferencesManager.getUserName()
                    val userAge = preferencesManager.getUserAge()
                    val userHeight = preferencesManager.getUserHeight()
                    val userWeight = preferencesManager.getUserWeight()

                    val userEmail = binding.emailRegisterEditText.text.toString()

                    userAge?.let {
                        val userInfo = UserInfo(age = userAge?: "", height = userHeight?: "", weight = userWeight?: "")
                        userInfoViewModel.insertUserInfo(userInfo)
                    }

                    userName?.let {
                        val userName = UserName(userName = userName)
                        userInfoViewModel.insertUserName(userName)
                    }

                    val userEmailEntity = UserEmail(userEmail = userEmail)
                    userInfoViewModel.insertUserEmail(userEmailEntity)

                    val intent = Intent(this, RegisterSuccessScreen::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                DataStatus.Status.ERROR -> {
                    binding.registerProgressIndicator.isVisible = false
                    showToast(it.message ?: "An unknown error occurred")
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}