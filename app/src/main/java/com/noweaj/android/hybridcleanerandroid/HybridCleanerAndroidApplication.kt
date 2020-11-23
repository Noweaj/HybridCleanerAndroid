package com.noweaj.android.hybridcleanerandroid

import android.app.Application
import android.util.DisplayMetrics
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader

class HybridCleanerAndroidApplication: Application() {

    private val TAG = HybridCleanerAndroidApplication::class.java.simpleName

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "resources.configuration")
        val config = resources.configuration
        Log.d(TAG, "smallestScreenWidthDp:${config.smallestScreenWidthDp}")
        Log.d(TAG, "screenWidthDp: ${config.screenWidthDp}")
        Log.d(TAG, "screenHeightDp: ${config.screenHeightDp}")
        Log.d(TAG, "densityDpi: ${config.densityDpi}")

        Log.d(TAG, "resources.displayMetrics")
        val dm = resources.displayMetrics
        Log.d(TAG, "xdpi: ${dm.xdpi}")
        Log.d(TAG, "ydpi: ${dm.ydpi}")
        Log.d(TAG, "densityDpi: ${dm.densityDpi}")
        Log.d(TAG, "scaledDensity: ${dm.scaledDensity}")
        Log.d(TAG, "widthPixels: ${dm.widthPixels}")
        Log.d(TAG, "heightPixels: ${dm.heightPixels}")
        Log.d(TAG, "widthPixels/density: ${dm.widthPixels/dm.density}")
    }
}