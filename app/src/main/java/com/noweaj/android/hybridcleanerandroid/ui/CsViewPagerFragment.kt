package com.noweaj.android.hybridcleanerandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.noweaj.android.hybridcleanerandroid.R
import com.noweaj.android.hybridcleanerandroid.adapter.*
import com.noweaj.android.hybridcleanerandroid.databinding.FragmentCsViewpagerBinding

class CsViewPagerFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCsViewpagerBinding.inflate(inflater, container, false)
        val tabLayout = binding.tlCs
        val viewPager = binding.vp2Cs

        viewPager.adapter = CsViewPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
            tab.text = getTabTitle(pos)
        }.attach()

        return binding.root
    }

    private fun getTabTitle(pos: Int): String? {
        return when(pos){
            QNA_PAGE_INDEX -> getString(R.string.tab_title_cs_qna)
            INQUIRY_PAGE_INDEX -> getString(R.string.tab_title_cs_inquiry)
            RESPONSE_PAGE_INDEX -> getString(R.string.tab_title_cs_response)
            CSCENTER_PAGE_INDEX -> getString(R.string.tab_title_cs_cscenter)
            else -> null
        }
    }
}