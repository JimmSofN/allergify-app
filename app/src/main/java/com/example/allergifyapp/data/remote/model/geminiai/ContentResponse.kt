package com.example.allergifyapp.data.remote.model.geminiai


import com.google.gson.annotations.SerializedName

data class ContentResponse(
    val parts: List<PartResponse>,
    val role: String
)