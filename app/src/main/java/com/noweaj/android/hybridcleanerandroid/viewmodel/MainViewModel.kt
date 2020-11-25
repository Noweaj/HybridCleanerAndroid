package com.noweaj.android.hybridcleanerandroid.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.noweaj.android.hybridcleanerandroid.R
import com.noweaj.android.hybridcleanerandroid.data.SingleEvent
import com.noweaj.android.hybridcleanerandroid.test.BleDataPublishTest
import com.noweaj.android.hybridcleanerandroid.util.ErrorCheckUtil
import com.noweaj.android.hybridcleanerandroid.util.NullCheckUtil
import org.json.JSONObject

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val TAG = MainViewModel::class.java.simpleName
    private val appContext = getApplication<Application>()

    inline fun <A, B, R> ifNotNull(a: A?, b: B?, code: () -> R){
        if(a != null && b != null) code()
    }

    private val _navigateToURL = MutableLiveData<SingleEvent<String>>()
    val navigateToURL: LiveData<SingleEvent<String>>
        get() = _navigateToURL

    private val _openDrawer = MutableLiveData<Boolean>()
    val openDrawer: LiveData<Boolean>
        get() = _openDrawer

    private val _snackbar = MutableLiveData<SingleEvent<String>>()
    val snackbar: LiveData<SingleEvent<String>>
        get() = _snackbar

    private val _bleStatus = MutableLiveData<Int>()
    val bleStatus: LiveData<Int>
        get() = _bleStatus

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _bleDisconnected = MutableLiveData<SingleEvent<Boolean>>()
    val bleDisconnected: LiveData<SingleEvent<Boolean>>
        get() = _bleDisconnected

    private val sharedPreferences = appContext.getSharedPreferences(
        appContext.getString(R.string.preference_ble),
        Context.MODE_PRIVATE
    )
    private val editor = sharedPreferences.edit()


    init{
//        val autoConnectDeviceAddress = sharedPreferences.getString("DEVICE_ADDRESS", null)
//        Log.d(TAG, "autoConnectDeviceAddress: $autoConnectDeviceAddress")
//        if(autoConnectDeviceAddress != null)
//            setConnection("FineHybrid", autoConnectDeviceAddress)
//        else
            setBleStatus(0)

//        _openDrawer.value = false
    }

    private fun setBleStatus(value: Int){ _bleStatus.value = value }

    fun setConnection(deviceName: String?, deviceAddress: String?){
        NullCheckUtil.ifNotNull(deviceName, deviceAddress){ deviceName_, deviceAddress_ ->
            if(/*deviceName_ == "FineHybrid"*/ true) { // for test
                connectToDevice(deviceAddress_)
                editor.putString("DEVICE_ADDRESS", deviceAddress_)
                editor.apply()
                setBleStatus(1)
                return
            } else {
                _snackbar.value = SingleEvent(
                    appContext.getString(R.string.text_main_snackbar_invaliddevice)
                )
            }
        }
        setBleStatus(-1)
    }

    private fun connectToDevice(deviceAddress: String){
        // connect to device

        // test runner start
        val bleDataPublishTest = BleDataPublishTest.getInstance()
        bleDataPublishTest.start()
    }

    fun disconnectDevice(){
        // disconnect connection

        // test runner stop
        val bleDataPublishTest = BleDataPublishTest.getInstance()
        bleDataPublishTest.stop()
    }

    fun onDataReceived(data: String){
        Log.d(TAG, "onDataReceived")
        val status = JSONObject(data).getInt("status")
        if(status < 0)
            _bleDisconnected.postValue(SingleEvent(true))
        ErrorCheckUtil.getErrorMessage(data, getApplication())?.let {
            Log.d(TAG, "getErrorMessage $status")
            val stringBuilder = StringBuilder()
            for(i in it.indices){
                stringBuilder.append("${it.get(i)}\n")
            }
            Log.d(TAG, "getErrorMessage: ${stringBuilder.toString()}")
            if(stringBuilder.toString().trim().isNotEmpty())
                _errorMessage.postValue(stringBuilder.toString())
        }
    }

    fun onBleClicked(){
        setBleStatus(0)
    }

    fun onDrawerClicked(){
        Log.d(TAG, "onDrawerClicked")
        _openDrawer.value = true
    }

    fun onLogoClicked(){
        _navigateToURL.value = SingleEvent(appContext.getString(R.string.text_other_sample_url))
    }

    fun onDrawerItemClicked(v: View){
        Log.d(TAG, "onDrawerItemClicked: ${v.id}")
        when(v.id){
            R.id.tv_drawer_announcement -> {
                // new dialog for showing announcements from firebase
            }
            R.id.tv_drawer_appversion -> {
                // new dialog for showing app version
            }
            R.id.tv_drawer_usermanual -> {
                // redirect to open sensebot_manual_kr_ver1.pdf
            }
            R.id.tv_drawer_website -> {
                _navigateToURL.value = SingleEvent(appContext.getString(R.string.text_other_sample_url))
            }
            R.id.tv_drawer_productinfo -> {
                _navigateToURL.value = SingleEvent(appContext.getString(R.string.text_other_sample_url))
            }
            R.id.tv_drawer_personalinfo -> {
                // open DocDialog with txt_agree2_utf.txt
            }
            R.id.tv_drawer_useragreement -> {
                // open DocDialog with txt_agree1_utf.txt
            }
            else -> {
                // err
                _openDrawer.value = false
            }
        }
    }
}