package com.noweaj.android.hybridcleanerandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONObject

class BatteryViewModel: ViewModel() {

    private val TAG = BatteryViewModel::class.java.simpleName

    private val _batteryHandheld = MutableLiveData<Float>()
    val batteryHandheld : LiveData<Float>
        get() = _batteryHandheld

    private val _batteryBase = MutableLiveData<Float>()
    val batteryBase : LiveData<Float>
        get() = _batteryBase

    private val _batteryRobot = MutableLiveData<Float>()
    val batteryRobot : LiveData<Float>
        get() = _batteryRobot

    fun processData(data: String){
        val baseObject = JSONObject(data).getJSONArray("devices").getJSONObject(0)
        val handHeldObject = JSONObject(data).getJSONArray("devices").getJSONObject(1)

        val baseBattPerc = baseObject.getInt("BattPerc")
        _batteryBase.postValue(baseBattPerc.toFloat())
        if(handHeldObject != null){
            val handHeldBattPerc = handHeldObject.getInt("BattPerc")

            _batteryHandheld.postValue(handHeldBattPerc.toFloat())
            _batteryRobot.postValue(Math.min(baseBattPerc, handHeldBattPerc).toFloat())
        }
    }
}
