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

    private val _isAmbientDark = MutableLiveData<Boolean>()
    val isAmbientDark: LiveData<Boolean>
        get() = _isAmbientDark

    override fun onConnected(data: String) {
        val dataObject = JSONObject(data).getJSONArray("devices").getJSONObject(0)
        _isAmbientDark.postValue(dataObject.getInt("AmSen") > 3000)
    }

    override fun onDisconnected() {

    }
}