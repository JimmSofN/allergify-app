package com.example.allergifyapp.repository.remote

import com.example.allergifyapp.data.remote.api.ApiService
import com.example.allergifyapp.data.remote.model.barcode.BarcodeRequest
import com.example.allergifyapp.data.remote.model.barcode.BarcodeResponse
import com.example.allergifyapp.utils.DataStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class BarcodeScanRepository @Inject constructor(
    private val apiService: ApiService
){
    suspend fun scanBarcode(barcode: String): Flow<DataStatus<BarcodeResponse>> = flow {
        emit(DataStatus.loading())

        val request = BarcodeRequest(barcode)
        val result: Response<BarcodeResponse> = apiService.scanBarcode(request)

        when (result.code()) {
            200 -> {
                val barcodeResponse = result.body()
                emit(DataStatus.success(barcodeResponse))
            }
            400 -> {
                emit(DataStatus.error("Invalid barcode"))
            }
            404 -> {
                emit(DataStatus.error("Barcode not found"))
            }
            else -> {
                emit(DataStatus.error("An error occurred, please try again later"))
            }
        }
    }.catch { exception ->
        emit(DataStatus.error("Ups.. something went wrong"))
    }.flowOn(Dispatchers.IO)


}