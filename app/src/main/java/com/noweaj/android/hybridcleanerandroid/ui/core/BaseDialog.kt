package com.noweaj.android.hybridcleanerandroid.ui.core

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog

abstract class BaseDialog(
    context: Context,
    view: View
) {

    interface BaseDialogCallback {
        fun onDialogFinished()
    }

    val builder: AlertDialog.Builder by lazy{
        AlertDialog.Builder(context).setView(view)
    }

    var dialog: AlertDialog? = null

    fun build(){
        dialog = builder.create()
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setCancelable(false)
    }

    fun show(){
        dialog?: return
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setCancelable(false)
        dialog?.show()
    }

    fun dismiss(){
        dialog?.dismiss()
    }
}