package com.noweaj.android.hybridcleanerandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.noweaj.android.hybridcleanerandroid.ble.BleDataTransmitter
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
        // send ble signal
        val bleDataTransmitter = BleDataTransmitter.getInstance()
        bleDataTransmitter.sendOperMode(-1)
    }

    fun onForwardButtonPressed(){
        // send ble signal
        val bleDataTransmitter = BleDataTransmitter.getInstance()
        bleDataTransmitter.sendOperMode(1)
    }

    fun onStopButtonPressed(){
        // send ble signal
        val bleDataTransmitter = BleDataTransmitter.getInstance()
        bleDataTransmitter.sendOperMode(0)
    }

    fun onBackwardButtonPressed(){
        // send ble signal
        val bleDataTransmitter = BleDataTransmitter.getInstance()
        bleDataTransmitter.sendOperMode(2)
    }
}