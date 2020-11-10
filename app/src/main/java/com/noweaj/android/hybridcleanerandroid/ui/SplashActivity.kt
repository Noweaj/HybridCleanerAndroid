package com.noweaj.android.hybridcleanerandroid.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.noweaj.android.hybridcleanerandroid.R

class SplashActivity: AppCompatActivity() {

    inline fun <A, B, R> ifNotNull(a: A?, b: B?, code: (A, B) -> R){
        if(a != null && b != null) code(a, b)
    }

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val r = Runnable {
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
            finish()
        }
        Handler().postDelayed(r, 2000L)
    }

    private fun findBluetoothConnection(){
        sharedPreferences = this.getSharedPreferences(getString(R.string.preference_ble), MODE_PRIVATE)

        val deviceName = sharedPreferences.getString("DEVICE_NAME", null)
        val deviceAddress = sharedPreferences.getString("DEVICE_ADDRESS", null)

        ifNotNull(deviceName, deviceAddress){ deviceName, deviceAddress ->

        }


    }
}