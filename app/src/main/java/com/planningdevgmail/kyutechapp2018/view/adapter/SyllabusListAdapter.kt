package com.planningdevgmail.kyutechapp2018.view.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.planningdevgmail.kyutechapp2018.model.Syllabus
import com.planningdevgmail.kyutechapp2018.view.customView.ItemListSyllabusUi
import org.jetbrains.anko.UI

class SyllabusListAdapter(private val context: Context): BaseAdapter() {
    var items: MutableList<Syllabus> = mutableListOf()
    var userDepartment: String = ""

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any? = items[position]

    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int,
                         convertView: View?,
                         parent: ViewGroup?): View =
            ((convertView as? ItemListSyllabusUi)?:
                    ItemListSyllabusUi(context).apply {
                        createView(context.UI {})
                    }).apply {
                setItem(items[position], userDepartment)
            }
}