package com.noweaj.android.hybridcleanerandroid.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.noweaj.android.hybridcleanerandroid.R
import androidx.databinding.DataBindingUtil.setContentView
import com.noweaj.android.hybridcleanerandroid.databinding.ActivityMainBinding
import com.noweaj.android.hybridcleanerandroid.databinding.ActivityMainBindingImpl
import com.noweaj.android.hybridcleanerandroid.ui.component.BleDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        
//        BleDialog(this).show()
    }
}