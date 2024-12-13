package com.example.allergifyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allergifyapp.data.remote.model.geminiai.Content
import com.example.allergifyapp.data.remote.model.geminiai.GeminiAiRequest
import com.example.allergifyapp.data.remote.model.geminiai.GeminiAiResponse
import com.example.allergifyapp.data.remote.model.geminiai.Part
import com.example.allergifyapp.repository.remote.GeminiApiRepository
import com.example.allergifyapp.utils.DataStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeminiAiViewModel @Inject constructor(
    private val geminiApiRepository: GeminiApiRepository
) : ViewModel() {

    private val _generateContentStatus = MutableLiveData<DataStatus<GeminiAiResponse>>()
    val generateContentStatus: LiveData<DataStatus<GeminiAiResponse>>
        get() = _generateContentStatus

    fun generateContent(request: GeminiAiRequest) {
        viewModelScope.launch {
            _generateContentStatus.value = DataStatus.loading()

            geminiApiRepository.generateContent(request).collect {
                _generateContentStatus.value = it
            }
        }
    }

    fun askGemini(question: String) {

        val request = GeminiAiRequest(
            contents = listOf(
                Content(
                    parts = listOf(
                        Part(text = question)
                    )
                )
            )
        )
        generateContent(request)
    }
}