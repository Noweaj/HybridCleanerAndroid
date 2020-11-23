package com.noweaj.android.hybridcleanerandroid.ble

import com.noweaj.android.hybridcleanerandroid.test.BleDataPublishTest
import java.lang.RuntimeException

class BleDataTransmitter {

    companion object{
        @Volatile private var bleDataTransmitter: BleDataTransmitter? = null

        fun getInstance(): BleDataTransmitter{
            if(bleDataTransmitter == null){
                synchronized(BleDataTransmitter::class){
                    if(bleDataTransmitter == null){
                        bleDataTransmitter = BleDataTransmitter()
                    }
                }
            }
            return bleDataTransmitter!!
        }
    }

    init {
        if(bleDataTransmitter != null){
            throw RuntimeException("Use getInstance() method to get the single instance of this class")
        }
    }

    fun sendOperMode(data: Int){
        val bleDataPublishTest = BleDataPublishTest.getInstance()
        bleDataPublishTest.setOperMode(data)
    }
}