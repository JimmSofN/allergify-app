package com.example.allergifyapp.data.remote.model.login

data class LoginResponse(
    val error: Boolean,
    val message: String,
    val token: String
)