package com.noweaj.android.hybridcleanerandroid.ui.core

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.noweaj.android.hybridcleanerandroid.R
import com.noweaj.android.hybridcleanerandroid.ui.component.ErrorDialog

abstract class BaseActivity: AppCompatActivity() {

    private val TAG = BaseActivity::class.java.simpleName

    private val REQUEST_CODE_BLE = 1
    private val REQUEST_CODE_PERMISSION = 2

    abstract fun onBluetoothReady()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        checkBluetooth()
    }

    private fun checkBluetooth(){
        if(!packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)){
            showAlertDialog(
                cause = "BLE not supported",
                msg = getString(R.string.text_dialog_err_ble_notsupported)
            )
            return
        }

        val mBluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val mBluetoothAdapter = mBluetoothManager.adapter

        if(mBluetoothAdapter == null){
            showAlertDialog(
                cause = "Bluetooth not supported",
                msg = getString(R.string.text_dialog_err_bluetooth_notsupported)
            )
            return
        }

        if(!mBluetoothAdapter.isEnabled){
            val intentEnableBle = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intentEnableBle, REQUEST_CODE_BLE)
            return
        }

        // check permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(ACCESS_COARSE_LOCATION), REQUEST_CODE_PERMISSION)
                return
            }
        }

        onBluetoothReady()
    }

    private fun showAlertDialog(cause: String, msg: String){
        // show alert dialog
        ErrorDialog(this,
            object : ErrorDialog.ErrorDialogCallback {
                override fun onDialogFinished() {
                    checkBluetooth()
                }
            },
            object : ErrorDialog.ErrorDialogCallback {
                override fun onDialogFinished() {
                    finish()
                }
            }).show(cause, msg)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_CODE_BLE -> {
                if(resultCode == Activity.RESULT_CANCELED){
                    showAlertDialog(
                        cause = "Bluetooth request denied",
                        msg = getString(R.string.text_dialog_err_bluetooth_notenabled)
                    )
                    return
                }
            }
            else -> {

            }
        }
        checkBluetooth()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE_PERMISSION -> {
                if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    showAlertDialog(
                        cause = "${permissions[0]} not granted",
                        msg = getString(R.string.text_dialog_err_permission_notgranted)
                    )
                    return
                }
            }
            else -> {

            }
        }
        checkBluetooth()
    }
}