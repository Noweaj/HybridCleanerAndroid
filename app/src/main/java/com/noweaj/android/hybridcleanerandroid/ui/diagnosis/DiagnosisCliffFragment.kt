package com.noweaj.android.hybridcleanerandroid.ui.diagnosis

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.noweaj.android.hybridcleanerandroid.data.CliffSensorData
import com.noweaj.android.hybridcleanerandroid.databinding.FragmentDiagnosisCliffBinding
import com.noweaj.android.hybridcleanerandroid.ui.core.BaseFragment
import com.noweaj.android.hybridcleanerandroid.viewmodel.DiagnosisCliffViewModel

class DiagnosisCliffFragment: BaseFragment() {

    private val TAG = DiagnosisCliffFragment::class.java.simpleName

    private lateinit var binding: FragmentDiagnosisCliffBinding
    private lateinit var observer: Observer<CliffSensorData>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiagnosisCliffBinding.inflate(inflater, container, false)
        binding.diagnosisCliffViewModel = DiagnosisCliffViewModel()

        setUpUi()
        return binding.root
    }

    private fun setUpUi(){
        binding.bDiagnosisAmbientInactive.setOnClickListener{
            findNavController().navigateUp()
        }
    }

    override fun addObservers() {
        observer = Observer {
            Log.d(TAG, "it: ${it.topLeft} ${it.topRight} ${it.botLeft} ${it.botRight}")
        }
        binding.diagnosisCliffViewModel!!.cliffSensorData.observe(viewLifecycleOwner, observer)
    }

    override fun removeObservers() {
        binding.diagnosisCliffViewModel!!.cliffSensorData.removeObserver(observer)
    }

    override fun onDataReceived(data: String) {
        binding.diagnosisCliffViewModel!!.processData(data)
    }
}