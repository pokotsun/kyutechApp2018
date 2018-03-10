package com.gorigolilagmail.kyutechapp2018.view.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.gorigolilagmail.kyutechapp2018.model.NewsHeading
import com.gorigolilagmail.kyutechapp2018.view.customView.ItemListNewsHeading

/**
 * Created by pokotsun on 18/03/10.
 */

class NewsHeadingListAdapter(private val context: Context): BaseAdapter() {
    var items: List<NewsHeading> = NewsHeading.getList(context)

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any? = items[position]

    override fun getItemId(position: Int): Long = 0

    // Viewを渡す
    override fun getView(position: Int,
                         convertView: View?,
                         parent: ViewGroup?): View =
            ((convertView as? ItemListNewsHeading) ?:
            ItemListNewsHeading(context)).apply {
                setItem(items[position])
            }
}