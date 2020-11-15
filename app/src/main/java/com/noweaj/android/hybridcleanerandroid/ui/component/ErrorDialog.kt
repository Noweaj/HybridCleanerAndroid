package com.noweaj.android.hybridcleanerandroid.ui.component

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.noweaj.android.hybridcleanerandroid.R
import kotlinx.android.synthetic.main.dialog_err.view.*

class ErrorDialog(context: Context, private val onRetryCallback: ErrorDialogCallback, private val onExitCallback: ErrorDialogCallback) {

    interface ErrorDialogCallback {
        fun onDialogFinished()
    }

    private val builder: AlertDialog.Builder by lazy {
        AlertDialog.Builder(context).setView(view)
    }

    private val view: View by lazy {
        View.inflate(context, R.layout.dialog_err, null)
    }

    private var dialog: AlertDialog? = null

    fun show(cause: String, msg: String){
        dialog = builder.create()

        view.tv_dialog_err_cause.text = "원인: $cause"
        view.tv_dialog_err_info.text = msg

        view.ll_dialog_err_retry.setOnClickListener{
            onRetryCallback.onDialogFinished()
            dismiss()
        }

        view.ll_dialog_err_exit.setOnClickListener{
            onExitCallback.onDialogFinished()
            dismiss()
        }

        dialog?.setCanceledOnTouchOutside(false)
        dialog?.show()
    }

    fun dismiss(){
        dialog?.dismiss()
    }
}