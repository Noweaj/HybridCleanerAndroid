package com.noweaj.android.hybridcleanerandroid.ble

import android.app.Service
import android.bluetooth.*
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.lang.StringBuilder
import java.util.*

class BluetoothLeService: Service() {

    inline fun <A, B, R> ifNotNull(a: A?, b: B?, code: () -> R){
        if(a != null && b != null) code()
    }

    private val TAG = BluetoothLeService::class.java.simpleName

    companion object{
        val PACKAGE_NAME = "com.noweaj.android.hybridcleanerandroid"

        val STATE_DISCONNECTED = 0
        val STATE_CONNECTING = 1
        val STATE_CONNECTED = 2

        val ACTION_GATT_CONNECTED = "$PACKAGE_NAME.ACTION_GATT_CONNECTED"
        val ACTION_GATT_DISCONNECTED = "$PACKAGE_NAME.ACTION_GATT_DISCONNECTED"
        val ACTION_GATT_SERVICES_DISCOVERED = "$PACKAGE_NAME.ACTION_GATT_SERVICES_DISCOVERED"
        val ACTION_DATA_AVAILABLE = "$PACKAGE_NAME.ACTION_DATA_AVAILABLE"
        val EXTRA_DATA = "$PACKAGE_NAME.EXTRA_DATA"

        val UUID_HM_RX = UUID.fromString(GattAttributes.HM_RX)
        val UUID_HM_TX = UUID.fromString(GattAttributes.HM_TX)
        val UUID_PIN = UUID.fromString(GattAttributes.PIN_CODE)
    }

    var mBluetoothManager: BluetoothManager? = null
    var mBluetoothAdapter: BluetoothAdapter? = null
    var mBluetoothDeviceAddress: String? = null
    var mBluetoothGatt: BluetoothGatt? = null
    var mConnectionState = STATE_DISCONNECTED
    private val mBinder: IBinder = LocalBinder()

    private val mGattCallback = object: BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            var intentAction: String
            if(newState == BluetoothProfile.STATE_CONNECTED){
                intentAction = ACTION_GATT_CONNECTED
                mConnectionState = STATE_CONNECTED
                broadcastUpdate(intentAction)
                Log.d(TAG, "Connected to GATT server")
                Log.d(TAG, "Attempting to start service discovery: ${mBluetoothGatt!!.discoverServices()}")
            } else if(newState == BluetoothProfile.STATE_DISCONNECTED){
                intentAction = ACTION_GATT_DISCONNECTED
                mConnectionState = STATE_DISCONNECTED
                broadcastUpdate(intentAction)
                Log.d(TAG, "Disconnected from GATT server")
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            if(status == BluetoothGatt.GATT_SUCCESS) broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED)
            else Log.w(TAG, "onServicesDiscover received: $status")
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic?,
            status: Int
        ) {
            if(status == BluetoothGatt.GATT_SUCCESS) broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic!!)
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic?
        ) {
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic!!)
        }
    }

    private fun broadcastUpdate(action: String){
        sendBroadcast(Intent(action))
    }

    private fun broadcastUpdate(action: String, characteristic: BluetoothGattCharacteristic){
        val intent = Intent(action)
        val data = characteristic.value
        if(data != null && data.isNotEmpty()){
            val stringBuilder = StringBuilder(data.size)
            for(byteChar in data.indices){
                stringBuilder.append(String.format("%02X ", byteChar))
            }
            intent.putExtra(EXTRA_DATA, String.format("%s", String(data)))
            Log.d(TAG, String.format("%s", String(data)))
        }
        sendBroadcast(intent)
    }

    fun initialize(): Boolean {
        mBluetoothManager = mBluetoothManager ?: getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        mBluetoothManager ?.run {
            Log.d(TAG, "Unable to initialize BluetoothManager")
            return false
        }

        mBluetoothAdapter = mBluetoothManager!!.adapter
        mBluetoothAdapter ?.run {
            Log.d(TAG, "Unable to obtain a BluetoothAdapter")
            return false
        }

        return true
    }

    fun connect(address: String?): Boolean {
        ifNotNull(mBluetoothAdapter, address){
            Log.d(TAG, "BluetoothAdapter not initialized or unspecified address")
            return false
        }
        ifNotNull(mBluetoothDeviceAddress, mBluetoothGatt){
            if(mBluetoothDeviceAddress.equals(address)){
                Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection")
                return if(mBluetoothGatt!!.connect()){
                    mConnectionState = STATE_CONNECTING
                    true
                } else {
                    val device = mBluetoothAdapter!!.getRemoteDevice(address)
                    mBluetoothGatt = device.connectGatt(this, false, mGattCallback)
                    mBluetoothDeviceAddress = address
                    false
                }
            }
        }

        val device = mBluetoothAdapter!!.getRemoteDevice(address)
        device ?.run{
            Log.d(TAG, "Device not found - Unable to connect")
            return false
        }

        mBluetoothGatt = device.connectGatt(this, false, mGattCallback)
        Log.d(TAG, "Trying to create a new connection")
        mBluetoothDeviceAddress = address
        mConnectionState = STATE_CONNECTING
        return true
    }

    fun disconnect(){
        ifNotNull(mBluetoothAdapter, mBluetoothGatt){
            mBluetoothGatt!!.disconnect()
            return
        }
        Log.d(TAG, "BluetoothAdapter not initialized yet")
    }

    fun readCharacteristic(characteristic: BluetoothGattCharacteristic){
        ifNotNull(mBluetoothAdapter, mBluetoothGatt){
            mBluetoothGatt!!.readCharacteristic(characteristic)
            return
        }
        Log.d(TAG, "BluetoothAdapter not initialized")
    }

    fun writeCharacteristic(characteristic: BluetoothGattCharacteristic){
        ifNotNull(mBluetoothAdapter, mBluetoothGatt){
            mBluetoothGatt!!.writeCharacteristic(characteristic)
            return
        }
        Log.d(TAG, "BluetoothAdapter not initialized")
    }

    fun setCharacteristicNotification(characteristic: BluetoothGattCharacteristic, enabled: Boolean){
        ifNotNull(mBluetoothAdapter, mBluetoothGatt){
            mBluetoothGatt!!.setCharacteristicNotification(characteristic, enabled)
            if(UUID_HM_RX == characteristic.uuid){
                var descriptor = characteristic.getDescriptor(UUID.fromString(GattAttributes.CLIENT_CHARACTERISTIC_CONFIG))
                descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                mBluetoothGatt!!.writeDescriptor(descriptor)
            }
            return
        }
        Log.d(TAG, "BluetoothAdapter not initialized")
    }

    fun getSupportedGattServices(): List<BluetoothGattService>?{
        mBluetoothGatt ?: return null
        return mBluetoothGatt!!.services
    }

    override fun onBind(p0: Intent?): IBinder? {
        return mBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        close()
        return super.onUnbind(intent)
    }

    fun close(){
        mBluetoothGatt ?: return
        mBluetoothGatt!!.close()
        mBluetoothGatt = null
    }

    class LocalBinder: Binder() {
        fun getService(): BluetoothLeService {
            return BluetoothLeService()
        }
    }
}