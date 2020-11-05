package com.noweaj.android.hybridcleanerandroid.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.noweaj.android.hybridcleanerandroid.databinding.FragmentDiagnosisBinding

class DiagnosisFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDiagnosisBinding.inflate(inflater, container, false)

        return binding.root
    }
}