package com.example.allergifyapp.data.remote.model.product

import com.google.gson.annotations.SerializedName

data class ProductRequest(
    @SerializedName("product_id") val productId: String,
    val name: String,
    val composition: String,
    val nutrition: NutritionRequest,
    val allergens: List<String>
)
