package com.example.allergifyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allergifyapp.data.remote.model.AllergenScan.AllergenResponse
import com.example.allergifyapp.repository.remote.AllergyScanRepository
import com.example.allergifyapp.utils.DataStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class AllergyScanViewModel @Inject constructor(
    private val allergyScanRepository: AllergyScanRepository
) : ViewModel() {

    private val _allergyScanResult = MutableLiveData<DataStatus<AllergenResponse>>()
    val allergyScanResult: LiveData<DataStatus<AllergenResponse>>
        get() = _allergyScanResult

    fun uploadImage(imageBytes: ByteArray) {
        viewModelScope.launch {
            val imagePart = createImagePart(imageBytes)
            allergyScanRepository.uploadImage(imagePart).collect { status ->
                _allergyScanResult.value = status
            }
        }
    }

    private fun createImagePart(imageBytes: ByteArray): MultipartBody.Part {
        // Convert ByteArray to RequestBody and then to MultipartBody.Part
        val requestFile = RequestBody.create("image/jpeg".toMediaType(), imageBytes)
        return MultipartBody.Part.createFormData("file", "image.jpg", requestFile)
    }
}