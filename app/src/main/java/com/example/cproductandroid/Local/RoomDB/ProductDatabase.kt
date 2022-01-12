package com.example.cproductandroid.Local.RoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
//import com.example.cproductandroid.model.entity.ProductRoomModel
import com.example.cproductandroid.model.AllProduct

@Database(entities = [AllProduct::class], version = 1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDAO(): ProductDAO

    companion object {

        /** Volatile mean this instance will be showed in all threads */
        @Volatile
        private var instance: ProductDatabase? = null

        fun getInstance(context: Context): ProductDatabase {
            return instance ?: synchronized(Any()) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): ProductDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                ProductDatabase::class.java,
                "products_table"
            ).build()
        }


    }

}