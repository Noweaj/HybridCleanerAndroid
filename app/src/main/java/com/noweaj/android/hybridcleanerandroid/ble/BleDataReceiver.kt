package com.noweaj.android.hybridcleanerandroid.ble

import io.reactivex.subjects.PublishSubject
import java.lang.RuntimeException

class BleDataReceiver private constructor() {

    companion object{
        @Volatile private var publishSubject: PublishSubject<String>? = null

        fun getInstance(): PublishSubject<String> {
            if(publishSubject == null){
                synchronized(BleDataReceiver::class){
                    if(publishSubject == null){
                        publishSubject = PublishSubject.create()
                    }
                }
            }
            return publishSubject!!
        }
    }

    init {
        if(publishSubject != null){
            throw RuntimeException("Use getInstance() method to get the single instance of this class")
        }
    }
}