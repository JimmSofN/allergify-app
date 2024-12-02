package com.example.allergifyapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.allergifyapp.databinding.FragmentHistoryScreenBinding

class HistoryScreen : Fragment() {
    private lateinit var binding: FragmentHistoryScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

}