package com.example.cproductandroid.Ui.Activity.AddingProduct

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cproductandroid.Network.RetrofitClient
import com.example.cproductandroid.model.AddProductResponse
import com.example.cproductandroid.model.ProductsRequestList
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddingProductViewModel : ViewModel() {
    private var pAddedMutableLiveData: MutableLiveData<AddProductResponse> = MutableLiveData()


    fun getPAddedMutableLiveData(): MutableLiveData<AddProductResponse> {
        return pAddedMutableLiveData
    }


    @DelicateCoroutinesApi
    fun addProductToServer(productsRequestList: ProductsRequestList, token: String) {

//        val call = RetrofitClient.apiInterface.addProduct(productsRequestList,"Bearer $token")

        GlobalScope.launch(Dispatchers.IO) {

            val response =
                RetrofitClient.apiInterface.addProduct(productsRequestList, "Bearer $token")

            Log.d("response ", "response is equ " + response.body()!!.response.message)

            pAddedMutableLiveData.postValue(response.body())

        }


//        call.enqueue(object : Callback<AddProductResponse?> {
//            override fun onResponse(
//                call: Call<AddProductResponse?>,
//                response: Response<AddProductResponse?>
//            ) {
//
//                pAddedMutableLiveData.postValue(response.body())
//                println("\nyes from mvvm + "+ response.body())
//
//            }
//
//            override fun onFailure(call: Call<AddProductResponse?>, t: Throwable) {
//                println("no from mvvm$t")
//            }
//        })

    }


}