package com.noweaj.android.hybridcleanerandroid.util

import org.json.JSONObject

class BleDataParser {

    companion object{
        fun getTargetData(data: String, targetObject: String): Any {
            val dataObject = JSONObject(data)
            return dataObject
        }
    }

    fun getTargetDataFromString(data: String, targetObject: String): Any {
        val dataObject = JSONObject(data)
        return dataObject
    }
}