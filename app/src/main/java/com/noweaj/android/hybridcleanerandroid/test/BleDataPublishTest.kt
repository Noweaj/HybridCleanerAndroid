package com.noweaj.android.hybridcleanerandroid.test

import com.noweaj.android.hybridcleanerandroid.ble.BleDataReceiver
import java.util.*

class BleDataPublishTest {

    init{
        var cnt = 0
        val publishSubject = BleDataReceiver.getInstance()
        val timerTask = object: TimerTask() {
            override fun run() {
                val data = "{\n" +
                        "  \"index\": $cnt,\n" +
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
                        "      \"Dock\": 1,\n" +
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
                publishSubject.onNext(data)
                cnt++
            }
        }
        val timer = Timer()
        timer.schedule(timerTask, 1000, 1000)
    }
}

//<HM7500> Bvolt:1099 V, ChCurr:12 mA, BTmp:24.7 C, B_stat:8, MotCurr:114 mA, BotSen:111(265, 357, 2230, 1990,), AmSen:2806, DOCK:1, ErrCode:0
//<HV7500> Bvolt:1572 V, ChCurr:18 mA, Btemp:25.6 C, Bstate:8, MotCurr:890 mA, MotWatt:13 W, Mode:2, Ctrl:0, ErrCode:0, BatChCycle:0, OpTime:90
