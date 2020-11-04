package com.noweaj.android.hybridcleanerandroid.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.noweaj.android.hybridcleanerandroid.ui.cs.CscenterFragment
import com.noweaj.android.hybridcleanerandroid.ui.cs.InquiryFragment
import com.noweaj.android.hybridcleanerandroid.ui.cs.QnaFragment
import com.noweaj.android.hybridcleanerandroid.ui.cs.ResponseFragment

const val QNA_PAGE_INDEX = 0
const val INQUIRY_PAGE_INDEX = 1
const val RESPONSE_PAGE_INDEX = 2
const val CSCENTER_PAGE_INDEX = 3

class CsViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        QNA_PAGE_INDEX to { QnaFragment() },
        INQUIRY_PAGE_INDEX to { InquiryFragment() },
        RESPONSE_PAGE_INDEX to { ResponseFragment() },
        CSCENTER_PAGE_INDEX to { CscenterFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}