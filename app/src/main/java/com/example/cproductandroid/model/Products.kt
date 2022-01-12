package com.example.cproductandroid.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Products(
    val isSuccess: Boolean,
    val message: String,
    val response: Response
)


data class Response(
    val categories: Categories
)


data class Categories(
    val allProducts: List<AllProduct>
)

@Entity(tableName = "products_table")
data class AllProduct(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var barCode: String,
    var barCodeID: Int,
    var barcodeName: String,
    var barecodeImage: String,
    var productID: Int,
    var productName: String
)
