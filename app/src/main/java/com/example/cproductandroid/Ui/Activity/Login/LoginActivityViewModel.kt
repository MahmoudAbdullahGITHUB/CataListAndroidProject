package com.example.cproductandroid.Ui.Activity.Login

import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cproductandroid.Local.Preference.PrefsConstants
import com.example.cproductandroid.Network.RetrofitClient
import com.example.cproductandroid.Ui.Activity.Home.HomeActivity
import com.example.cproductandroid.model.Products
import com.example.cproductandroid.model.ResponseUser
import com.example.cproductandroid.model.UserRequest
import com.example.cproductandroid.model.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivityViewModel : ViewModel() {
    private var loginMutableLiveData: MutableLiveData<ResponseUser>

    init {
        loginMutableLiveData = MutableLiveData()
    }


    fun getLoginMutableLiveData(): MutableLiveData<ResponseUser> {
        return loginMutableLiveData
    }


    fun loginToServer(userRequest: UserRequest) {



        GlobalScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.apiInterface.login(userRequest)
            loginMutableLiveData.postValue(response.body())

        }

    }


}