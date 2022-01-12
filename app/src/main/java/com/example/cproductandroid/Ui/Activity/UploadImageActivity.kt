package com.example.cproductandroid.Ui.Activity

import android.Manifest
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.cproductandroid.Local.Preference.MySharedPrefManager
import com.example.cproductandroid.Local.Preference.PrefsConstants
import com.example.cproductandroid.Network.RetrofitClient
import com.example.cproductandroid.R
import com.example.cproductandroid.model.ImageResponse
import com.example.cproductandroid.utiles.RealPathOfFileFromUri
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import android.app.Activity
import android.content.Context

import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager

import androidx.core.content.ContextCompat

import android.os.Build
import android.widget.Toast
import com.example.cproductandroid.utiles.ExternalStoragePermission


class UploadImageActivity : AppCompatActivity() {


    private lateinit var mySharedPrefManager: MySharedPrefManager
    lateinit var token: String
    lateinit var image: ImageView
    private lateinit var btnUploadFile: Button
    lateinit var btnSelectImage: Button
    lateinit var bitMap: Bitmap
    private lateinit var filePath: String

    private val getImage = registerForActivityResult(
        ActivityResultContracts.GetContent(),
        ActivityResultCallback {
            image.setImageURI(it)

            Log.d("TAG", "onClicks: " + it.toString() + " ")

            filePath = RealPathOfFileFromUri.getRealPathFromURI(this, it)!!

        }
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_image)

        mySharedPrefManager = MySharedPrefManager(applicationContext)
        btnUploadFile = findViewById(R.id.btn_upload_file)
        image = findViewById(R.id.image)
        btnSelectImage = findViewById(R.id.btn_select_image)
        token = mySharedPrefManager.getString(PrefsConstants.KEY_USER_TOKEN)

        ExternalStoragePermission
            .checkPermissionREAD_EXTERNAL_STORAGE(this)



        onClicks()


    }

    private fun onClicks() {
        btnUploadFile.setOnClickListener(View.OnClickListener {
            uploadFile()
        })







        btnSelectImage.setOnClickListener(View.OnClickListener {

            getImage.launch("image/*")

        })


    }




    private fun uploadFile() {

        Log.d("TAG", "uploadFile: ")

        val file: File = File("$filePath")

        val requestBody: RequestBody = RequestBody.create(MediaType.parse("image/*"), file)

        val part: MultipartBody.Part =
            MultipartBody.Part.createFormData("newImage", file.name, requestBody)

        val call = RetrofitClient.apiInterface.uploadFile(token, part)

        call!!.enqueue(object : Callback<ImageResponse?> {
            override fun onResponse(
                call: Call<ImageResponse?>,
                response: Response<ImageResponse?>
            ) {

                Toast.makeText(this@UploadImageActivity, "Uploaded Successful ", Toast.LENGTH_LONG).show()

                Log.d("TAG", "onResponse: i cannot believe")

            }

            override fun onFailure(call: Call<ImageResponse?>, t: Throwable) {
                Toast.makeText(this@UploadImageActivity, "Uploaded Failed ", Toast.LENGTH_SHORT).show()

                Log.d("TAG", "onResponse: i believed" + t.message)

            }
        })


    }


}