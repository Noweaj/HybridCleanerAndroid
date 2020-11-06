package com.noweaj.android.hybridcleanerandroid.ble

import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.os.IBinder

class BluetoothLeService: Service() {

    private val TAG = BluetoothLeService::class.java.simpleName

    var mBluetoothManager: BluetoothManager? = null
    var mBluetoothAdapter: BluetoothAdapter? = null

    fun initialize(): Boolean {
        if(mBluetoothManager == null) {
            mBluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            if(mBluetoothManager == null){
                return false
            }
        }

        mBluetoothAdapter = mBluetoothManager!!.adapter
        if(mBluetoothAdapter == null) return false

        return true
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }
}