package com.noweaj.android.hybridcleanerandroid.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.noweaj.android.hybridcleanerandroid.R
import com.noweaj.android.hybridcleanerandroid.databinding.FragmentRemoteBinding
import com.noweaj.android.hybridcleanerandroid.ui.core.BaseBleFragment
import com.noweaj.android.hybridcleanerandroid.util.InjectionUtil
import com.noweaj.android.hybridcleanerandroid.viewmodel.RemoteViewModel

class RemoteFragment: BaseBleFragment() {

    private val TAG = RemoteFragment::class.java.simpleName

    private val viewModel: RemoteViewModel by viewModels {
        InjectionUtil.provideRemoteViewModelFactory()
    }

    private lateinit var binding: FragmentRemoteBinding
    private lateinit var observerOperatingMode: Observer<Int>

    private var currentMode: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRemoteBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setUpUi()
        return binding.root
    }

    private fun setUpUi(){

    }

    private fun setOperatingMode(mode: Int){
        if(mode < 0){ // auto
            binding.ivRemoteMode.setImageResource(R.drawable.image_remote_mode_auto_blue)
        } else { // manual
            binding.ivRemoteMode.setImageResource(R.drawable.image_remote_mode_manual_blue)
        }
    }

    override fun addObservers() {
        observerOperatingMode = Observer { setOperatingMode(it) }
        binding.viewModel!!.setOperatingMode.observe(viewLifecycleOwner, observerOperatingMode)
    }

    override fun removeObservers() {
        binding.viewModel!!.setOperatingMode.removeObserver(observerOperatingMode)
    }

    override fun onDataReceived(data: String) {
        binding.viewModel!!.processData(data)
    }
}