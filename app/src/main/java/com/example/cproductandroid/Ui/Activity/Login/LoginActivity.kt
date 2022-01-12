package com.example.cproductandroid.Ui.Activity.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cproductandroid.Local.Preference.MySharedPrefManager
import com.example.cproductandroid.Local.Preference.PrefsConstants
import com.example.cproductandroid.Network.RetrofitClient
import com.example.cproductandroid.R
import com.example.cproductandroid.Ui.Activity.Home.HomeActivity
import com.example.cproductandroid.model.UserRequest
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class LoginActivity : AppCompatActivity(), View.OnClickListener {


    private val TAG: String = "TAG"
    private lateinit var edtName: EditText
    private lateinit var loginStatus: TextView
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    lateinit var token: String
    lateinit var mySharedPreferences: MySharedPrefManager
    private lateinit var viewModel: LoginActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        edtName = findViewById(R.id.edt_userName)
        edtPassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.btn_login)
        loginStatus = findViewById(R.id.login_status)


        btnLogin.setOnClickListener(this)


        mySharedPreferences = MySharedPrefManager(applicationContext)


//        getMeLogin()

        observeViewModel()


    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_login -> {
                loginMethod(edtName.text.toString(), edtPassword.text.toString())
            }
            R.id.navigation_view -> {
                println("Ok")
            }
            else -> {
                println("no login button")
            }

        }
    }


    private fun loginMethod(toString: String, toString1: String) {
        Log.d(TAG, "loginMethod: ")
        val textName = edtName.text.toString()
        val textPassword = edtPassword.text.toString()

        val userRequest: UserRequest = UserRequest(textName, textPassword, 1)
//        val userRequest: UserRequest = UserRequest("102402", "102402", 1)

//        Log.d("TAG", "onCreate: edtName " + textName)

        viewModel.loginToServer(userRequest)

    }

    private fun observeViewModel() {

        viewModel = ViewModelProvider(this)[LoginActivityViewModel::class.java]

        viewModel.getLoginMutableLiveData().observe(this, Observer {
            if (it != null) {
                print("how to say login 2")
                if (it.isSuccess) {
                    loginStatus.text = "Succeeded"
                    token = it.response.user.token
                    val name = it.response.user.fullName
                    val email = it.response.user.userEmail

                    mySharedPreferences.putString(PrefsConstants.KEY_USER_TOKEN, token)
                    mySharedPreferences.putString(PrefsConstants.KEY_USER_NAME, name)
                    mySharedPreferences.putString(PrefsConstants.KEY_USER_EMAIL, email)

                    intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    loginStatus.text = "Failed"
                }

            } else {
                println("unfroutnly ")

            }
        })

    }


}


/**
 * first way without singleton
 */
//        val retrofit: Retrofit = Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl(BASE_URL)
//            .build()


/**
 * second way Singleton
 */
//val apiInterface: ApiInterface = RetroSingleton.getClient().create(ApiInterface::class.java)
//
//val call = apiInterface.login(user)


/**
 * login MVC
 */
/*

    private fun getMeLogin() {

        val userRequest: UserRequest = UserRequest("102402", "102402", 1)


        val call = RetrofitClient.apiInterface.login(userRequest)

        call.enqueue(object : Callback<ResponseUser?> {
            override fun onResponse(call: Call<ResponseUser?>, response: Response<ResponseUser?>) {
                println("Please " + response.body())

                if (response.body()!!.isSuccess) {
                    token = response.body()!!.response.user.token
                    val name = response.body()!!.response.user.fullName
                    val email = response.body()!!.response.user.userEmail

                    mySharedPreferences.putString(PrefsConstants.KEY_USER_TOKEN, token)
                    mySharedPreferences.putString(PrefsConstants.KEY_USER_NAME, name)
                    mySharedPreferences.putString(PrefsConstants.KEY_USER_EMAIL, email)

                    intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<ResponseUser?>, t: Throwable) {
                print("Not yet")
            }
        })


    }


 */