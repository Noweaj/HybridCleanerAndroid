package com.noweaj.android.hybridcleanerandroid.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.noweaj.android.hybridcleanerandroid.R
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Observer
import com.noweaj.android.hybridcleanerandroid.databinding.ActivityMainBinding
import com.noweaj.android.hybridcleanerandroid.databinding.ActivityMainBindingImpl
import com.noweaj.android.hybridcleanerandroid.ui.component.BleDialog
import com.noweaj.android.hybridcleanerandroid.util.InjectionUtil
import com.noweaj.android.hybridcleanerandroid.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private val viewModel: MainViewModel by viewModels {
        InjectionUtil.provideMainViewModelFactory(application = application)
    }

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        setUpUi()
        observe()

    }

    private fun setUpUi(){
        setSupportActionBar(binding.tbMain)
        binding.ivMainBle.setOnClickListener{
            viewModel.setBleStatus(0)
        }
    }

    private fun observe(){
        viewModel.bleStatus.observe(this, {
            Log.d(TAG, "it: $it")
            if(it < 0){
                // no connection
            } else if(it == 0){
                viewModel.showBleDialog(this)
            } else {
                // connection exists
                binding.ivMainBle.setImageResource(R.drawable.image_main_ble_on)
            }
        })
    }
}