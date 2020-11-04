package com.noweaj.android.hybridcleanerandroid.ui.cs

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.noweaj.android.hybridcleanerandroid.databinding.FragmentCsCenterBinding

class CscenterFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCsCenterBinding.inflate(inflater, container, false)
        binding.ibCsCenterCall.setOnClickListener{
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.setData(Uri.parse("tel:15771577"))
            startActivity(callIntent)
        }
        return binding.root
    }
}