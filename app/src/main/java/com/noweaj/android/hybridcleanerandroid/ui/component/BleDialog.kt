package com.noweaj.android.hybridcleanerandroid.ui.component

import android.app.Application
import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.noweaj.android.hybridcleanerandroid.R
import com.noweaj.android.hybridcleanerandroid.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.dialog_ble.view.*

class BleDialog(context: Context, private val dialogCallback: MainViewModel.DialogCallback){

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

    fun show() {
        dialog = builder.create()

        view.ll_dialog_ble_button.setOnClickListener{
            dialogCallback.onDialogCallback(null, null)
            dismiss()
        }

        dialog?.setCanceledOnTouchOutside(false)
        dialog?.show()
    }

    fun dismiss() {
        dialog?.dismiss()
    }
}