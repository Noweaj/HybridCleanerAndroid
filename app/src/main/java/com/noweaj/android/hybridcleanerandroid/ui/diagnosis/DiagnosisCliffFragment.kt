package com.noweaj.android.hybridcleanerandroid.ui.diagnosis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.noweaj.android.hybridcleanerandroid.databinding.FragmentDiagnosisCliffBinding
import com.noweaj.android.hybridcleanerandroid.viewmodel.DiagnosisCliffViewModel

class DiagnosisCliffFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDiagnosisCliffBinding.inflate(inflater, container, false)
        binding.diagnosisCliffViewModel = DiagnosisCliffViewModel()
        binding.bDiagnosisAmbientInactive.setOnClickListener{
            findNavController().navigateUp()
        }
        return binding.root
    }
}