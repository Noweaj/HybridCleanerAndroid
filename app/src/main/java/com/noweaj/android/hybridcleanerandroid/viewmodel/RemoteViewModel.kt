package com.noweaj.android.hybridcleanerandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.noweaj.android.hybridcleanerandroid.data.SingleEvent
import com.noweaj.android.hybridcleanerandroid.ui.core.BaseViewModel
import org.json.JSONObject

class RemoteViewModel: BaseViewModel() {

    // operatingMode:
    // 1 -> auto
    // 2 -> manual
    private val _operatingMode = MutableLiveData<Int>()
    val operatingMode: LiveData<Int>
        get() = _operatingMode

    private val _manualForward = MutableLiveData<SingleEvent<Unit>>()
    val manualForward: LiveData<SingleEvent<Unit>>
        get() = _manualForward

    private val _manualStop = MutableLiveData<SingleEvent<Unit>>()
    val manualStop: LiveData<SingleEvent<Unit>>
        get() = _manualStop

    private val _manualBackward = MutableLiveData<SingleEvent<Unit>>()
    val manualBackward: LiveData<SingleEvent<Unit>>
        get() = _manualBackward

    override fun onConnected(data: String) {
        val baseObject = JSONObject(data).getJSONArray("devices").getJSONObject(0)
        val opMode = baseObject.getInt("OpMode")
        _operatingMode.postValue(opMode)
    }

    override fun onDisconnected() {

    }

    fun onAutoModeButtonPressed(){
        _operatingMode.postValue(1)
    }

    fun onForwardButtonPressed(){
        _operatingMode.postValue(2)
        _manualForward.postValue(null)
    }

    fun onStopButtonPressed(){
        _operatingMode.postValue(2)
        _manualStop.postValue(null)
    }

    fun onBackwardButtonPressed(){
        _operatingMode.postValue(2)
        _manualStop.postValue(null)
    }
}