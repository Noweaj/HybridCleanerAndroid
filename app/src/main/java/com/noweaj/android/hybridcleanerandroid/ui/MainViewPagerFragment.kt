package com.noweaj.android.hybridcleanerandroid.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.noweaj.android.hybridcleanerandroid.R
import com.noweaj.android.hybridcleanerandroid.adapter.*
import com.noweaj.android.hybridcleanerandroid.databinding.FragmentMainViewpagerBinding

class MainViewPagerFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainViewpagerBinding.inflate(inflater, container, false)
        val tabLayout = binding.tlMain
        val viewPager = binding.vp2Main

        viewPager.adapter = MainViewPagerAdapter(this)

        tabLayout.setTabTextColors(
            Color.parseColor("#D9D9D9"),
            Color.parseColor("#231F20")
        )

        TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
            tab.setIcon(getTabIcon(pos))
            tab.text = getTabTitle(pos)
        }.attach()

        (activity as AppCompatActivity).setSupportActionBar(binding.tbMain)
//        (activity as AppCompatActivity).supportActionBar!!.setIcon(R.drawable.ic_baseline_menu_48_000000)

        return binding.root
    }

    private fun getTabIcon(pos: Int): Int {
        return when(pos){
            REMOTE_PAGE_INDEX -> R.drawable.selector_tablayout_remote_icon
            DIAGNOSIS_PAGE_INDEX -> R.drawable.selector_tablayout_diagnosis_icon
            BATTERY_PAGE_INDEX -> R.drawable.selector_tablayout_battery_icon
            CS_PAGE_INDEX -> R.drawable.selector_tablayout_cs_icon
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(pos: Int): String? {
        return when(pos){
            REMOTE_PAGE_INDEX -> getString(R.string.tab_title_main_remote)
            DIAGNOSIS_PAGE_INDEX -> getString(R.string.tab_title_main_diagnosis)
            BATTERY_PAGE_INDEX -> getString(R.string.tab_title_main_battery)
            CS_PAGE_INDEX -> getString(R.string.tab_title_main_cs)
            else -> null
        }
    }
}