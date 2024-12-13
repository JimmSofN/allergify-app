package com.example.allergifyapp.data.remote.model.product

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("product_id") val productId: String,
    val name: String,
    val composition: String,
    val nutrition: Nutrition,
    val allergens: List<String>
)
