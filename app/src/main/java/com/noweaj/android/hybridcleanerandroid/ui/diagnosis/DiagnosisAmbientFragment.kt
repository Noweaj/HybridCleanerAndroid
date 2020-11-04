package com.noweaj.android.hybridcleanerandroid.ui.diagnosis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.noweaj.android.hybridcleanerandroid.R
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
        binding.bDiagnosisCliffInactive.setOnClickListener{
            findNavController().navigate(DiagnosisAmbientFragmentDirections.actionAmbientFragmentToCliffFragment())
        }
        return binding.root
    }
}