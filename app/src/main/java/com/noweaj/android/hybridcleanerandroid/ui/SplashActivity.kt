package com.noweaj.android.hybridcleanerandroid.ui

import android.Manifest.permission
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
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

class SplashActivity: AppCompatActivity() {

    private val requestCode_Ble = 1
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
    }

    override fun onResume() {
        super.onResume()
        checkBluetooth()
    }

    private fun checkBluetooth() {
        if(!packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
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
            val intentBle = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intentBle, requestCode_Ble)
            return
        }

        // check permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION), requestCode_perm)
                return
            }
        }

        navigateToMainActivity()
    }

    private fun showAlertDialog(cause: String, msg: String){
        // show alert dialog
        ErrorDialog(this, object : ErrorDialog.ErrorDialogCallback {
            override fun onDialogFinished() {
                finish()
            }
        }).show(cause, msg)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == requestCode_Ble) {
            if (resultCode == Activity.RESULT_CANCELED) {
                showAlertDialog(
                    cause = "Bluetooth not enabled",
                    msg = getString(R.string.text_dialog_err_bluetooth_notenabled)
                )
                return
            }
        } else {
            if(resultCode == Activity.RESULT_CANCELED){
                showAlertDialog(
                    cause = "Permission not granted",
                    msg = ""
                )
                return
            }
        }
        checkBluetooth()
    }

    private fun navigateToMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
        finish()
    }
}