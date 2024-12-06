package com.example.allergifyapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.allergifyapp.R
import com.example.allergifyapp.databinding.FragmentUserProfileScreenBinding
import com.example.allergifyapp.utils.DataStatus
import com.example.allergifyapp.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileScreen : Fragment() {
    private lateinit var binding: FragmentUserProfileScreenBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserProfileScreenBinding.inflate(inflater, container, false)



        setupObserveProfileStatus()

        return binding.root
    }

    private fun setupObserveProfileStatus() {

        viewModel.profile()

        viewModel.profileStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                DataStatus.Status.LOADING -> {
                    binding.userEmailProfile.text = getString(R.string.user_profile_loading_message)
                }
                DataStatus.Status.SUCCESS -> {
                    val profile = it.data
                    binding.userEmailProfile.text = profile?.user?.email ?: getString(R.string.user_profile_email_not_found_message)
                }
                DataStatus.Status.ERROR -> {
                    val errorMessage = it.message
                    binding.userEmailProfile.text = errorMessage ?: getString(R.string.user_profile_error_message)
                }
            }
        }
    }

}