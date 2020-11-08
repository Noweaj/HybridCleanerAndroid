package com.noweaj.android.hybridcleanerandroid.util

import com.noweaj.android.hybridcleanerandroid.viewmodel.BatteryViewModelFactory
import com.noweaj.android.hybridcleanerandroid.viewmodel.MainViewModelFactory
import com.noweaj.android.hybridcleanerandroid.viewmodel.RemoteViewModelFactory

object InjectionUtil {

    fun provideMainViewModelFactory(): MainViewModelFactory {
        return MainViewModelFactory()
    }

    fun provideRemoteViewModelFactory(): RemoteViewModelFactory {
        return RemoteViewModelFactory()
    }

    fun provideDiagnosisAmbientViewModelFactory(){

    }

    fun provideDiagnosisCliffViewModelFactory(){

    }

    fun provideBatteryViewModelFactory(): BatteryViewModelFactory {
        return BatteryViewModelFactory()
    }

    fun provideInquiryViewModelFactory(){

    }

    fun provideResponseViewModelFactory(){

    }
}