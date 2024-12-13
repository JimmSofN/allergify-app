package com.example.allergifyapp.ui.main


import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.example.allergifyapp.R
import com.example.allergifyapp.data.local.model.UserInfo
import com.example.allergifyapp.databinding.ActivitySettingUserAdjustmentBinding
import com.example.allergifyapp.viewmodel.UserInfoViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingUserAdjustment : BaseActivity() {
    private lateinit var binding: ActivitySettingUserAdjustmentBinding
    private val userInfoViewModel: UserInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingUserAdjustmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDropdowns()
        setupButton()
        observeUserInfo()

    }

    private fun setupDropdowns() {
        val ageItems = (12..44).map { it.toString() }
        val ageAdapter = ArrayAdapter(this, R.layout.list_item, ageItems)
        binding.dropdownAgeAdjustmentField.setAdapter(ageAdapter)

        val heightItems = (90..220).map { it.toString() }
        val heightAdapter = ArrayAdapter(this, R.layout.list_item, heightItems)
        binding.dropdownHeightAdjustmentField.setAdapter(heightAdapter)

        val weightItems = (40..160).map { it.toString() }
        val weightAdapter = ArrayAdapter(this, R.layout.list_item, weightItems)
        binding.dropdownWeightAdjustmentField.setAdapter(weightAdapter)

    }

    private fun observeUserInfo() {
        userInfoViewModel.userInfo.observe(this) { userInfoList ->
            val userInfo = userInfoList.firstOrNull()
            userInfo?.let {
                binding.dropdownAgeAdjustmentField.setText(it.age, false)
                binding.dropdownHeightAdjustmentField.setText(it.height, false)
                binding.dropdownWeightAdjustmentField.setText(it.weight, false)
            }
        }
    }

    private fun setupButton() {
        binding.updateButton.setOnClickListener {
            val age = binding.dropdownAgeAdjustmentField.text.toString().toIntOrNull()
            val height = binding.dropdownHeightAdjustmentField.text.toString().toIntOrNull()
            val weight = binding.dropdownWeightAdjustmentField.text.toString().toIntOrNull()

            if (age != null && height != null && weight != null) {
                val userInfo = UserInfo(age = age.toString(), height = height.toString(), weight = weight.toString())

                userInfoViewModel.insertUserInfo(userInfo)

            } else {
                //TODO
            }
            finish()
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }
}