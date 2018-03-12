package com.gorigolilagmail.kyutechapp2018.view.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.gorigolilagmail.kyutechapp2018.view.customView.ClassGridItem

/**
 * Created by pokotsun on 18/03/12.
 */

class ClassGridAdapter(private val context: Context): BaseAdapter() {

    override fun getCount(): Int = 30

    override fun getItem(p0: Int): Any {
        return 7
    }

    override fun getItemId(p0: Int): Long {
        return 7
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return ((convertView as? ClassGridItem)?: ClassGridItem(context)).apply {
        }
    }
}