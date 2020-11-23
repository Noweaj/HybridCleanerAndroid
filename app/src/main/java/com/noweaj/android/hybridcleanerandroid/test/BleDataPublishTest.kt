package com.noweaj.android.hybridcleanerandroid.test

import android.util.Log
import com.noweaj.android.hybridcleanerandroid.ble.BleDataReceiver
import java.lang.RuntimeException
import java.util.*

class BleDataPublishTest {

    private val TAG = BleDataPublishTest::class.java.simpleName

    companion object{
        @Volatile private var bleDataPublishTest: BleDataPublishTest? = null

        fun getInstance(): BleDataPublishTest{
            if(bleDataPublishTest == null){
                synchronized(BleDataPublishTest::class){
                    if(bleDataPublishTest == null){
                        bleDataPublishTest = BleDataPublishTest()
                    }
                }
            }
            return bleDataPublishTest!!
        }
    }

    init{
        if(bleDataPublishTest != null){
            throw RuntimeException("Use getInstance() method to get the single instance of this class")
        }
    }

    /**
     * cnt
     * 0 - 4:
     * base only
     * 5 - 9:
     * robot mode
     * 10 - 14:
     * robot mode different values
     * 15 - 19:
     * over voltage
     * 20 - 24:
     * over current
     * 25 - 29:
     * err code
     * 30 - 34:
     * detach handheld
     * 35 - 39
     * disconnected
     *
     */
    private fun getBleData(cnt: Int): String{
        var data: String
        when(cnt){
            in 0..4 ->{
                data = "{\n" +
                        "  \"status\": $cnt,\n" +
                        "  \"devices\": [\n" +
                        "    {\n" +
                        "      \"id\": \"HM7500\",\n" +
                        "      \"BattVolt\": 1099,\n" +
                        "      \"ChgCurr\": 12,\n" +
                        "      \"BattTemp\": 24.7,\n" +
                        "      \"BattPerc\": 42,\n" +
                        "      \"MotCurr\": 114,\n" +
                        "      \"BotSen\": \"265,357,2230,1990\",\n" +
                        "      \"AmSen\": 2806,\n" +
                        "      \"Dock\": 0,\n" +
                        "      \"OpMode\": $opMode,\n" +
                        "      \"ErrCode\": 0\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}"
            }
            in 5..9 ->{
                data = "{\n" +
                        "  \"status\": $cnt,\n" +
                        "  \"devices\": [\n" +
                        "    {\n" +
                        "      \"id\": \"HM7500\",\n" +
                        "      \"BattVolt\": 1099,\n" +
                        "      \"ChgCurr\": 12,\n" +
                        "      \"BattTemp\": 24.7,\n" +
                        "      \"BattPerc\": 42,\n" +
                        "      \"MotCurr\": 114,\n" +
                        "      \"BotSen\": \"265,357,2230,1990\",\n" +
                        "      \"AmSen\": 3200,\n" +
                        "      \"Dock\": 1,\n" +
                        "      \"OpMode\": $opMode,\n" +
                        "      \"ErrCode\": 0\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": \"HV7500\",\n" +
                        "      \"BattVolt\": 1572,\n" +
                        "      \"ChgCurr\": 18,\n" +
                        "      \"BattTemp\": 25.6,\n" +
                        "      \"BattPerc\": 85,\n" +
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
            }
            in 10..14 ->{
                data = "{\n" +
                        "  \"status\": $cnt,\n" +
                        "  \"devices\": [\n" +
                        "    {\n" +
                        "      \"id\": \"HM7500\",\n" +
                        "      \"BattVolt\": 1099,\n" +
                        "      \"ChgCurr\": 12,\n" +
                        "      \"BattTemp\": 24.7,\n" +
                        "      \"BattPerc\": 90,\n" +
                        "      \"MotCurr\": 114,\n" +
                        "      \"BotSen\": \"2650,3570,223,199\",\n" +
                        "      \"AmSen\": 3200,\n" +
                        "      \"Dock\": 1,\n" +
                        "      \"OpMode\": $opMode,\n" +
                        "      \"ErrCode\": 0\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": \"HV7500\",\n" +
                        "      \"BattVolt\": 1572,\n" +
                        "      \"ChgCurr\": 18,\n" +
                        "      \"BattTemp\": 25.6,\n" +
                        "      \"BattPerc\": 45,\n" +
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
            }
            in 15..19 ->{
                data = "{\n" +
                        "  \"status\": $cnt,\n" +
                        "  \"devices\": [\n" +
                        "    {\n" +
                        "      \"id\": \"HM7500\",\n" +
                        "      \"BattVolt\": 1399,\n" + // over voltage on >1200
                        "      \"ChgCurr\": 12,\n" +
                        "      \"BattTemp\": 24.7,\n" +
                        "      \"BattPerc\": 90,\n" +
                        "      \"MotCurr\": 114,\n" +
                        "      \"BotSen\": \"2650,3570,223,199\",\n" +
                        "      \"AmSen\": 3200,\n" +
                        "      \"Dock\": 1,\n" +
                        "      \"OpMode\": $opMode,\n" +
                        "      \"ErrCode\": 0\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": \"HV7500\",\n" +
                        "      \"BattVolt\": 1572,\n" +
                        "      \"ChgCurr\": 18,\n" +
                        "      \"BattTemp\": 25.6,\n" +
                        "      \"BattPerc\": 45,\n" +
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
            }
            in 20..24 -> {
                data = "{\n" +
                        "  \"status\": $cnt,\n" +
                        "  \"devices\": [\n" +
                        "    {\n" +
                        "      \"id\": \"HM7500\",\n" +
                        "      \"BattVolt\": 1099,\n" +
                        "      \"ChgCurr\": 12,\n" +
                        "      \"BattTemp\": 24.7,\n" +
                        "      \"BattPerc\": 90,\n" +
                        "      \"MotCurr\": 114,\n" +
                        "      \"BotSen\": \"2650,3570,223,199\",\n" +
                        "      \"AmSen\": 3200,\n" +
                        "      \"Dock\": 1,\n" +
                        "      \"OpMode\": $opMode,\n" +
                        "      \"ErrCode\": 0\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": \"HV7500\",\n" +
                        "      \"BattVolt\": 1572,\n" +
                        "      \"ChgCurr\": 30,\n" + // over current on >25 (2.5A)
                        "      \"BattTemp\": 25.6,\n" +
                        "      \"BattPerc\": 45,\n" +
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
            }
            in 25..29 -> {
                data = "{\n" +
                        "  \"status\": $cnt,\n" +
                        "  \"devices\": [\n" +
                        "    {\n" +
                        "      \"id\": \"HM7500\",\n" +
                        "      \"BattVolt\": 1099,\n" +
                        "      \"ChgCurr\": 12,\n" +
                        "      \"BattTemp\": 24.7,\n" +
                        "      \"BattPerc\": 90,\n" +
                        "      \"MotCurr\": 114,\n" +
                        "      \"BotSen\": \"2650,3570,223,199\",\n" +
                        "      \"AmSen\": 3200,\n" +
                        "      \"Dock\": 1,\n" +
                        "      \"OpMode\": $opMode,\n" +
                        "      \"ErrCode\": 0\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": \"HV7500\",\n" +
                        "      \"BattVolt\": 1572,\n" +
                        "      \"ChgCurr\": 18,\n" +
                        "      \"BattTemp\": 25.6,\n" +
                        "      \"BattPerc\": 45,\n" +
                        "      \"MotCurr\": 890,\n" +
                        "      \"MatWatt\": 13,\n" +
                        "      \"Mode\": 2,\n" +
                        "      \"Ctrl\": 0,\n" +
                        "      \"ErrCode\": 21,\n" +
                        "      \"BattChgCycle\": 0,\n" +
                        "      \"OpTime\": 90\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}"
            }
            in 30..34 -> {
                data = "{\n" +
                        "  \"status\": $cnt,\n" +
                        "  \"devices\": [\n" +
                        "    {\n" +
                        "      \"id\": \"HM7500\",\n" +
                        "      \"BattVolt\": 1099,\n" +
                        "      \"ChgCurr\": 12,\n" +
                        "      \"BattTemp\": 24.7,\n" +
                        "      \"BattPerc\": 42,\n" +
                        "      \"MotCurr\": 114,\n" +
                        "      \"BotSen\": \"265,357,2230,1990\",\n" +
                        "      \"AmSen\": 2806,\n" +
                        "      \"Dock\": 0,\n" +
                        "      \"OpMode\": $opMode,\n" +
                        "      \"ErrCode\": 0\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}"
            }
            in 35..39 -> {
                data = "{\n" +
                        "  \"status\": -1\n" +
                        "}"
            }
            else -> {
                // reset
                data = "{\n" +
                        "  \"status\": -1\n" +
                        "}"
                this.cnt = 0
            }
        }
        return data
    }

    val publishSubject = BleDataReceiver.getInstance()
    lateinit var timer: Timer
    var cnt: Int = 0
    var isRunning = false
    var opMode: Int = -1

    fun start(): Boolean{
        return if(!isRunning) {
            timer = Timer()
            cnt = 0
            val timerTask = object : TimerTask() {
                override fun run() {
                    val data = getBleData(cnt)
                    // send
//                    Log.d(TAG, "data:\n$data")
                    publishSubject.onNext(data)
                    cnt++
                }
            }
            timer.schedule(timerTask, 1000, 1000)
            isRunning = true
            true
        } else {
            false
        }
    }

    fun stop(): Boolean{
        return if(isRunning) {
            timer.cancel()
            isRunning = false
            true
        } else {
            false
        }
    }

    fun setOperMode(mode: Int){
        opMode = mode
    }
}

//<HM7500> Bvolt:1099 V, ChCurr:12 mA, BTmp:24.7 C, B_stat:8, MotCurr:114 mA, BotSen:111(265, 357, 2230, 1990,), AmSen:2806, DOCK:1, ErrCode:0
//<HV7500> Bvolt:1572 V, ChCurr:18 mA, Btemp:25.6 C, Bstate:8, MotCurr:890 mA, MotWatt:13 W, Mode:2, Ctrl:0, ErrCode:0, BatChCycle:0, OpTime:90
