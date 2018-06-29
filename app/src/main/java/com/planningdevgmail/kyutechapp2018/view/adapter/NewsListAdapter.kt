package com.planningdevgmail.kyutechapp2018.view.adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.planningdevgmail.kyutechapp2018.model.News
import com.planningdevgmail.kyutechapp2018.view.customView.ItemListNewsUi
import org.jetbrains.anko.UI

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
                ((convertView as? ItemListNewsUi) ?:
                ItemListNewsUi(context).apply {
                    createView(context.UI {})
                }).apply {
                    setItem(items[position])
                }

//    ItemListNewsHeadingUi(context).apply {
//        createView(context.UI {})
//    })

}