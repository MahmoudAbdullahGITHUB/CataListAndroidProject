package com.example.cproductandroid.Network

import com.example.cproductandroid.Network.Urls.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

     val apiInterface:ApiInterface by lazy {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

        retrofit.create(ApiInterface::class.java)
    }

}