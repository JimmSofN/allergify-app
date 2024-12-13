package com.example.allergifyapp.data.remote.api

import com.example.allergifyapp.data.remote.model.barcode.BarcodeRequest
import com.example.allergifyapp.data.remote.model.barcode.BarcodeResponse
import com.example.allergifyapp.data.remote.model.login.LoginRequest
import com.example.allergifyapp.data.remote.model.login.LoginResponse
import com.example.allergifyapp.data.remote.model.product.ProductRequest
import com.example.allergifyapp.data.remote.model.product.ProductResponse
import com.example.allergifyapp.data.remote.model.register.RegisterRequest
import com.example.allergifyapp.data.remote.model.register.RegisterResponse
import com.example.allergifyapp.data.remote.model.userallergen.UserAllergenResponse
import com.example.allergifyapp.data.remote.model.userallergenadd.AddAllergenResponse
import com.example.allergifyapp.data.remote.model.userallergenadd.UserAllergenRequest
import com.example.allergifyapp.data.remote.model.userallergendelete.UserAllergenDeleteResponse
import com.example.allergifyapp.data.remote.model.userallergenupdate.UpdateAllergenRequest
import com.example.allergifyapp.data.remote.model.userallergenupdate.UpdateAllergenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("userLogin/signup")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("userLogin/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("trackingUser/allergens")
    suspend fun getUserAllergens(): Response<UserAllergenResponse>

    @PUT("trackingUser/allergens/{id}")
    suspend fun updateUserAllergens(
        @Path("id") id: String,
        @Body request: UpdateAllergenRequest
    ): Response<UpdateAllergenResponse>

    @POST("trackingUser/allergens")
    suspend fun addUserAllergens(@Body allergen: UserAllergenRequest): Response<AddAllergenResponse>

    @DELETE("trackingUser/allergens/{id}")
    suspend fun deleteUserAllergens(@Path("id") id: String): Response<UserAllergenDeleteResponse>

    @POST("/api/scanning/barcodeScan")
    suspend fun scanBarcode(@Body request: BarcodeRequest): Response<BarcodeResponse>

    @POST("/api/products")
    suspend fun addProduct(@Body request: ProductRequest): Response<ProductResponse>

    @GET("/api/products/{productId}")
    suspend fun getProduct(@Path("productId") productId: String): Response<ProductResponse>
}