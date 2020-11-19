package com.noweaj.android.hybridcleanerandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DiagnosisAmbientViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DiagnosisAmbientViewModel() as T
    }
}