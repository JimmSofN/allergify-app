package com.example.allergifyapp.data.remote.api

import com.example.allergifyapp.data.remote.model.login.LoginRequest
import com.example.allergifyapp.data.remote.model.login.LoginResponse
import com.example.allergifyapp.data.remote.model.register.RegisterRequest
import com.example.allergifyapp.data.remote.model.register.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("userLogin/signup")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("userLogin/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}