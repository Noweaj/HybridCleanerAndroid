package com.noweaj.android.hybridcleanerandroid.ui.core

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.noweaj.android.hybridcleanerandroid.ble.BleDataReceiver
import io.reactivex.disposables.Disposable

abstract class BaseFragment: Fragment() {

    abstract fun onDataReceived(data: String)
    abstract fun addObservers()
    abstract fun removeObservers()

    private val TAG = BaseFragment::class.java.simpleName
    private var disposable: Disposable? = null

    override fun onResume() {
        super.onResume()
        subscribe()
        addObservers()
    }

    private fun subscribe(){
        val publishSubject = BleDataReceiver.getInstance()
        if(disposable == null) {
            disposable = publishSubject.subscribe {
                onDataReceived(it)
            }
            Log.d(TAG, "Start subscribing")
        } else {
            Log.d(TAG, "Subscriber already subscribing")
        }
    }

    override fun onPause() {
        super.onPause()
        unsubscribe()
        removeObservers()
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
}