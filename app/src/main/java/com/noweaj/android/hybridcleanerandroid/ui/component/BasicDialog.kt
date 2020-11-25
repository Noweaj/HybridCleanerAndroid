package com.noweaj.android.hybridcleanerandroid.ui.component

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.noweaj.android.hybridcleanerandroid.R
import com.noweaj.android.hybridcleanerandroid.ui.core.BaseDialog
import kotlinx.android.synthetic.main.dialog_base.view.*

class BasicDialog(
    context: Context,
    val view: View,
    private val title: String,
    private val onButton1Callback: BaseDialogCallback,
    private val onButton2Callback: BaseDialogCallback,
    private val onExitCallback: BaseDialogCallback,
    private val cause: String,
    private val msg: String,
    vararg val button: String
): BaseDialog(context, view) {

    fun build(): AlertDialog {
        dialog = builder.create()
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setCancelable(false)

        view.tv_dialog_base_title.text = title
        view.tv_dialog_base_cause.text = "$cause"
        view.tv_dialog_base_info.text = msg

        view.ll_dialog_base_button1.setOnClickListener{
            onButton1Callback.onDialogFinished()
            dismiss()
        }

        view.ll_dialog_base_button2.setOnClickListener{
            onButton2Callback.onDialogFinished()
            dismiss()
        }

        view.ll_dialog_base_exit.setOnClickListener{
            onExitCallback.onDialogFinished()
            dismiss()
        }

        when(button.size){
            0 -> {}
            1 -> {
                view.tv_dialog_base_button1.setText(button[0])
                view.ll_dialog_base_button2.visibility = View.GONE
            }
            2 -> {
                view.tv_dialog_base_button1.setText(button[0])
                view.tv_dialog_base_button2.setText(button[1])
            }
            else -> {
                // err. show as default
            }
        }

        return dialog as AlertDialog
    }
}