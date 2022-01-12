package com.example.cproductandroid.Local.RoomDB

import androidx.room.*
//import com.example.cproductandroid.model.entity.ProductRoomModel
import com.example.cproductandroid.model.AllProduct

@Dao
interface ProductDAO {

    /** if i found the same Product replace it or in another meaning i make update **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProduct(productRoomModel: AllProduct)

    @Delete
    suspend fun deleteProduct(productRoomModel: AllProduct)

    @Query("select * from products_table")
    suspend fun getProducts():List<AllProduct>

}