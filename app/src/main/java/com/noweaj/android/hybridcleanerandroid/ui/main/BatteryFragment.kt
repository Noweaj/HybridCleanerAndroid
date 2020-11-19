package com.noweaj.android.hybridcleanerandroid.ui.main

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.android.noweaj.sensebotbatterylibrary.SenseBotBatteryMeter
import com.noweaj.android.hybridcleanerandroid.R
import com.noweaj.android.hybridcleanerandroid.databinding.FragmentBatteryBinding
import com.noweaj.android.hybridcleanerandroid.ui.core.BaseFragment
import com.noweaj.android.hybridcleanerandroid.util.InjectionUtil
import com.noweaj.android.hybridcleanerandroid.viewmodel.BatteryViewModel

class BatteryFragment: BaseFragment() {

    private val viewModel: BatteryViewModel by viewModels {
        InjectionUtil.provideBatteryViewModelFactory()
    }

    private lateinit var binding: FragmentBatteryBinding
    private lateinit var observerHandheld: Observer<Float>
    private lateinit var observerBase: Observer<Float>
    private lateinit var observerRobot: Observer<Float>
    private lateinit var observerBluetoothConnection: Observer<Boolean>

    private var animatorHandheld: ObjectAnimator? = null
    private var animatorBase: ObjectAnimator? = null
    private var animatorRobot: ObjectAnimator? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBatteryBinding.inflate(inflater, container, false)
        setUpUi()
        return binding.root
    }

    private fun setUpUi(){
        // indicate as "disconnected"
        binding.ssbmHandheld.productImage = R.drawable.image_battery_handheld
        binding.ssbmHandheld.progress = 0.toFloat()
        binding.ssbmBase.productImage = R.drawable.image_battery_base
        binding.ssbmBase.progress = 0.toFloat()
        binding.ssbmRobot.productImage = R.drawable.image_battery_robot
        binding.ssbmRobot.progress = 0.toFloat()
        setBatteryMetersDisconnected()
    }

    private fun setConnectionStatus(textView: TextView, msg: Int, background: Int){
        textView.setText(msg)
        textView.setBackgroundResource(background)
    }

    override fun addObservers() {
        observerHandheld = Observer{
            animatorHandheld = setAnimation(
                binding.ssbmHandheld,
                binding.ssbmHandheld.progress,
                it
            )
            animatorHandheld!!.start()
            setConnectionStatus(
                binding.tvBatteryBtconnectionHandheld,
                R.string.text_battery_connected,
                R.drawable.background_battery_connected
            )
        }
        viewModel.batteryHandheld.observe(viewLifecycleOwner, observerHandheld)

        observerBase = Observer {
            animatorBase = setAnimation(
                binding.ssbmBase,
                binding.ssbmBase.progress,
                it
            )
            animatorBase!!.start()
            setConnectionStatus(
                binding.tvBatteryBtconnectionBase,
                R.string.text_battery_connected,
                R.drawable.background_battery_connected
            )
        }
        viewModel.batteryBase.observe(viewLifecycleOwner, observerBase)

        observerRobot = Observer {
            animatorRobot = setAnimation(
                binding.ssbmRobot,
                binding.ssbmRobot.progress,
                it
            )
            animatorRobot!!.start()
            setConnectionStatus(
                binding.tvBatteryBtconnectionRobot,
                R.string.text_battery_connected,
                R.drawable.background_battery_connected
            )
        }
        viewModel.batteryRobot.observe(viewLifecycleOwner, observerRobot)

        observerBluetoothConnection = Observer {
            if(!it){
                setBatteryMetersDisconnected()
            }
        }
        viewModel.bluetoothConnection.observe(viewLifecycleOwner, observerBluetoothConnection)
    }

    private fun setAnimation(meter: SenseBotBatteryMeter, before: Float, after: Float): ObjectAnimator {
        val objectAnimator = ObjectAnimator.ofFloat(meter, "progress", before, after)
        objectAnimator.interpolator = DecelerateInterpolator()
        objectAnimator.duration = 1000
        return objectAnimator
    }

    private fun setBatteryMetersDisconnected(){
        animatorHandheld = setAnimation(
            binding.ssbmHandheld,
            binding.ssbmHandheld.progress,
            0.toFloat()
        )
        animatorHandheld!!.start()
        setConnectionStatus(
            binding.tvBatteryBtconnectionHandheld,
            R.string.text_battery_disconnected,
            R.drawable.background_battery_disconnected
        )

        animatorBase = setAnimation(
            binding.ssbmBase,
            binding.ssbmBase.progress,
            0.toFloat()
        )
        animatorBase!!.start()
        setConnectionStatus(
            binding.tvBatteryBtconnectionBase,
            R.string.text_battery_disconnected,
            R.drawable.background_battery_disconnected
        )

        animatorRobot = setAnimation(
            binding.ssbmRobot,
            binding.ssbmRobot.progress,
            0.toFloat()
        )
        animatorRobot!!.start()
        setConnectionStatus(
            binding.tvBatteryBtconnectionRobot,
            R.string.text_battery_disconnected,
            R.drawable.background_battery_disconnected
        )
    }

    override fun removeObservers() {
        viewModel.batteryHandheld.removeObserver(observerHandheld)
        viewModel.batteryBase.removeObserver(observerBase)
        viewModel.batteryRobot.removeObserver(observerRobot)
        viewModel.bluetoothConnection.removeObserver(observerBluetoothConnection)
    }

    override fun onDataReceived(data: String) {
        viewModel.processData(data)
    }
}