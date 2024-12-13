package com.example.allergifyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allergifyapp.data.remote.model.barcode.BarcodeResponse
import com.example.allergifyapp.repository.remote.BarcodeScanRepository
import com.example.allergifyapp.utils.DataStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ScanBarcodeViewModel @Inject constructor(
    private val barcodeScanRepository: BarcodeScanRepository
): ViewModel(){


    private val _barcodeScanResult = MutableLiveData<DataStatus<BarcodeResponse>>()
    val barcodeScanResult: LiveData<DataStatus<BarcodeResponse>>
        get() = _barcodeScanResult

    fun scanBarcode(barcode: String) {
        viewModelScope.launch {
            barcodeScanRepository.scanBarcode(barcode).collect {
                _barcodeScanResult.value = it
            }
        }
    }
}