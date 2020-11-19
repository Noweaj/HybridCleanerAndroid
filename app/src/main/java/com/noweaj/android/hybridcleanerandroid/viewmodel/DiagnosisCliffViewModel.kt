package com.noweaj.android.hybridcleanerandroid.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.noweaj.android.hybridcleanerandroid.data.CliffSensorData
import com.noweaj.android.hybridcleanerandroid.ui.core.BaseViewModel
import org.json.JSONObject

class DiagnosisCliffViewModel: BaseViewModel() {

    private val TAG = DiagnosisCliffViewModel::class.java.simpleName

    private val _cliffSensorData = MutableLiveData<CliffSensorData>()
    val cliffSensorData: LiveData<CliffSensorData>
        get() = _cliffSensorData

    override fun onConnected(data: String) {
        val dataObject = JSONObject(data).getJSONArray("devices").getJSONObject(0)
        val cliffSensorValue = dataObject.getString("BotSen")

        val tokens = cliffSensorValue.split(",")
        _cliffSensorData.postValue(
            CliffSensorData(
                topLeft = Integer.parseInt(tokens[0]) > 1500,
                topRight = Integer.parseInt(tokens[1]) > 1500,
                botLeft = Integer.parseInt(tokens[2]) > 1500,
                botRight = Integer.parseInt(tokens[3]) > 1500
            )
        )
    }

    override fun onDisconnected() {

    }
}