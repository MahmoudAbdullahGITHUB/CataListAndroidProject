package com.example.cproductandroid.Ui.Activity.AddingProduct

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cproductandroid.Local.Preference.MySharedPrefManager
import com.example.cproductandroid.Local.Preference.PrefsConstants
import com.example.cproductandroid.R
import com.example.cproductandroid.Ui.Activity.Home.HomeActivity
import com.example.cproductandroid.Ui.Activity.UploadImageActivity
import com.example.cproductandroid.model.AddProductItem
import com.example.cproductandroid.model.ProductsRequestList

class AddingProductsActivity : AppCompatActivity() {

    lateinit var price: EditText
    private lateinit var count: EditText
    lateinit var barCode: EditText
    private lateinit var addProductBtn: Button
    lateinit var btnUploadActivity: Button
    private lateinit var mySharedPrefManager: MySharedPrefManager
    lateinit var token: String
    private lateinit var viewModel: AddingProductViewModel
    lateinit var texView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_products)

        initialize()
        observeViewModel()
        onClickButtons()

    }

    private fun initialize() {
        price = findViewById(R.id.price)
        count = findViewById(R.id.count)
        barCode = findViewById(R.id.bar_code)
        btnUploadActivity = findViewById(R.id.btn_upload_activity)
        addProductBtn = findViewById(R.id.add_product)
        texView = findViewById(R.id.add_response)
        mySharedPrefManager = MySharedPrefManager(applicationContext)
        token = mySharedPrefManager.getString(PrefsConstants.KEY_USER_TOKEN)

    }

    private fun observeViewModel() {
        viewModel = ViewModelProvider(this)[AddingProductViewModel::class.java]

        viewModel.getPAddedMutableLiveData().observe(this, Observer {
            if (it != null) {
                texView.text = it.message
                Toast.makeText(this, "${it.message} added", Toast.LENGTH_SHORT).show()
                print("how to say Adding")
            } else {
                println("unfroutnly Adding")

            }
        })

    }


    private fun onClickButtons() {
        addProductBtn.setOnClickListener(View.OnClickListener {
            addProducts()
        })

        btnUploadActivity.setOnClickListener(View.OnClickListener {
            intent = Intent(this@AddingProductsActivity, UploadImageActivity::class.java)
            startActivity(intent)
        })

    }


    private fun addProducts() {
        if (barCode.text.toString() != ""
            && count.text.toString() != ""
            && price.text.toString() != ""
        ) {
            val productsRequestList = ProductsRequestList()
            val barCodeI = barCode.text.toString().toInt()
            val countI = count.text.toString().toInt()
            val priceI = price.text.toString().toInt()
            val addProductRequest = AddProductItem(barCodeI, countI, priceI, "")
            productsRequestList.add(addProductRequest)
            viewModel.addProductToServer(productsRequestList, token)
        }
    }


}


/**
 * Retrofit MVC
 */

/*
private fun onClickButtons2() {

        val productsRequestList = ProductsRequestList()
//        var addProductItem:AddProductItem =

        addProductBtn.setOnClickListener(View.OnClickListener {

            val addProductRequest = AddProductItem(1, 20, 10, "")

            productsRequestList.add(addProductRequest)

            val call = RetrofitClient.apiInterface.addProduct(productsRequestList, "Bearer $token")

            call.enqueue(object : Callback<AddProductResponse?> {
                override fun onResponse(
                    call: Call<AddProductResponse?>,
                    response: Response<AddProductResponse?>
                ) {
                    println("hello Guys "+response.body())
                }

                override fun onFailure(call: Call<AddProductResponse?>, t: Throwable) {
                    println("not second time")
                }
            })


        })
    }

 */


/*

//
//    private fun onClickButtons() {
//
//        val productsRequestList = ProductsRequestList()
////        var addProductItem:AddProductItem =
//
//        addProductBtn.setOnClickListener(View.OnClickListener {
//
//            val addProductRequest = AddProductItem(1, 20, 10, "")
//
//            productsRequestList.add(addProductRequest)
//
//
//            GlobalScope.launch(Dispatchers.IO) {
//
//                val response = RetrofitClient.apiInterface.addProduct(productsRequestList, "Bearer $token")
//
//                Log.d("response ","response is equ "+response.body()!!.response.message)
//
//            }
//
//
//        })
//    }



 */
