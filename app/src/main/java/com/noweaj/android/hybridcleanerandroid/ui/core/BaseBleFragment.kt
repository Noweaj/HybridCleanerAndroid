package com.noweaj.android.hybridcleanerandroid.ui.core

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.noweaj.android.hybridcleanerandroid.ble.BleDataReceiver
import io.reactivex.disposables.Disposable

abstract class BaseBleFragment: Fragment() {

    private val TAG = BaseBleFragment::class.java.simpleName

    abstract fun onDataReceived(data: String)
    abstract fun addObservers()
    abstract fun removeObservers()

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "BaseFragment::onAttach")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "BaseFragment::onDetach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "BaseFragment::onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "BaseFragment::onDestroy")
    }
}