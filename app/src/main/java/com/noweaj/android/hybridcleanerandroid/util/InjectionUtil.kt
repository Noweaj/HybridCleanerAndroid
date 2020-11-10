package com.noweaj.android.hybridcleanerandroid.util

import android.app.Application
import android.util.Log
import com.noweaj.android.hybridcleanerandroid.viewmodel.BatteryViewModelFactory
import com.noweaj.android.hybridcleanerandroid.viewmodel.MainViewModelFactory
import com.noweaj.android.hybridcleanerandroid.viewmodel.RemoteViewModelFactory

object InjectionUtil {

    fun provideMainViewModelFactory(application: Application): MainViewModelFactory {
        return MainViewModelFactory(application = application)
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