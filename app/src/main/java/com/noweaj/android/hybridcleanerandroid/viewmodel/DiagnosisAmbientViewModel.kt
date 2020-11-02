package com.noweaj.android.hybridcleanerandroid.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar

class DiagnosisAmbientViewModel: ViewModel() {

    fun onModeChange(){
        Log.d("Ambient", "onModeChange")
    }
}