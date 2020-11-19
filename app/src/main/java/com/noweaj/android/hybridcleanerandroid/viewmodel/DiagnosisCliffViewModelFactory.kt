package com.noweaj.android.hybridcleanerandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DiagnosisCliffViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DiagnosisCliffViewModel() as T
    }
}