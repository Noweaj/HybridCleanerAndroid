package com.noweaj.android.hybridcleanerandroid.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.noweaj.android.hybridcleanerandroid.R
import com.noweaj.android.hybridcleanerandroid.databinding.ActivityMainBinding
import com.noweaj.android.hybridcleanerandroid.test.BleDataPublishTest
import com.noweaj.android.hybridcleanerandroid.ui.component.BleDialog
import com.noweaj.android.hybridcleanerandroid.ui.core.BaseActivity
import com.noweaj.android.hybridcleanerandroid.util.InjectionUtil
import com.noweaj.android.hybridcleanerandroid.viewmodel.MainViewModel

class MainActivity : BaseActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private val viewModel: MainViewModel by viewModels{
        InjectionUtil.provideMainViewModelFactory(application)
    }
    lateinit var binding: ActivityMainBinding

    private var isBluetoothAvailable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()

        BleDataPublishTest()
    }

    override fun onBluetoothCheckDone(isBluetoothAvailable: Boolean) {
        observe()
        if(isBluetoothAvailable){
            this.isBluetoothAvailable = isBluetoothAvailable
        }
    }

    private fun initUi(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.tbMain)
        binding.mainViewModel = viewModel
    }

    private fun observe(){
        viewModel.navigateToURL.observe(this, { event ->
            Log.d(TAG, "navigateToURL")
            event.getContentIfNotHandled()?.let{
                val intentURL = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(intentURL)
            }
        })

        viewModel.snackbar.observe(this, { event ->
            Log.d(TAG, "snackbar")
            event.getContentIfNotHandled()?.let{
                Snackbar.make(binding.root, "$it", Snackbar.LENGTH_SHORT).show()
            }
        })

        viewModel.bleStatus.observe(this, {
            Log.d(TAG, "bleStatus: $it")
            if(isBluetoothAvailable) {
                when (it) {
                    -1 -> {
                    } // do nothing
                    0 -> showBleDialog()
                    else -> binding.ivMainBle.setImageResource(R.drawable.image_main_ble_on)
                }
            } else {
                Snackbar.make(
                    binding.root,
                    getString(R.string.text_snackbar_bluetoothnotavailable),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })
    }

    private var dialog: AlertDialog? = null

    private fun showBleDialog(){
        dialog = BleDialog(
            context = this,
            dialogCallback = object: BleDialog.BleDialogCallback{
                override fun onDialogFinished(deviceName: String?, deviceAddress: String?) {
                    Log.d(TAG, "$deviceName, $deviceAddress")
                    viewModel.setConnection(deviceName, deviceAddress)
                }
            }
        ).show()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        dialog?.let{ dialog!!.dismiss() }
    }

    override fun onStop() {
        super.onStop()

        viewModel.bleStatus.removeObservers(this)
        viewModel.navigateToURL.removeObservers(this)
        viewModel.snackbar.removeObservers(this)
    }
}