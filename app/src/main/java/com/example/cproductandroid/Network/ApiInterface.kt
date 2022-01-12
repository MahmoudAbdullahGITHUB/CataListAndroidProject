package com.example.cproductandroid.Network

import com.example.cproductandroid.model.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import okhttp3.RequestBody
import retrofit2.http.*


interface ApiInterface {

    @POST(Urls.LOGIN)
    suspend fun login(@Body userRequest: UserRequest): Response<ResponseUser>

    @GET(Urls.GET_PRODUCTS)
    fun getProducts(@Header("Authorization") token: String): Call<Products>


    @POST(Urls.ADD_PRODUCT)
    suspend fun addProduct(
        @Body productsRequestList: ProductsRequestList,
        @Header("Authorization") token: String
    ): Response<AddProductResponse>





    @Multipart
    @POST(Urls.UPLOAD_FILE)
    fun uploadFile(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): Call<ImageResponse>?


}