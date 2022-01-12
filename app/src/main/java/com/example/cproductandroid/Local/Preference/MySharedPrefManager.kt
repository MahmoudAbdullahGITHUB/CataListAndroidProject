package com.example.cproductandroid.Local.Preference

import android.content.Context
import android.content.SharedPreferences

class MySharedPrefManager(mContext: Context) {
    private val sharedPrefManager =
        mContext.getSharedPreferences(
            PrefsConstants.PREF_NAME,
            PrefsConstants.PREFERENCES_MODE
        )
    private val editor: SharedPreferences.Editor = sharedPrefManager.edit()

//    companion object {
//        private var prefs: SharedPreferences? = null
//        private var prefsHelper: MySharedPrefManager? = null
//        val PREF_NAME = "pref" //이 이름으로 xml파일이 만들어짐
//
//        fun getInstance(context: Context): MySharedPrefManager? {
//            if (prefsHelper == null) {
//                prefsHelper = MySharedPrefManager()
//            }
//            if (prefs == null) {
//                prefs = context.getSharedPreferences(PrefsConstants.PREF_NAME, Context.MODE_PRIVATE)
//            }
//
//            return prefsHelper
//        }
//    }



//    companion object {
//        private val sharePref = MySharedPrefManager()
//        private lateinit var sharedPreferences: SharedPreferences
//
//
//        fun getInstance(context: Context): MySharedPrefManager {
//            if (sharedPreferences == null) {
//                synchronized(MySharedPrefManager::class.java) {
//                    if (sharedPreferences == null) {
//                        sharedPreferences =
//                            context.getSharedPreferences(
//                                PrefsConstants.PREF_NAME,
//                                PrefsConstants.PREFERENCES_MODE
//                            )
//                    }
//                }
//            }
//            return sharePref
//        }
//    }
//
    fun putString(key: String, value: String) {
        editor.apply() {
            putString(key, value)
        }.apply()
    }

    fun getString(key: String): String {
        return sharedPrefManager.getString(key, "noValue")!!
    }


}

