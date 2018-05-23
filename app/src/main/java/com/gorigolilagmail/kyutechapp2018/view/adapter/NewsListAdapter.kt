package com.gorigolilagmail.kyutechapp2018.view.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.gorigolilagmail.kyutechapp2018.model.News
import com.gorigolilagmail.kyutechapp2018.view.customView.ItemListNews

/**
 * Created by pokotsun on 18/03/16.
 */

class NewsListAdapter(private val context: Context): BaseAdapter() {
    var items: MutableList<News> = mutableListOf()

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any? = items[position]

    override fun getItemId(position: Int): Long = 0

    // Viewを渡す
    override fun getView(position: Int,
                         convertView: View?,
                         parent: ViewGroup?): View =
            ((convertView as? ItemListNews) ?:
            ItemListNews(context)).apply {
                setView(items[position])
            }
}