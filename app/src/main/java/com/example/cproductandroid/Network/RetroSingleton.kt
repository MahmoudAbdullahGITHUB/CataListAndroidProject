package com.example.cproductandroid.Network

import com.example.cproductandroid.Network.Urls.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroSingleton {

    companion object {
        private var retrofit: Retrofit? = null

        fun getClient(): Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
            }
            return retrofit!!
        }



    }

}


