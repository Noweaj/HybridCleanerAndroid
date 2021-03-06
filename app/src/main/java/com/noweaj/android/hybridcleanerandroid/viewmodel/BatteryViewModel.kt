package com.noweaj.android.hybridcleanerandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.noweaj.android.hybridcleanerandroid.ui.core.BaseFragmentViewModel
import org.json.JSONObject

class BatteryViewModel: BaseFragmentViewModel() {

    private val TAG = BatteryViewModel::class.java.simpleName

    private val _batteryHandheld = MutableLiveData<Float>()
    val batteryHandheld: LiveData<Float>
        get() = _batteryHandheld

    private val _batteryBase = MutableLiveData<Float>()
    val batteryBase: LiveData<Float>
        get() = _batteryBase

    private val _batteryRobot = MutableLiveData<Float>()
    val batteryRobot: LiveData<Float>
        get() = _batteryRobot

    private val _handheldDetached = MutableLiveData<Float>()
    val handheldDetached: LiveData<Float>
        get() = _handheldDetached

    override fun onConnected(data: String) {
        val baseObject = JSONObject(data).getJSONArray("devices").getJSONObject(0)
        val baseBattPerc = baseObject.getInt("BattPerc")
        _batteryBase.postValue(baseBattPerc.toFloat())
        if(JSONObject(data).getJSONArray("devices").length() > 1){
            val handHeldObject = JSONObject(data).getJSONArray("devices").getJSONObject(1)
            val handHeldBattPerc = handHeldObject.getInt("BattPerc")

            _batteryHandheld.postValue(handHeldBattPerc.toFloat())
            _batteryRobot.postValue(Math.min(baseBattPerc, handHeldBattPerc).toFloat())
        } else {
            _handheldDetached.postValue(0.toFloat())
        }
    }

    override fun onDisconnected() {

    }
}
