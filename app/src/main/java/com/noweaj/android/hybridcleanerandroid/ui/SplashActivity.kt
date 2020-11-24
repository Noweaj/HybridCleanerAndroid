package com.noweaj.android.hybridcleanerandroid.ui

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import com.noweaj.android.hybridcleanerandroid.R
import com.noweaj.android.hybridcleanerandroid.ui.core.BaseActivity

class SplashActivity: BaseActivity() {

    private val TAG = SplashActivity::class.java.simpleName

    private val requestCode_ble = 1
    private val requestCode_perm = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        val r = Runnable {
//            val intent = Intent(this, MainActivity::class.java)
//            this.startActivity(intent)
//            finish()
//        }
//        Handler().postDelayed(r, 2000L)

        showInfo()
    }

    override fun onBluetoothCheckDone(isBluetoothAvailable: Boolean) {
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
        finish()
    }

    private fun showInfo(){
        var displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        Log.d(TAG, "display dpi: ${displayMetrics.densityDpi}")
    }
}