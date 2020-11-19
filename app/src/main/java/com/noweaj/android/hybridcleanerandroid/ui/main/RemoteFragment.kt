package com.noweaj.android.hybridcleanerandroid.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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

    override fun addObservers() {
        observerOperatingMode = Observer {
            Log.d(TAG, "OperatingMode: $it")
        }
        binding.remoteViewModel!!.operatingMode.observe(viewLifecycleOwner, observerOperatingMode)
    }

    override fun removeObservers() {
        binding.remoteViewModel!!.operatingMode.removeObserver(observerOperatingMode)
    }

    override fun onDataReceived(data: String) {
        binding.remoteViewModel!!.processData(data)
    }
}