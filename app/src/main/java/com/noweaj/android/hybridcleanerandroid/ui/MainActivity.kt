package com.noweaj.android.hybridcleanerandroid.ui

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.noweaj.android.hybridcleanerandroid.R
import com.noweaj.android.hybridcleanerandroid.ble.BleDataReceiver
import com.noweaj.android.hybridcleanerandroid.data.SingleEvent
import com.noweaj.android.hybridcleanerandroid.databinding.ActivityMainBinding
import com.noweaj.android.hybridcleanerandroid.ui.component.BleDialog
import com.noweaj.android.hybridcleanerandroid.ui.component.BaseDialog
import com.noweaj.android.hybridcleanerandroid.ui.core.BaseActivity
import com.noweaj.android.hybridcleanerandroid.util.InjectionUtil
import com.noweaj.android.hybridcleanerandroid.viewmodel.MainViewModel
import io.reactivex.disposables.Disposable

class MainActivity : BaseActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private val viewModel: MainViewModel by viewModels{
        InjectionUtil.provideMainViewModelFactory(application)
    }
    private lateinit var binding: ActivityMainBinding

    private lateinit var observerNavigateToURL: Observer<SingleEvent<String>>
    private lateinit var observerOpenDrawer: Observer<Boolean>
    private lateinit var observerSnackBar: Observer<SingleEvent<String>>
    private lateinit var observerBleStatus: Observer<Int>
    private lateinit var observerErrorMessage: Observer<String>
    private lateinit var observerBleDisconnected: Observer<SingleEvent<Boolean>>

    private var isBluetoothAvailable = false

    private var bleDialog: AlertDialog? = null
    private var errorDialog: AlertDialog? = null
    private var exitDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
    }

    override fun onBluetoothCheckDone(isBluetoothAvailable: Boolean) {
        observe()
        if(isBluetoothAvailable){
            this.isBluetoothAvailable = isBluetoothAvailable
        }
    }

    private fun initUi(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.tbMain)
        binding.mainViewModel = viewModel

        binding.tvMainNotificationNobluetooth.visibility = View.VISIBLE

        binding.nvMain.bringToFront()
        binding.dlMain.drawerElevation = 0f
    }


    private fun observe(){
        observerNavigateToURL = Observer{ event ->
            event.getContentIfNotHandled()?.let{
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
            }
        }
        viewModel.navigateToURL.observe(this, observerNavigateToURL)

        observerOpenDrawer = Observer{
            if(it)
                binding.dlMain.openDrawer(Gravity.RIGHT)
            else
                binding.dlMain.closeDrawer(Gravity.RIGHT)
        }
        viewModel.openDrawer.observe(this, observerOpenDrawer)

        observerSnackBar = Observer { event ->
            event.getContentIfNotHandled()?.let{
                showSnackbar("$it")
            }
        }
        viewModel.snackbar.observe(this, observerSnackBar)

        observerBleStatus = Observer {
            if(isBluetoothAvailable) {
                when (it) {
                    -1 -> {
                    } // do nothing
                    0 -> {
                        showBleDialog()
                        disconnect()
                    }
                    else -> {
                        binding.ivMainBle.setImageResource(R.drawable.image_main_ble_on)
                        binding.tvMainNotificationNobluetooth.visibility = View.GONE
                    }
                }
            } else {
                showSnackbar(getString(R.string.text_main_snackbar_bluetoothnotavailable))
            }
        }
        viewModel.bleStatus.observe(this, observerBleStatus)

        observerErrorMessage = Observer {
            Log.d(TAG, "$it")
            errorDialog?.let { return@Observer }
            errorDialog = BaseDialog(
                context = this,
                getString(R.string.text_dialog_err_title),
                onButton1Callback = object: BaseDialog.BaseDialogCallback{
                    override fun onDialogFinished() {
                        // button: 무시
                        showSnackbar(getString(R.string.text_main_snackbar_error_ignore))
                        errorDialog = null
                    }
                },
                onButton2Callback = object: BaseDialog.BaseDialogCallback{
                    override fun onDialogFinished() {
                        // button: AS문의하기
                        Log.d(TAG, "Move to inquiry")
                        showSnackbar("\'고객센터->문의하기\'로 이동하여 AS문의를 진행해 주세요.")
                        disconnect()
                    }
                },
                onExitCallback = object: BaseDialog.BaseDialogCallback{
                    override fun onDialogFinished() {
                        disconnect()
                        finish()
                    }
                },
                it,
                getString(R.string.text_main_error_message),
                "무시",
                "AS문의"
            ).build()
            errorDialog!!.show()
        }
        viewModel.errorMessage.observe(this, observerErrorMessage)

        observerBleDisconnected = Observer { event ->
            event.getContentIfNotHandled()?.let{
                // disconnected
                disconnect()
            }
        }
        viewModel.bleDisconnected.observe(this, observerBleDisconnected)
    }

    private fun showBleDialog(){
        bleDialog = BleDialog(
            context = this,
            dialogCallback = object: BleDialog.BleDialogCallback{
                override fun onDialogFinished(deviceName: String?, deviceAddress: String?) {
                    Log.d(TAG, "$deviceName, $deviceAddress")
                    viewModel.setConnection(deviceName, deviceAddress)
                }
            }
        ).show()
    }

    private fun showSnackbar(msg: String){
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
    }

    private fun disconnect(){
        errorDialog = null
        binding.ivMainBle.setImageResource(R.drawable.image_main_ble_off)
        binding.mainViewModel!!.disconnectDevice()
    }

    override fun onResume() {
        super.onResume()
        subscribe()
    }

    private var disposable: Disposable? = null

    private fun subscribe(){
        val publishSubject = BleDataReceiver.getInstance()
        if(disposable == null) {
            disposable = publishSubject.subscribe {
                binding.mainViewModel!!.onDataReceived(it)
            }
            Log.d(TAG, "Start subscribing")
        } else {
            Log.d(TAG, "Subscriber already subscribing")
        }
    }

    override fun onPause() {
        super.onPause()
        bleDialog?.let{ bleDialog!!.dismiss() }
        errorDialog?.let{ errorDialog!!.dismiss() }
        unsubscribe()

//        val bleDataPublishTest = BleDataPublishTest.getInstance()
//        bleDataPublishTest.stop()
    }

    private fun unsubscribe(){
        if(!disposable!!.isDisposed){
            disposable!!.dispose()
            disposable = null
            Log.d(TAG, "Subscriber disposed")
        } else {
            Log.d(TAG, "Subscriber already disposed")
        }
    }

    override fun onStop() {
        super.onStop()

        viewModel.bleStatus.removeObserver(observerBleStatus)
        viewModel.navigateToURL.removeObserver(observerNavigateToURL)
        viewModel.openDrawer.removeObserver(observerOpenDrawer)
        viewModel.snackbar.removeObserver(observerSnackBar)
        viewModel.errorMessage.removeObserver(observerErrorMessage)
        viewModel.bleDisconnected.removeObserver(observerBleDisconnected)
    }

    override fun onBackPressed() {
        exitDialog = BaseDialog(
            context = this,
            "종료",
            onButton1Callback = object: BaseDialog.BaseDialogCallback{
                override fun onDialogFinished() {
                    exitDialog!!.dismiss()
                }
            },
            onButton2Callback = object: BaseDialog.BaseDialogCallback{
                override fun onDialogFinished() {
                    // no implementation
                }
            },
            onExitCallback = object: BaseDialog.BaseDialogCallback{
                override fun onDialogFinished() {
                    disconnect()
                    finish()
                }
            },
            "앱을 종료합니다. 저장되지 않은 정보는 모두 사라집니다.",
            "계속 하시겠습니까?",
            "취소"
        ).build()
        exitDialog!!.show()
    }
}