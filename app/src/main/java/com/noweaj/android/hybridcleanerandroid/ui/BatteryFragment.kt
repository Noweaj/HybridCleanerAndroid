package com.noweaj.android.hybridcleanerandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.noweaj.android.hybridcleanerandroid.R
import com.noweaj.android.hybridcleanerandroid.databinding.FragmentBatteryBinding

class BatteryFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBatteryBinding.inflate(inflater, container, false)
        setupUi(binding)
        return binding.root
    }

    private fun setupUi(binding: FragmentBatteryBinding){
        binding.ssbmHandheld.productImage = R.drawable.image_battery_handheld
        binding.ssbmBase.productImage = R.drawable.image_battery_base
        binding.ssbmRobot.productImage = R.drawable.image_battery_robot
    }
}