package com.noweaj.android.hybridcleanerandroid.ui.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONObject

abstract class BaseViewModel: ViewModel() {

    abstract fun onConnected(data: String)
    abstract fun onDisconnected()

    private val _bluetoothConnection = MutableLiveData<Boolean>()
    val bluetoothConnection: LiveData<Boolean>
        get() = _bluetoothConnection

    fun processData(data: String){
        val status = JSONObject(data).getInt("status")
        if(status > 0){
            _bluetoothConnection.postValue(true)
            onConnected(data)
        } else {
            _bluetoothConnection.postValue(false)
            onDisconnected()
        }
    }
}