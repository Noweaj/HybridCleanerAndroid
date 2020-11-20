package com.noweaj.android.hybridcleanerandroid.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.noweaj.android.hybridcleanerandroid.ui.core.BaseViewModel
import org.json.JSONObject

class DiagnosisAmbientViewModel: BaseViewModel() {

    private val TAG = DiagnosisAmbientViewModel::class.java.simpleName

    private val _disconnected = MutableLiveData<Boolean>()
    val disconnected: LiveData<Boolean>
        get() = _disconnected

    private val _isBright = MutableLiveData<Boolean>()
    val isBright: LiveData<Boolean>
        get() = _isBright

    override fun onConnected(data: String) {
        val dataObject = JSONObject(data).getJSONArray("devices").getJSONObject(0)
        _isBright.postValue(dataObject.getInt("AmSen") > 3000)
    }

    override fun onDisconnected() {
        _disconnected.postValue(true)
    }
}