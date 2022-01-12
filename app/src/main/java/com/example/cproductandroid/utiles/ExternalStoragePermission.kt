package com.example.cproductandroid.utiles

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object ExternalStoragePermission {
    val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123

    fun checkPermissionREAD_EXTERNAL_STORAGE(
        context: Context?
    ): Boolean {
        val currentAPIVersion = Build.VERSION.SDK_INT
        return if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (context as Activity?)!!,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    Log.d(
                        "TAG",
                        "checkPermissionREAD_EXTERNAL_STORAGE: "
                                + Manifest.permission.READ_EXTERNAL_STORAGE
                    )

                    /*showDialog(
                        "External storage", context,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )*/

                } else {
                    ActivityCompat
                        .requestPermissions(
                            (context as Activity?)!!,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                        )
                }
                false
            } else {
                true
            }
        } else {
            true
        }
    }

}