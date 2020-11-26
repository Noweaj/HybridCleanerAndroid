package com.noweaj.android.hybridcleanerandroid.ui.component

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.noweaj.android.hybridcleanerandroid.R
import com.noweaj.android.hybridcleanerandroid.ui.core.BaseDialog
import kotlinx.android.synthetic.main.dialog_doc.view.*

class DocDialog(
    context: Context,
    val view: View
): BaseDialog(context, view) {

    fun build(
        title: String,
        content: String,
        onExitCallback: BaseDialogCallback
    ){
        super.build()

        view.tv_dialog_doc_title.setText(title)
        view.tv_dialog_doc_content.setText(content)

        view.tv_dialog_doc_exit.setOnClickListener{
            dismiss()
            onExitCallback.onDialogFinished()
        }
    }
}