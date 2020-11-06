package com.noweaj.android.hybridcleanerandroid.ui.component

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.noweaj.android.hybridcleanerandroid.R

class BleDialog(context: Context){

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
        dialog?.show()
    }

    fun dismiss() {
        dialog?.dismiss()
    }


}