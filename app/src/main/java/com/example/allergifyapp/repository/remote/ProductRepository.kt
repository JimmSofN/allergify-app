package com.example.allergifyapp.repository.remote

import com.example.allergifyapp.data.remote.api.ApiService
import com.example.allergifyapp.data.remote.model.product.ProductRequest
import com.example.allergifyapp.data.remote.model.product.ProductResponse
import com.example.allergifyapp.utils.DataStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun addProduct(request: ProductRequest): Flow<DataStatus<ProductResponse>> = flow {
        emit(DataStatus.loading())
        try {
            val response = apiService.addProduct(request)

            when (response.code()) {
                201 -> {
                    response.body()?.let {
                        emit(DataStatus.success(it))
                    } ?: emit(DataStatus.error("No data found"))
                }
                else -> {
                    emit(DataStatus.error("An error occurred "))
                }
            }
        } catch (e: Exception) {
            emit(DataStatus.error("Ups.. something went wrong"))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getProduct(productId: String): Flow<DataStatus<ProductResponse>> = flow {
        emit(DataStatus.loading())
        try {
            val response = apiService.getProduct(productId)

            when (response.code()) {
                200 -> {
                    response.body()?.let {
                        emit(DataStatus.success(it))
                    } ?: emit(DataStatus.error("No data found"))
                }
                else -> {
                    emit(DataStatus.error("An error occurred "))
                }
            }
        } catch (e: Exception) {
            emit(DataStatus.error("Ups.. something went wrong"))
        }
    }.flowOn(Dispatchers.IO)

}