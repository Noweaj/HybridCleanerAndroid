package com.noweaj.android.hybridcleanerandroid.ui.component

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.noweaj.android.hybridcleanerandroid.R

class DocDialog(context: Context): Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_doc)
    }
}