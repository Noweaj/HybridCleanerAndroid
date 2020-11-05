package com.noweaj.android.hybridcleanerandroid.ui.main

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import com.noweaj.android.hybridcleanerandroid.R
import com.noweaj.android.hybridcleanerandroid.databinding.FragmentBatteryBinding

class BatteryFragment: Fragment() {

    lateinit var binding: FragmentBatteryBinding
    lateinit var animatorHandheld: ObjectAnimator
    lateinit var animatorBase: ObjectAnimator
    lateinit var animatorRobot: ObjectAnimator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBatteryBinding.inflate(inflater, container, false)
        setupUi()
        return binding.root
    }

    private fun setupUi(){
        binding.ssbmHandheld.productImage = R.drawable.image_battery_handheld
        animatorHandheld = ObjectAnimator.ofFloat(binding.ssbmHandheld, "progress", 0.toFloat(), 20.toFloat())
        animatorHandheld.interpolator = DecelerateInterpolator()
        animatorHandheld.duration = 1500

        binding.ssbmBase.productImage = R.drawable.image_battery_base
        animatorBase = ObjectAnimator.ofFloat(binding.ssbmBase, "progress", 0.toFloat(), 50.toFloat())
        animatorBase.interpolator = DecelerateInterpolator()
        animatorBase.duration = 1500

        binding.ssbmRobot.productImage = R.drawable.image_battery_robot
        animatorRobot = ObjectAnimator.ofFloat(binding.ssbmRobot, "progress", 0.toFloat(), 80.toFloat())
        animatorRobot.interpolator = DecelerateInterpolator()
        animatorRobot.duration = 1500
    }

    override fun onResume() {
        super.onResume()

        runAnimation()
    }

    private fun runAnimation(){
        animatorHandheld.start()
        animatorBase.start()
        animatorRobot.start()
    }
}