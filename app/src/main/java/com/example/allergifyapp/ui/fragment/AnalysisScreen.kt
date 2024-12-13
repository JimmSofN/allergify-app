package com.example.allergifyapp.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.allergifyapp.R
import com.example.allergifyapp.databinding.FragmentAnalysisScreenBinding
import com.example.allergifyapp.utils.DataStatus
import com.example.allergifyapp.viewmodel.AnalysisDateViewModel
import com.example.allergifyapp.viewmodel.GeminiAiViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AnalysisScreen : Fragment() {
    private lateinit var binding: FragmentAnalysisScreenBinding
    private val analysisDateViewModel: AnalysisDateViewModel by viewModels()
    private val geminiAiViewModel: GeminiAiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnalysisScreenBinding.inflate(inflater, container, false)

        setupButtons()
        observeViewModel()
        observeGeminiViewModel()
        setupTextFieldListener()

        return binding.root
    }

    private fun setupButtons() {
        binding.prevCalendarButton.setOnClickListener {
            analysisDateViewModel.selectedDate.value?.let {
                it.add(Calendar.DAY_OF_MONTH, -1)
                analysisDateViewModel.setSelectedDate(it)
            }
        }

        binding.nextCalendarButton.setOnClickListener {
            analysisDateViewModel.selectedDate.value?.let {
                it.add(Calendar.DAY_OF_MONTH, 1)
                analysisDateViewModel.setSelectedDate(it)
            }
        }

        binding.setCalendarButton.setOnClickListener {
            showDatePicker()
        }

        binding.askGeminiButton.setOnClickListener {
            val question = binding.askGeminiField.text.toString()
            if (question.isNotEmpty()) {
                geminiAiViewModel.askGemini(question)
            } else {
                binding.geminiAnswerText.text = getString(R.string.fragment_analysis_screen_ask_gemini_empty_question)
            }
        }
    }

    private fun showDatePicker() {
        val currentDate = analysisDateViewModel.selectedDate.value?.timeInMillis ?: Calendar.getInstance().timeInMillis
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.select_date_title))
            .setSelection(currentDate)
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            val selectedCalendar = Calendar.getInstance().apply {
                timeInMillis = selection
            }
            analysisDateViewModel.setSelectedDate(selectedCalendar)
        }
        datePicker.show(parentFragmentManager, "DATE_PICKER")
    }

    private fun observeViewModel() {
        analysisDateViewModel.selectedDate.observe(viewLifecycleOwner) { selectedDate ->
            updateButtonText(selectedDate)
        }

    }

    private fun observeGeminiViewModel() {
        geminiAiViewModel.generateContentStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                DataStatus.Status.LOADING -> {
                    binding.geminiAnswerText.text = getString(R.string.fragment_analysis_screen_ask_gemini_text_answer_loading)
                }
                DataStatus.Status.SUCCESS -> {
                    val response = it.data
                    val answerText = response?.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                    binding.geminiAnswerText.text = answerText
                }
                DataStatus.Status.ERROR -> {
                    binding.geminiAnswerText.text = it.message
                }
            }
        }
    }

    private fun setupTextFieldListener() {
        binding.askGeminiField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun updateButtonText(selectedDate: Calendar) {
        val today = Calendar.getInstance()
        val dateFormatFull = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
        val dateFormatDayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault())

        when {
            isSameDay(selectedDate, today) -> {
                binding.setCalendarButton.text = getString(R.string.today_text)
            }
            isSameDay(selectedDate, today.apply { add(Calendar.DAY_OF_MONTH, -1) }) -> {
                binding.setCalendarButton.text = getString(R.string.yesterday_text)
            }
            selectedDate.before(today) &&
                    selectedDate.after(today.apply { add(Calendar.DAY_OF_MONTH, -6) }) -> {
                binding.setCalendarButton.text = dateFormatDayOfWeek.format(selectedDate.time)
            }
            else -> {
                binding.setCalendarButton.text = dateFormatFull.format(selectedDate.time)
            }
        }
    }

    private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }
}