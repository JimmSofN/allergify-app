package com.example.allergifyapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.allergifyapp.databinding.FragmentUserProfileScreenBinding

class UserProfileScreen : Fragment() {
    private lateinit var binding: FragmentUserProfileScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserProfileScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

}