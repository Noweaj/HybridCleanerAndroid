package com.noweaj.android.hybridcleanerandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.noweaj.android.hybridcleanerandroid.databinding.FragmentDiagnosisAmbientBinding
import com.noweaj.android.hybridcleanerandroid.viewmodel.DiagnosisAmbientViewModel

class DiagnosisAmbientFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDiagnosisAmbientBinding.inflate(inflater, container, false)
        binding.diagnosisAmbientViewModel = DiagnosisAmbientViewModel()
        return binding.root
    }
}