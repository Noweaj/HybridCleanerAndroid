package com.noweaj.android.hybridcleanerandroid.ui.component

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.noweaj.android.hybridcleanerandroid.R
import com.noweaj.android.hybridcleanerandroid.ui.core.BaseDialog
import kotlinx.android.synthetic.main.dialog_basic.view.*

class BasicDialog(
    context: Context,
    val view: View
): BaseDialog(context, view) {

    fun build(
        title: String,
        cause: String,
        msg: String,
        onButton1Callback: BaseDialogCallback,
        onButton2Callback: BaseDialogCallback,
        onExitCallback: BaseDialogCallback,
        vararg buttonTitle: String
    ) {
        super.build()

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

        when(buttonTitle.size){
            0 -> {}
            1 -> {
                view.tv_dialog_base_button1.setText(buttonTitle[0])
                view.ll_dialog_base_button2.visibility = View.GONE
            }
            2 -> {
                view.tv_dialog_base_button1.setText(buttonTitle[0])
                view.tv_dialog_base_button2.setText(buttonTitle[1])
            }
            else -> {
                // err. show as default
            }
        }
    }
}