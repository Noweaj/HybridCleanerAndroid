package com.noweaj.android.hybridcleanerandroid.util

import android.content.Context
import com.noweaj.android.hybridcleanerandroid.R
import org.json.JSONObject

class ErrorCheckUtil {

    companion object{
        fun getErrorMessage(data: String, context: Context): List<String>?{
            val errorResult = arrayListOf<String>()

            val curStatus = JSONObject(data).getInt("status")
            if(curStatus < 0)
                return null

            val baseObject = JSONObject(data).getJSONArray("devices").getJSONObject(0)
            getErrorCodeMessage(baseObject.getInt("ErrCode"))?.let {
                errorResult.add(it)
            }
            val baseBattVolt = baseObject.getInt("BattVolt")
            if(baseBattVolt > 1200)
                errorResult.add("물걸레 청소기 ${context.getString(R.string.text_errorcode_battvolt)}")
            val baseBattTemp = baseObject.getDouble("BattTemp")
            if(baseBattTemp > 60)
                errorResult.add("물걸레 청소기 ${context.getString(R.string.text_errorcode_batttemp)}")
            val baseChgCurr = baseObject.getInt("ChgCurr")
            if(baseChgCurr > 100)
                errorResult.add("물걸레 청소기 ${context.getString(R.string.text_errorcode_chgcurr)}")
            val baseMotCurr = baseObject.getInt("MotCurr")
            if(baseMotCurr > 300)
                errorResult.add("물걸레 청소기 ${context.getString(R.string.text_errorcode_motcurr)}")

            if(JSONObject(data).getJSONArray("devices").length() > 1){
                val handHeldObject = JSONObject(data).getJSONArray("devices").getJSONObject(1)
                getErrorCodeMessage(handHeldObject.getInt("ErrCode"))?.let {
                    errorResult.add(it)
                }
                val handHeldBattVolt = handHeldObject.getInt("BattVolt")
                if(handHeldBattVolt > 1900)
                    errorResult.add("핸디 청소기 ${context.getString(R.string.text_errorcode_battvolt)}")
                val handHeldBattTemp = handHeldObject.getDouble("BattTemp")
                if(handHeldBattTemp > 60)
                    errorResult.add("핸디 청소기 ${context.getString(R.string.text_errorcode_batttemp)}")
                val handHeldChgCurr = handHeldObject.getInt("ChgCurr")
                if(handHeldChgCurr > 100)
                    errorResult.add("핸디 청소기 ${context.getString(R.string.text_errorcode_chgcurr)}")
                val handHeldMotCurr = handHeldObject.getInt("MotCurr")
                if(handHeldMotCurr > 1000)
                    errorResult.add("핸디 청소기 ${context.getString(R.string.text_errorcode_motcurr)}")
            }
            return errorResult
        }

        private fun getErrorCodeMessage(errCode: Int): String?{
            return when(errCode){
                0 -> null
                10 -> "모터 불량"
                20 -> "배터리 과열"
                21 -> "배터리 인식불가"
                30 -> "전원 어댑터 불량"
                else -> "알 수 없는 오류"
            }
        }
    }
}