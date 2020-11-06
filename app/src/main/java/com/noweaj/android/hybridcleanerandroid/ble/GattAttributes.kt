package com.noweaj.android.hybridcleanerandroid.ble

class GattAttributes {

    companion object{
        val CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb"
        val HM_10_CONF = "0003cdd0-0000-1000-8000-00805f9b0131"
        val HM_RX = "0003cdd1-0000-1000-8000-00805f9b0131"
        val HM_TX = "0003cdd2-0000-1000-8000-00805f9b0131"
        val PIN_SERVICE = "0003cde0-0000-1000-8000-00805f9b0131"
        val PIN_CODE = "0003cde1-0000-1000-8000-00805f9b0131"

        private val attributes: MutableMap<String, String> = HashMap()

        init{
            // Sample Services.
            attributes.put(HM_10_CONF, "HM 10 Serial");
            attributes.put("00001800-0000-1000-8000-00805f9b34fb", "Device Information Service");
            attributes.put(PIN_SERVICE, "PIN SERVICE");

            // Sample Characteristics.
            attributes.put(HM_RX, "RX data");
            attributes.put(HM_TX, "TX data");
            attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");
            attributes.put(PIN_CODE, "PIN code");
        }

        fun lookup(uuid: String, defaultName: String): String{
            return attributes[uuid] ?: defaultName
        }
    }
}