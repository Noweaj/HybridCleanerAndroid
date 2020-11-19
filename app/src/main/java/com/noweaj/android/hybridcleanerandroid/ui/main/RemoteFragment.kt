package com.noweaj.android.hybridcleanerandroid.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.noweaj.android.hybridcleanerandroid.data.SingleEvent
import com.noweaj.android.hybridcleanerandroid.databinding.FragmentRemoteBinding
import com.noweaj.android.hybridcleanerandroid.ui.core.BaseFragment
import com.noweaj.android.hybridcleanerandroid.util.InjectionUtil
import com.noweaj.android.hybridcleanerandroid.viewmodel.RemoteViewModel

class RemoteFragment: BaseFragment() {

    private val TAG = RemoteFragment::class.java.simpleName

    private val viewModel: RemoteViewModel by viewModels {
        InjectionUtil.provideRemoteViewModelFactory()
    }

    private lateinit var binding: FragmentRemoteBinding
    private lateinit var observerOperatingMode: Observer<Int>
    private lateinit var observerManualForward: Observer<SingleEvent<Unit>>
    private lateinit var observerManualStop: Observer<SingleEvent<Unit>>
    private lateinit var observerManualBackward: Observer<SingleEvent<Unit>>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRemoteBinding.inflate(inflater, container, false)
        binding.remoteViewModel = viewModel

        setUpUi()
        return binding.root
    }

    private fun setUpUi(){

    }

    private fun setAutoMode(){

    }

    private fun setManualMode(){

    }

    override fun addObservers() {
        observerOperatingMode = Observer {
            Log.d(TAG, "OperatingMode: $it")
            if(it == 1){
                // auto mode
            } else {
                // manual mode
            }
        }
        binding.remoteViewModel!!.operatingMode.observe(viewLifecycleOwner, observerOperatingMode)

        observerManualForward = Observer {
            // forward
        }
        binding.remoteViewModel!!.manualForward.observe(viewLifecycleOwner, observerManualForward)

        observerManualStop = Observer {
            // stop
        }
        binding.remoteViewModel!!.manualStop.observe(viewLifecycleOwner, observerManualStop)

        observerManualBackward = Observer {
            // backward
        }
        binding.remoteViewModel!!.manualBackward.observe(viewLifecycleOwner, observerManualBackward)
    }

    override fun removeObservers() {
        binding.remoteViewModel!!.operatingMode.removeObserver(observerOperatingMode)
        binding.remoteViewModel!!.manualForward.removeObserver(observerManualForward)
        binding.remoteViewModel!!.manualStop.removeObserver(observerManualStop)
        binding.remoteViewModel!!.manualBackward.removeObserver(observerManualBackward)
    }

    override fun onDataReceived(data: String) {
        binding.remoteViewModel!!.processData(data)
    }
}