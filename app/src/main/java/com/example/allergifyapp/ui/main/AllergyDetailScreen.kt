package com.example.allergifyapp.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import com.example.allergifyapp.databinding.ActivityAllergyDetailScreenBinding
import com.example.allergifyapp.utils.DataStatus
import com.example.allergifyapp.viewmodel.AllergyScanViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllergyDetailScreen : BaseActivity() {
    private val allergyScanViewModel: AllergyScanViewModel by viewModels()

    private lateinit var binding: ActivityAllergyDetailScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllergyDetailScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObserveAllergyScanResult()
    }

    private fun setupObserveAllergyScanResult() {
        allergyScanViewModel.allergyScanResult.observe(this) {
            when (it.status) {
                DataStatus.Status.LOADING -> {

                    binding.allergenText.text = "loading..."
                }

                DataStatus.Status.SUCCESS -> {

                    val allergens = it.data?.detected_allergens ?: "No allergens detected"
                    binding.allergenText.text = allergens.toString()
                }

                DataStatus.Status.ERROR -> {

                    binding.allergenText.text = it.message
                }
            }

        }
    }

}