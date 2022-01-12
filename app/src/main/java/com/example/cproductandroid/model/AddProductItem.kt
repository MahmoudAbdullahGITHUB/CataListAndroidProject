package com.example.cproductandroid.model


class ProductsRequestList : ArrayList<AddProductItem>()


data class AddProductItem(
    val barcodeID: Int,
    val count: Int,
    val price: Int,
    val imagePath: String
)