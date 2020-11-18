package com.noweaj.android.hybridcleanerandroid.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.noweaj.android.hybridcleanerandroid.databinding.FragmentDiagnosisBinding

class DiagnosisFragment: Fragment() {

    private val TAG = DiagnosisFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDiagnosisBinding.inflate(inflater, container, false)

        return binding.root
    }

//    override fun onDataReceived(data: String) {
//        Log.d(TAG, "data received: $data")
//        val dataObject = JSONObject(data)
//        Log.d(TAG, "index: ${dataObject.getInt("index")} / id: ${dataObject.getJSONArray("devices").getJSONObject(0).getString("id")}")
//    }
}