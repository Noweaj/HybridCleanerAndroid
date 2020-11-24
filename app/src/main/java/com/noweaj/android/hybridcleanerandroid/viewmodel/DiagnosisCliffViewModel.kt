package com.noweaj.android.hybridcleanerandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.noweaj.android.hybridcleanerandroid.ui.core.BaseFragmentViewModel
import org.json.JSONObject

class DiagnosisCliffViewModel: BaseFragmentViewModel() {

    private val TAG = DiagnosisCliffViewModel::class.java.simpleName

    private val _disconnected = MutableLiveData<Boolean>()
    val disconnected: LiveData<Boolean>
        get() = _disconnected

    private val _cliffSensorData = MutableLiveData<Array<Boolean>>()
    val cliffSensorData: LiveData<Array<Boolean>>
        get() = _cliffSensorData

    override fun onConnected(data: String) {
        val dataObject = JSONObject(data).getJSONArray("devices").getJSONObject(0)
        val cliffSensorValue = dataObject.getString("BotSen")

        val tokens = cliffSensorValue.split(",")
        _cliffSensorData.postValue(
            arrayOf(
                Integer.parseInt(tokens[0]) > 1500,
                Integer.parseInt(tokens[1]) > 1500,
                Integer.parseInt(tokens[2]) > 1500,
                Integer.parseInt(tokens[3]) > 1500
            )
        )
    }

    override fun onDisconnected() {
        _disconnected.postValue(true)
    }
}