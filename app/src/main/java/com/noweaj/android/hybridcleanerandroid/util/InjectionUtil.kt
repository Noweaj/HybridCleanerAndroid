package com.noweaj.android.hybridcleanerandroid.util

import android.app.Application
import android.util.Log
import com.noweaj.android.hybridcleanerandroid.viewmodel.*

object InjectionUtil {

    fun provideMainViewModelFactory(application: Application): MainViewModelFactory {
        return MainViewModelFactory(application = application)
    }

    fun provideRemoteViewModelFactory(): RemoteViewModelFactory {
        return RemoteViewModelFactory()
    }

    fun provideDiagnosisAmbientViewModelFactory(): DiagnosisAmbientViewModelFactory {
        return DiagnosisAmbientViewModelFactory()
    }

    fun provideDiagnosisCliffViewModelFactory(): DiagnosisCliffViewModelFactory {
        return DiagnosisCliffViewModelFactory()
    }

    fun provideBatteryViewModelFactory(): BatteryViewModelFactory {
        return BatteryViewModelFactory()
    }

    fun provideInquiryViewModelFactory(){

    }

    fun provideResponseViewModelFactory(){

    }
}