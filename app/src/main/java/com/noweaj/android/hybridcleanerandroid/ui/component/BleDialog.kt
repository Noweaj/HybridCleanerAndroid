package com.noweaj.android.hybridcleanerandroid.ui.component

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.noweaj.android.hybridcleanerandroid.R
import kotlinx.android.synthetic.main.dialog_ble.view.*

class BleDialog(val context: Context, private val dialogCallback: BleDialogCallback){

    private val TAG = BleDialog::class.java.simpleName

    interface BleDialogCallback{
        fun onDialogFinished(deviceName: String?, deviceAddress: String?)
    }

    private val builder: AlertDialog.Builder by lazy {
        AlertDialog.Builder(context).setView(view)
    }

    private val view: View by lazy {
        View.inflate(context, R.layout.dialog_ble, null)
    }

    private var dialog: AlertDialog? = null

    fun create() {
        dialog = builder.create()
    }

    lateinit var mListViewAdapter: ListViewAdapter
    lateinit var mBluetoothAdapter: BluetoothAdapter
    lateinit var mBluetoothLeScanner: BluetoothLeScanner
    lateinit var mHandler: Handler
    var mScanning = false

    fun show(): AlertDialog{
        dialog = builder.create()

        mHandler = Handler()

        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        mBluetoothAdapter = bluetoothManager.adapter

        mBluetoothLeScanner = mBluetoothAdapter.bluetoothLeScanner

        view.ll_dialog_ble_button.setOnClickListener{
            finish(null, null)
        }

        view.iv_dialog_ble_rescan.setOnClickListener{
            mListViewAdapter.clear()
            mListViewAdapter.notifyDataSetChanged()
            scanLeDevice(true)
        }

        view.lv_dialog_ble_devices.onItemClickListener = onItemClickListener

        mListViewAdapter = ListViewAdapter()
        view.lv_dialog_ble_devices.adapter = mListViewAdapter

        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setCancelable(false)
        dialog?.show()

        scanLeDevice(true)
        return dialog as AlertDialog
    }

    val onItemClickListener = AdapterView.OnItemClickListener{ parent, view, pos, id ->
//        mBluetoothAdapter.stopLeScan(leScanCallback)
        mBluetoothLeScanner.stopScan(bleScannerCallback)
        val selectedDevice = parent.getItemAtPosition(pos) as BluetoothDevice
        var unknownDevice = selectedDevice.name
        unknownDevice = unknownDevice ?: "Unknown Device"
        finish(unknownDevice, selectedDevice.address)
    }

    private val leScanCallback = BluetoothAdapter.LeScanCallback{ device, rssi, scanRecord ->
        Log.d(TAG, "${device.name} ${device.address}")
        mListViewAdapter.addDevice(device)
        mListViewAdapter.notifyDataSetChanged()
    }

    private val bleScannerCallback = object: ScanCallback(){
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            mListViewAdapter.addDevice(result!!.device)
            mListViewAdapter.notifyDataSetChanged()
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            super.onBatchScanResults(results)
        }
    }

    private fun scanLeDevice(enable: Boolean){
        Log.d(TAG, "scanLeDevice: $enable")
        if(enable){
            mHandler.postDelayed({
                scanLeDevice(false)
            }, 5000)

            mScanning = enable
//            mBluetoothAdapter.startLeScan(leScanCallback)
            mBluetoothLeScanner.startScan(bleScannerCallback)
            view.pb_dialog_ble_scanning.visibility = View.VISIBLE
            view.iv_dialog_ble_rescan.visibility = View.INVISIBLE
        } else {
            mScanning = enable
//            mBluetoothAdapter.stopLeScan(leScanCallback)
            mBluetoothLeScanner.stopScan(bleScannerCallback)
            view.pb_dialog_ble_scanning.visibility = View.INVISIBLE
            view.iv_dialog_ble_rescan.visibility = View.VISIBLE
        }
    }

    fun dismiss() {
        scanLeDevice(false)
        mListViewAdapter.clear()
        dialog?.dismiss()
    }

    fun finish(name: String?, address: String?){
        dialogCallback.onDialogFinished(name, address)
        dismiss()
    }

    internal class ListViewItem {
        var deviceName: TextView? = null
        var deviceAddress: TextView? = null
    }

    inner class ListViewAdapter: BaseAdapter() {

        private val mLeDevices : ArrayList<BluetoothDevice> = ArrayList<BluetoothDevice>()
        private lateinit var mInflater: LayoutInflater

        fun addDevice(device: BluetoothDevice){
            if(!mLeDevices.contains(device))
                mLeDevices.add(device)
        }

        fun clear(){
            mLeDevices.clear()
        }

        override fun getCount(): Int {
            return mLeDevices.size
        }

        override fun getItem(p0: Int): Any {
            return mLeDevices[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(pos: Int, convertView: View?, parent: ViewGroup?): View {
            mInflater = parent!!.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var mListViewItem: ListViewItem

            if(convertView == null){
                val newConvertView = mInflater.inflate(R.layout.list_ble, null)
                mListViewItem = ListViewItem()
                mListViewItem.deviceName = newConvertView.findViewById(R.id.tv_list_ble_device_name)
                mListViewItem.deviceAddress = newConvertView.findViewById(R.id.tv_list_ble_device_address)
                newConvertView.setTag(mListViewItem)

                val device = mLeDevices[pos]

                val deviceName = device.name
                if(deviceName != null && deviceName.isNotEmpty()){
                    mListViewItem.deviceName!!.text = deviceName
                } else {
//                    Log.d(TAG, "$deviceName")
                    mListViewItem.deviceName!!.text = "Unknown Device"
                }

                mListViewItem.deviceAddress!!.text = device.address

                return newConvertView
            } else {
                mListViewItem = convertView.tag as ListViewItem

                val device = mLeDevices[pos]

                val deviceName = device.name
                if(deviceName != null && deviceName.isNotEmpty()){
                    mListViewItem.deviceName!!.text = deviceName
                } else {
                    mListViewItem.deviceName!!.text = "Unknown Device"
                }

                mListViewItem.deviceAddress!!.text = device.address

                return convertView
            }
        }
    }
}