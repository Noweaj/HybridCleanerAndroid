package com.noweaj.android.hybridcleanerandroid.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.noweaj.android.hybridcleanerandroid.R
import com.noweaj.android.hybridcleanerandroid.databinding.ActivityMainBinding
import com.noweaj.android.hybridcleanerandroid.ui.component.BleDialog
import com.noweaj.android.hybridcleanerandroid.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
        observe()
    }

    private fun initUi(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.tbMain)
        val viewModel = MainViewModel(application)
        binding.mainViewModel = viewModel
    }

    private fun observe(){
        binding.mainViewModel!!.bleStatus.observe(this, {
            Log.d(TAG, "bleStatus: $it")
            when(it){
                -1 -> {} // do nothing
                0 -> showBleDialog()
                else -> binding.ivMainBle.setImageResource(R.drawable.image_main_ble_on)
            }
        })

        binding.mainViewModel!!.navigateToURL.observe(this, { event ->
            Log.d(TAG, "navigateToURL")
            event.getContentIfNotHandled()?.let{
                val intentURL = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(intentURL)
            }
        })

        binding.mainViewModel!!.snackbar.observe(this, { event ->
            Log.d(TAG, "snackbar")
            event.getContentIfNotHandled()?.let{
                Snackbar.make(binding.root, "$it", Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    private var dialog: AlertDialog? = null

    private fun showBleDialog(){
        dialog = BleDialog(context = this, dialogCallback = object: BleDialog.BleDialogCallback{
            override fun onDialogFinished(deviceName: String?, deviceAddress: String?) {
                Log.d(TAG, "$deviceName, $deviceAddress")
                binding.mainViewModel!!.setConnection(deviceName, deviceAddress)
            }
        }).show()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        dialog?.let{dialog!!.dismiss()}
    }
}