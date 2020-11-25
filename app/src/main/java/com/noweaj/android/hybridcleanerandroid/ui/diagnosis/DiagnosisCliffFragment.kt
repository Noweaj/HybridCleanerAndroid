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
    private lateinit var observerCliff: Observer<Array<Boolean>>
    private lateinit var observerDisconnected: Observer<Boolean>

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
        setVisibilityOnDisconnected()
    }

    override fun addObservers() {
        observerCliff = Observer {
            Log.d(TAG, "topleft: ${it[0]} topright: ${it[1]} botleft: ${it[2]} botright: ${it[3]}")
            setVisibilityOnConnected()
//            setPosition()
            setValue(it)
        }
        binding.viewModel!!.cliffSensorData.observe(viewLifecycleOwner, observerCliff)

        observerDisconnected = Observer {
            if(it)
                setVisibilityOnDisconnected()
        }
        binding.viewModel!!.disconnected.observe(viewLifecycleOwner, observerDisconnected)
    }

    private fun setVisibilityOnDisconnected(){
        binding.ivDiagnosisCliffSensorTopleft.visibility = View.INVISIBLE
        binding.ivDiagnosisCliffSensorTopright.visibility = View.INVISIBLE
        binding.ivDiagnosisCliffSensorBottomleft.visibility = View.INVISIBLE
        binding.ivDiagnosisCliffSensorBottomright.visibility = View.INVISIBLE
    }

    private fun setVisibilityOnConnected(){
        binding.ivDiagnosisCliffSensorTopleft.visibility = View.VISIBLE
        binding.ivDiagnosisCliffSensorTopright.visibility = View.VISIBLE
        binding.ivDiagnosisCliffSensorBottomleft.visibility = View.VISIBLE
        binding.ivDiagnosisCliffSensorBottomright.visibility = View.VISIBLE
    }

    private fun setPosition(){
        Log.d(TAG, "body layout size: w=${binding.clDiagnosisCliffBodyLayout.width} h=${binding.clDiagnosisCliffBodyLayout.height}")
        Log.d(TAG, "body size: w=${binding.ivDiagnosisCliffBody.width} h=${binding.ivDiagnosisCliffBody.height}")

        binding.ivDiagnosisCliffSensorTopleft.x =
            (/*binding.ivDiagnosisCliffBody.x + */(binding.ivDiagnosisCliffBody.width.toFloat() * 0.073)).toFloat()
        binding.ivDiagnosisCliffSensorTopleft.y =
            (/*binding.ivDiagnosisCliffBody.y + */(binding.ivDiagnosisCliffBody.height.toFloat() * 0.109)).toFloat()

        binding.ivDiagnosisCliffSensorTopright.x =
            (/*binding.ivDiagnosisCliffBody.x + */(binding.ivDiagnosisCliffBody.width.toFloat() * 0.885)).toFloat()
        binding.ivDiagnosisCliffSensorTopright.y =
            (/*binding.ivDiagnosisCliffBody.y + */(binding.ivDiagnosisCliffBody.height.toFloat() * 0.109)).toFloat()

        binding.ivDiagnosisCliffSensorBottomleft.x =
            (/*binding.ivDiagnosisCliffBody.x + */(binding.ivDiagnosisCliffBody.width.toFloat() * 0.099)).toFloat()
        binding.ivDiagnosisCliffSensorBottomleft.y =
            (/*binding.ivDiagnosisCliffBody.y + */(binding.ivDiagnosisCliffBody.height.toFloat() * 0.837)).toFloat()

        binding.ivDiagnosisCliffSensorBottomright.x =
            (/*binding.ivDiagnosisCliffBody.x + */(binding.ivDiagnosisCliffBody.width.toFloat() * 0.856)).toFloat()
        binding.ivDiagnosisCliffSensorBottomright.y =
            (/*binding.ivDiagnosisCliffBody.y + */(binding.ivDiagnosisCliffBody.height.toFloat() * 0.837)).toFloat()
    }

    private fun setValue(data: Array<Boolean>){
        val sensorArray = arrayOf(
            binding.ivDiagnosisCliffSensorTopleft,
            binding.ivDiagnosisCliffSensorTopright,
            binding.ivDiagnosisCliffSensorBottomleft,
            binding.ivDiagnosisCliffSensorBottomright)

        for(i in data.indices){
            if(data[i])
                sensorArray[i].setImageResource(R.drawable.image_diagnosis_sensor_on)
            else
                sensorArray[i].setImageResource(R.drawable.image_diagnosis_sensor_off)
        }
    }

    override fun removeObservers() {
        binding.viewModel!!.cliffSensorData.removeObserver(observerCliff)
        binding.viewModel!!.disconnected.removeObserver(observerDisconnected)
    }

    override fun onDataReceived(data: String) {
        binding.viewModel!!.processData(data)
    }
}

/**
383x319

28x35	339x35
38x267	328x267

0.07310x0.10971	0.88511x0.10971
0.09921x0.83699	0.85639x0.83699
        **/