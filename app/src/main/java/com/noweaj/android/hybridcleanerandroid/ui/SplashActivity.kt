package com.noweaj.android.hybridcleanerandroid.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.noweaj.android.hybridcleanerandroid.R

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val r = Runnable {
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        }
        Handler().postDelayed(r, 2000L)
    }
}