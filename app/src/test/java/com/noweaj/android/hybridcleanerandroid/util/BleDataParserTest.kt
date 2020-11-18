package com.noweaj.android.hybridcleanerandroid.util

import junit.framework.TestCase
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class BleDataParserTest : TestCase() {

    fun testGetTargetDataFromString() {
        val str = "{\n" +
                "  \"index\": 0,\n" +
                "  \"devices\": [\n" +
                "    {\n" +
                "      \"id\": \"HM7500\",\n" +
                "      \"BattVolt\": 1099,\n" +
                "      \"ChgCurr\": 12,\n" +
                "      \"BattTemp\": 24.7,\n" +
                "      \"BattStat\": 8,\n" +
                "      \"MotCurr\": 114,\n" +
                "      \"BotSen\": \"111(265, 357, 2230, 1990)\",\n" +
                "      \"AmSen\": 2806,\n" +
                "      \"Dock\": 1,\n" +
                "      \"ErrCode\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"HV7500\",\n" +
                "      \"BattVolt\": 1572,\n" +
                "      \"ChgCurr\": 18,\n" +
                "      \"BattTemp\": 25.6,\n" +
                "      \"BattState\": 8,\n" +
                "      \"MotCurr\": 890,\n" +
                "      \"MatWatt\": 13,\n" +
                "      \"Mode\": 2,\n" +
                "      \"Ctrl\": 0,\n" +
                "      \"ErrCode\": 0,\n" +
                "      \"BattChgCycle\": 0,\n" +
                "      \"OpTime\": 90\n" +
                "    }\n" +
                "  ]\n" +
                "}"

        val jsonObject = JSONObject(str)
        println(jsonObject.getInt("index"))
        try {
            val jsonArray = JSONObject(str).getJSONArray("devices")
            for (i in 0 until jsonArray.length()) {
                println(jsonArray.getJSONObject(i).getString("id"))
            }
        } catch (e: JSONException){
            e.printStackTrace()
        }
    }
}