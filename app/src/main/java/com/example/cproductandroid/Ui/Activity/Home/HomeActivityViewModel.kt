package com.example.cproductandroid.Ui.Activity.Home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cproductandroid.Network.RetrofitClient
import com.example.cproductandroid.model.Products
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivityViewModel: ViewModel() {
    private var productsMutableLiveData: MutableLiveData<Products>

    init {
        productsMutableLiveData  = MutableLiveData()
    }

    fun getProductsMutableLiveData(): MutableLiveData<Products> {
        return productsMutableLiveData
    }

     fun getProductsFromServer(token:String) {

        val call = RetrofitClient.apiInterface.getProducts("Bearer $token")

        call.enqueue(object : Callback<Products?> {
            override fun onResponse(call: Call<Products?>, response: Response<Products?>) {
                productsMutableLiveData.postValue(response.body())

                println("My Pleasure " + response.body())
            }

            override fun onFailure(call: Call<Products?>, t: Throwable) {
//                productsMutableLiveData.postValue(null)
            }
        })
    }


}