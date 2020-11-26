package com.noweaj.android.hybridcleanerandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.noweaj.android.hybridcleanerandroid.R
import com.noweaj.android.hybridcleanerandroid.data.TitleContentData

class CsQnaListAdapter: BaseExpandableListAdapter {

    val dataSet: ArrayList<TitleContentData>
    val titleLayout: Int
    val contentLayout: Int
    val context: Context
    val inflator: LayoutInflater

    constructor(dataSet: ArrayList<TitleContentData>, titleLayout: Int, contentLayout: Int, context: Context){
        this.dataSet = dataSet
        this.titleLayout = titleLayout
        this.contentLayout = contentLayout
        this.context = context
        this.inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getGroupCount(): Int {
        return dataSet.size
    }

    override fun getChildrenCount(p0: Int): Int {
        return 1
    }

    override fun getGroup(p0: Int): Any {
        return dataSet[p0].title
    }

    override fun getChild(p0: Int, p1: Int): Any {
        return dataSet[p0].content
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return p1.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View {
        var convertView = p2 ?: inflator.inflate(this.titleLayout, p3, false)
        val title = convertView.findViewById(R.id.tv_cs_qna_title) as TextView
        title.text = dataSet[p0].title
        return convertView
    }

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {
        var convertView = p3 ?: inflator.inflate(this.contentLayout, p4, false)
        val content = convertView.findViewById(R.id.tv_cs_qna_content) as TextView
        content.text = dataSet[p0].content
        return convertView
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }
}