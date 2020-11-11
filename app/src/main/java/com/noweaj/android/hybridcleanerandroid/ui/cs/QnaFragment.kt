package com.noweaj.android.hybridcleanerandroid.ui.cs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.noweaj.android.hybridcleanerandroid.R
import com.noweaj.android.hybridcleanerandroid.adapter.CsQnaListAdapter
import com.noweaj.android.hybridcleanerandroid.data.CsQnaData
import com.noweaj.android.hybridcleanerandroid.databinding.FragmentCsQnaBinding
import org.json.JSONObject

class QnaFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCsQnaBinding.inflate(inflater, container, false)

        val dataSet = loadQnaData()
        val adapter = CsQnaListAdapter(
            dataSet = dataSet,
            titleLayout = R.layout.elv_cs_qna_title,
            contentLayout = R.layout.elv_cs_qna_content,
            context = requireContext()
        )
        val expandableListView = binding.elvQna
        expandableListView.setAdapter(adapter)

        return binding.root
    }

    private fun loadQnaData(): ArrayList<CsQnaData>{
        val jsonArray = JSONObject(
            requireContext().assets.open("qna.json").bufferedReader().use { it.readText() }
        ).getJSONArray("qna")

        val dataSet = ArrayList<CsQnaData>()
        for(i in 0 until jsonArray.length()){
            dataSet.add(
                CsQnaData(
                    title = jsonArray.getJSONObject(i).getString("title"),
                    content = jsonArray.getJSONObject(i).getString("content")
                )
            )
        }
        return dataSet
    }
}