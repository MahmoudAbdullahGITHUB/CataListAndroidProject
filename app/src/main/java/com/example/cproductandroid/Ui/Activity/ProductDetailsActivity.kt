package com.example.cproductandroid.Ui.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrinterId
import android.widget.TextView
import com.example.cproductandroid.R

class ProductDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        val productName:TextView = findViewById(R.id.productName)
        val productId:TextView = findViewById(R.id.productId)

        val bundle : Bundle? = intent.extras

        val name = bundle!!.getString("productName")
        val id = bundle!!.getString("productID")


        productName.text = "product name: "+name
        productId.text = "barcode: "+id


    }
}