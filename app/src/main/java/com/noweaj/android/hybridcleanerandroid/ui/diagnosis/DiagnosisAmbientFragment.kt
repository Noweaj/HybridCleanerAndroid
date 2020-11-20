package com.noweaj.android.hybridcleanerandroid.ui.diagnosis

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.noweaj.android.hybridcleanerandroid.databinding.FragmentDiagnosisAmbientBinding
import com.noweaj.android.hybridcleanerandroid.ui.core.BaseBleFragment
import com.noweaj.android.hybridcleanerandroid.util.InjectionUtil

import com.noweaj.android.hybridcleanerandroid.viewmodel.DiagnosisAmbientViewModel

class DiagnosisAmbientFragment: BaseBleFragment() {

    private val TAG = DiagnosisAmbientFragment::class.java.simpleName

    private val viewModel: DiagnosisAmbientViewModel by viewModels {
        InjectionUtil.provideDiagnosisAmbientViewModelFactory()
    }

    private lateinit var binding: FragmentDiagnosisAmbientBinding
    private lateinit var observerAmbient: Observer<Boolean>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiagnosisAmbientBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setUpUi()
        return binding.root
    }

    private fun setUpUi(){
        binding.bDiagnosisCliffInactive.setOnClickListener{
            findNavController().navigate(
                DiagnosisAmbientFragmentDirections.actionAmbientFragmentToCliffFragment())
        }
    }

    override fun addObservers() {
        observerAmbient = Observer {
            Log.d(TAG, "it: $it")
        }
        binding.viewModel!!.isAmbientDark.observe(viewLifecycleOwner, observerAmbient)
    }

    override fun removeObservers() {
        binding.viewModel!!.isAmbientDark.removeObserver(observerAmbient)
    }

    override fun onDataReceived(data: String) {
        binding.viewModel!!.processData(data)
    }
}