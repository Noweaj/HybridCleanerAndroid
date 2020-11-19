package com.noweaj.android.hybridcleanerandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.noweaj.android.hybridcleanerandroid.ui.core.BaseViewModel
import org.json.JSONObject

class RemoteViewModel: BaseViewModel() {

    // operatingMode:
    // 1 -> auto
    // 2 -> manual
    private val _operatingMode = MutableLiveData<Int>()
    val operatingMode: LiveData<Int>
        get() = _operatingMode

    override fun onConnected(data: String) {
        val baseObject = JSONObject(data).getJSONArray("devices").getJSONObject(0)
        val opMode = baseObject.getInt("OpMode")
        _operatingMode.postValue(opMode)
    }

    override fun onDisconnected() {

    }
}