package com.noweaj.android.hybridcleanerandroid.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.noweaj.android.hybridcleanerandroid.R
import com.noweaj.android.hybridcleanerandroid.data.SingleEvent
import com.noweaj.android.hybridcleanerandroid.util.NullCheckUtil

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val TAG = MainViewModel::class.java.simpleName

    inline fun <A, B, R> ifNotNull(a: A?, b: B?, code: () -> R){
        if(a != null && b != null) code()
    }

    private val _navigateToURL = MutableLiveData<SingleEvent<String>>()
    val navigateToURL: LiveData<SingleEvent<String>>
        get() = _navigateToURL

    private val _snackbar = MutableLiveData<SingleEvent<String>>()
    val snackbar: LiveData<SingleEvent<String>>
        get() = _snackbar

    private val _bleStatus = MutableLiveData<Int>()
    val bleStatus: LiveData<Int>
        get() = _bleStatus

    private val sharedPreferences = getApplication<Application>().getSharedPreferences(
        getApplication<Application>().getString(R.string.preference_ble),
        Context.MODE_PRIVATE
    )
    private val editor = sharedPreferences.edit()

    init{
        val autoConnectDeviceAddress = sharedPreferences.getString("DEVICE_ADDRESS", null)
        Log.d(TAG, "autoConnectDeviceAddress: $autoConnectDeviceAddress")
        if(autoConnectDeviceAddress != null)
            setConnection("FineHybrid", autoConnectDeviceAddress)
        else
            setBleStatus(0)
    }

    private fun setBleStatus(value: Int){ _bleStatus.value = value }

    fun setConnection(deviceName: String?, deviceAddress: String?){
        NullCheckUtil.ifNotNull(deviceName, deviceAddress){ deviceName_, deviceAddress_ ->
            if(deviceName_ == "FineHybrid") {
                connectToDevice(deviceAddress_)
                editor.putString("DEVICE_ADDRESS", deviceAddress_)
                editor.apply()
                setBleStatus(1)
                return
            } else {
                _snackbar.value = SingleEvent("연결에 실패하였습니다 - 잘못된 기기 연결 시도")
            }
        }
        setBleStatus(-1)
    }

    private fun connectToDevice(deviceAddress: String){
        // connect to device
    }

    fun onBleClicked(){
        setBleStatus(0)
    }

    fun onDrawerClicked(){
        Log.d(TAG, "onDrawerClicked")
    }

    fun onLogoClicked(){
        _navigateToURL.value = SingleEvent("http://google.com")
    }
}