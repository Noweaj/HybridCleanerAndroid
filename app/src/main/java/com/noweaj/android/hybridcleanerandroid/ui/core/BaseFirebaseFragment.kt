package com.noweaj.android.hybridcleanerandroid.ui.core

import androidx.fragment.app.Fragment

abstract class BaseFirebaseFragment: Fragment() {

    override fun onResume() {
        super.onResume()
        // check firebase credential
    }

    override fun onPause(){
        super.onPause()
    }
}