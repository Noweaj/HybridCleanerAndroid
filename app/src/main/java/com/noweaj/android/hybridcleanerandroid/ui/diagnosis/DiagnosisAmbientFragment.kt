package com.noweaj.android.hybridcleanerandroid.ui.diagnosis

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.noweaj.android.hybridcleanerandroid.R
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
    private lateinit var observerDisconnected: Observer<Boolean>

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
        binding.ivDiagnosisAmbientSensor.visibility = View.INVISIBLE
    }

    override fun addObservers() {
        observerAmbient = Observer {
            Log.d(TAG, "it: $it")
            binding.ivDiagnosisAmbientSensor.visibility = View.VISIBLE
//            setPosition()
            if(it){
                binding.ivDiagnosisAmbientSensor.setImageResource(R.drawable.image_diagnosis_sensor_on)
            } else {
                binding.ivDiagnosisAmbientSensor.setImageResource(R.drawable.image_diagnosis_sensor_off)
            }
        }
        binding.viewModel!!.isBright.observe(viewLifecycleOwner, observerAmbient)

        observerDisconnected = Observer {
            if(it)
                binding.ivDiagnosisAmbientSensor.visibility = View.INVISIBLE
        }
        binding.viewModel!!.disconnected.observe(viewLifecycleOwner, observerDisconnected)
    }

    private fun setPosition(){
        Log.d(TAG, "body layout size: w=${binding.clDiagnosisAmbientBodyLayout.width} h=${binding.clDiagnosisAmbientBodyLayout.height}")
        Log.d(TAG, "body size: w=${binding.ivDiagnosisAmbientBody.width} h=${binding.ivDiagnosisAmbientBody.height}")

        Log.d(TAG, "bodylayoutx: ${binding.clDiagnosisAmbientBodyLayout.x} bodylayouty: ${binding.clDiagnosisAmbientBodyLayout.y}")
        Log.d(TAG, "bodyx: ${binding.ivDiagnosisAmbientBody.x} bodyy: ${binding.ivDiagnosisAmbientBody.y}")

        val lightX = /*binding.ivDiagnosisAmbientBody.x + */(binding.ivDiagnosisAmbientBody.width.toFloat() * 0.14925)
        val lightY = /*binding.ivDiagnosisAmbientBody.y + */(binding.ivDiagnosisAmbientBody.height.toFloat() * 0.17210)

        Log.d(TAG, "sensorX: $lightX, sensorY: $lightY")

        binding.ivDiagnosisAmbientSensor.x = lightX.toFloat()
        binding.ivDiagnosisAmbientSensor.y = lightY.toFloat()
    }

    override fun removeObservers() {
        binding.viewModel!!.isBright.removeObserver(observerAmbient)
        binding.viewModel!!.disconnected.removeObserver(observerDisconnected)
    }

    override fun onDataReceived(data: String) {
        binding.viewModel!!.processData(data)
    }
}

/**
width x height

402 x 337

68 x 66

0.16915 x 0.19584

57 x 57

0.14179 x 0.16914


60 x 58

 0.14925 x 0.17210

        **/