package com.noweaj.android.hybridcleanerandroid.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel

class DiagnosisAmbientViewModel(application: Application): AndroidViewModel(application) {

    fun onModeChange(){
        Log.d("Ambient", "onModeChange")
    }
}