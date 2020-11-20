package com.noweaj.android.hybridcleanerandroid.ui.diagnosis

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.noweaj.android.hybridcleanerandroid.data.CliffSensorData
import com.noweaj.android.hybridcleanerandroid.databinding.FragmentDiagnosisCliffBinding
import com.noweaj.android.hybridcleanerandroid.ui.core.BaseBleFragment
import com.noweaj.android.hybridcleanerandroid.util.InjectionUtil
import com.noweaj.android.hybridcleanerandroid.viewmodel.DiagnosisCliffViewModel

class DiagnosisCliffFragment: BaseBleFragment() {

    private val TAG = DiagnosisCliffFragment::class.java.simpleName

    private val viewModel: DiagnosisCliffViewModel by viewModels {
        InjectionUtil.provideDiagnosisCliffViewModelFactory()
    }

    private lateinit var binding: FragmentDiagnosisCliffBinding
    private lateinit var observerCliff: Observer<CliffSensorData>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiagnosisCliffBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setUpUi()
        return binding.root
    }

    private fun setUpUi(){
        binding.bDiagnosisAmbientInactive.setOnClickListener{
            findNavController().navigateUp()
        }
    }

    override fun addObservers() {
        observerCliff = Observer {
            Log.d(TAG, "it: ${it.topLeft} ${it.topRight} ${it.botLeft} ${it.botRight}")
        }
        binding.viewModel!!.cliffSensorData.observe(viewLifecycleOwner, observerCliff)
    }

    override fun removeObservers() {
        binding.viewModel!!.cliffSensorData.removeObserver(observerCliff)
    }

    override fun onDataReceived(data: String) {
        binding.viewModel!!.processData(data)
    }
}