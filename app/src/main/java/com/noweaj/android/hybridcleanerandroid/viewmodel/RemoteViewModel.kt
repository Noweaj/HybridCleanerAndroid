package com.noweaj.android.hybridcleanerandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.noweaj.android.hybridcleanerandroid.data.SingleEvent
import com.noweaj.android.hybridcleanerandroid.ui.core.BaseViewModel
import org.json.JSONObject

class RemoteViewModel: BaseViewModel() {

    // mode
    // -1 -> auto
    // 0 -> stop
    // 1 -> forward
    // 2 -> backward
    private val _setOperatingMode = MutableLiveData<Int>()
    val setOperatingMode: LiveData<Int>
        get() = _setOperatingMode

    override fun onConnected(data: String) {
        val baseObject = JSONObject(data).getJSONArray("devices").getJSONObject(0)
        val opMode = baseObject.getInt("OpMode")
        _setOperatingMode.postValue(opMode)
    }

    override fun onDisconnected() {

    }

    fun onAutoModeButtonPressed(){
//        _setOperatingMode.postValue(-1)
        // send ble signal
    }

    fun onForwardButtonPressed(){
//        _setOperatingMode.postValue(1)
        // send ble signal
    }

    fun onStopButtonPressed(){
//        _setOperatingMode.postValue(0)
        // send ble signal
    }

    fun onBackwardButtonPressed(){
//        _setOperatingMode.postValue(2)
        // send ble signal
    }
}