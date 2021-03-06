package com.noweaj.android.hybridcleanerandroid.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.noweaj.android.hybridcleanerandroid.ui.main.BatteryFragment
import com.noweaj.android.hybridcleanerandroid.ui.main.CsViewPagerFragment
import com.noweaj.android.hybridcleanerandroid.ui.main.DiagnosisFragment
import com.noweaj.android.hybridcleanerandroid.ui.main.RemoteFragment

const val REMOTE_PAGE_INDEX = 0
const val DIAGNOSIS_PAGE_INDEX = 1
const val BATTERY_PAGE_INDEX = 2
const val CS_PAGE_INDEX = 3

class MainViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        REMOTE_PAGE_INDEX to { RemoteFragment() },
        DIAGNOSIS_PAGE_INDEX to { DiagnosisFragment() },
        BATTERY_PAGE_INDEX to { BatteryFragment() },
        CS_PAGE_INDEX to { CsViewPagerFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}