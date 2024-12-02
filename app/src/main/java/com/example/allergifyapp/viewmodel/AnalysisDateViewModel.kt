package com.example.allergifyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Calendar

class AnalysisDateViewModel: ViewModel() {
    private val _selectedDate = MutableLiveData<Calendar>().apply {
        value = Calendar.getInstance()
    }
    val selectedDate: LiveData<Calendar> get() = _selectedDate

    fun setSelectedDate(date: Calendar) {
        _selectedDate.value = date
    }
}