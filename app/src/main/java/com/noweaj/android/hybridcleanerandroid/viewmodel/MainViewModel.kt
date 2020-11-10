package com.noweaj.android.hybridcleanerandroid.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.noweaj.android.hybridcleanerandroid.ui.component.BleDialog

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val TAG = MainViewModel::class.java.simpleName

    inline fun <A, B, R> ifNotNull(a: A?, b: B?, code: () -> R){
        if(a != null && b != null) code()
    }

    private val _bleStatus = MutableLiveData<Int>()

    val bleStatus: LiveData<Int>
        get() = _bleStatus

    init{ setBleStatus(0) }

    fun setBleStatus(value: Int){ _bleStatus.value = value }

    interface DialogCallback{
        fun onDialogCallback(deviceName: String?, deviceAddress: String?)
    }

    fun showBleDialog(activityContext: Context){
        BleDialog(context = activityContext, dialogCallback = object: DialogCallback{
            override fun onDialogCallback(deviceName: String?, deviceAddress: String?) {
                Log.d(TAG, "$deviceName / $deviceAddress")
                ifNotNull(deviceName, deviceAddress){
                    // connect to device
                    if(deviceName.equals("FineHybrid")) {
                        connectToDevice(deviceAddress!!)
                        return
                    }
                }
                setBleStatus(-1)
            }
        }).show()
    }

    private fun connectToDevice(deviceAddress: String){

        setBleStatus(1)
    }
}