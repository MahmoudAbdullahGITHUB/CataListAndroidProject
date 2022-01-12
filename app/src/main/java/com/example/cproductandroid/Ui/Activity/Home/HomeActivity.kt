package com.example.cproductandroid.Ui.Activity.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cproductandroid.Local.Preference.MySharedPrefManager
import com.example.cproductandroid.Local.Preference.PrefsConstants
import com.example.cproductandroid.Ui.Adapter.ProductListAdapter
import com.example.cproductandroid.R
import com.example.cproductandroid.Ui.Activity.AddingProduct.AddingProductsActivity
//import com.example.cproductandroid.model.entity.ProductRoomModel
import com.example.cproductandroid.Local.RoomDB.ProductDatabase
import com.example.cproductandroid.model.AllProduct
import com.example.cproductandroid.utiles.CheckNetworkConnection
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.*


class HomeActivity : AppCompatActivity() {

    private lateinit var mySharedPrefManager: MySharedPrefManager
    lateinit var token: String
    lateinit var recyclerView: RecyclerView
    lateinit var myAdapter: ProductListAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var fullName: TextView
    lateinit var email: TextView
    private lateinit var viewModel: HomeActivityViewModel
    private lateinit var floatingActionButton: FloatingActionButton
    lateinit var db: ProductDatabase
    lateinit var progressBar: ProgressBar
    lateinit var productsList: List<AllProduct>
    private lateinit var checkNetworkConnection: CheckNetworkConnection


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        initialize()


        db = ProductDatabase.getInstance(this)
        callNetworkConnection()

    }


    private fun getProductsFromDB() {
        GlobalScope.launch(Dispatchers.IO) {
            val returnedProducts = async {
                db.productDAO().getProducts()
            }

            /** now using main thread to be able to using ui views */
            withContext(Dispatchers.Main) {
                progressBar.visibility = View.VISIBLE
                productsList = returnedProducts.await()

                Log.d("TAG", "getProductsFromDB: " + productsList.size)

                progressBar.visibility = View.GONE

                myAdapter = ProductListAdapter(
                    baseContext,
                    productsList
                )

                myAdapter.notifyDataSetChanged()

                recyclerView.adapter = myAdapter
            }


        }
    }


    private fun initialize() {
        floatingActionButton = findViewById(R.id.add_fab)
        mySharedPrefManager = MySharedPrefManager(applicationContext)
        recyclerView = findViewById(R.id.recycler_view)
        progressBar = findViewById(R.id.progress_bar)

        recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.navigation_view)

        val header: View = navigationView.getHeaderView(0)
        fullName = header.findViewById(R.id.fullName)
        email = header.findViewById(R.id.userEmail)
        fullName.text = mySharedPrefManager.getString(PrefsConstants.KEY_USER_NAME)
        email.text = mySharedPrefManager.getString(PrefsConstants.KEY_USER_EMAIL)


        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        onClickButtons()
    }

    private fun onClickButtons() {
        floatingActionButton.setOnClickListener(View.OnClickListener {
            intent = Intent(this@HomeActivity, AddingProductsActivity::class.java)
            startActivity(intent)
        })
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[HomeActivityViewModel::class.java]

        viewModel.getProductsMutableLiveData().observe(this, Observer {
            if (it != null) {
                myAdapter = ProductListAdapter(
                    baseContext,
                    it.response.categories.allProducts
                )

                for (pro in it.response.categories.allProducts){
                    insertProductInDB2(pro)
                }

                myAdapter.notifyDataSetChanged()

                recyclerView.adapter = myAdapter

                print("how to say")
            } else {
                println("unfroutnly ")

            }
        })

    }

    private fun insertProductInDB2(monAllProduct: AllProduct) {
//        val productRoomModel = AllProduct(
//            0, "barCode0", 0, "barCodeName0",
//            "barCodeImage0", 0, "ProductName01"
//        )

        GlobalScope.launch {
            withContext((Dispatchers.IO)) {
                db.productDAO().insertOrUpdateProduct(monAllProduct)
            }
        }

    }




    private fun getProductsFromServer() {
        token = mySharedPrefManager.getString(PrefsConstants.KEY_USER_TOKEN)
        viewModel.getProductsFromServer(token)
    }


    private fun callNetworkConnection() {
        checkNetworkConnection = CheckNetworkConnection(application)
        checkNetworkConnection.observe(this, { isConnected ->
            if (isConnected) {
                initViewModel()
                getProductsFromServer()
                Log.d("TAG", "callNetworkConnection: doing well ")
            } else {
                getProductsFromDB()
                Log.d("TAG", "callNetworkConnection: doing wrong ")

            }
        })

    }


}

/*
 private fun insertProductInDB() {
        val productRoomModel = AllProduct(
            0, "barCode0", 0, "barCodeName0",
            "barCodeImage0", 0, "ProductName01"
        )

        GlobalScope.launch {
            withContext((Dispatchers.IO)) {
                db.productDAO().insertOrUpdateProduct(productRoomModel)
            }
        }

    }
*/


/**
 * Retrofit MVC
 */
/*
    private fun getProducts() {
//        token = mySharedPrefManager.getString(PrefsConstants.KEY_USER_TOKEN)
//
//        val call = RetrofitClient.apiInterface.getProducts("Bearer $token")
//
//        call.enqueue(object : Callback<Products?> {
//            override fun onResponse(call: Call<Products?>, response: Response<Products?>) {
//
//                myAdapter = ProductListAdapter(
//                    baseContext,
//                    response.body()!!.response.categories.allProducts
//                )
//
//                myAdapter.notifyDataSetChanged()
//                // myAdapter.notifyItemChanged(postion)
//
//                recyclerView.adapter = myAdapter
//
//
//                println("My Pleasure " + response.body())
//            }
//
//            override fun onFailure(call: Call<Products?>, t: Throwable) {
//            }
//        })


    }


 */


/**
 * without Retrofit Singleton
 */
/*
private fun getProducts() {
    token = mySharedPrefManager.getString(PrefsConstants.KEY_USER_TOKEN)

    val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val apiInterface: ApiInterface = retrofit.create(ApiInterface::class.java)

    val call = apiInterface.getProducts("Bearer $token")

    call.enqueue(object : Callback<Products?> {
        override fun onResponse(call: Call<Products?>, response: Response<Products?>) {

            myAdapter = ProductListAdapter(
                baseContext,
                response.body()!!.response.categories.allProducts
            )

            myAdapter.notifyDataSetChanged()

//                myAdapter.notifyItemChanged(postion)

            recyclerView.adapter = myAdapter


            println("My Pleasure " + response.body())
        }

        override fun onFailure(call: Call<Products?>, t: Throwable) {
        }
    })

}

*/