package com.noweaj.android.hybridcleanerandroid.ui.diagnosis

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.noweaj.android.hybridcleanerandroid.databinding.FragmentDiagnosisAmbientBinding
import com.noweaj.android.hybridcleanerandroid.ui.core.BaseFragment

import com.noweaj.android.hybridcleanerandroid.viewmodel.DiagnosisAmbientViewModel
import org.json.JSONObject

class DiagnosisAmbientFragment: BaseFragment() {

    private val TAG = DiagnosisAmbientFragment::class.java.simpleName

    private lateinit var binding: FragmentDiagnosisAmbientBinding
    private lateinit var observer: Observer<Boolean>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiagnosisAmbientBinding.inflate(inflater, container, false)
        binding.diagnosisAmbientViewModel = DiagnosisAmbientViewModel()

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
        observer = Observer {
            Log.d(TAG, "it: $it")
        }
        binding.diagnosisAmbientViewModel!!.isAmbientDark.observe(viewLifecycleOwner, observer)
    }

    override fun removeObservers() {
        binding.diagnosisAmbientViewModel!!.isAmbientDark.removeObserver(observer)
    }

    override fun onDataReceived(data: String) {
        binding.diagnosisAmbientViewModel!!.processData(data)
    }
}