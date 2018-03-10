package com.gorigolilagmail.kyutechapp2018.view.customView

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.model.NewsHeading
import kotlinx.android.synthetic.main.news_heading_list_item.view.*

/**
 * Created by pokotsun on 18/03/10.
 */

class ItemListNewsHeading @JvmOverloads constructor(context: Context,
                                                    attrs: AttributeSet? = null,
                                                    defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {

    private var view: View? = null

    init {
        view = LayoutInflater.from(context).inflate(R.layout.news_heading_list_item, this)
    }

    fun setItem(item: NewsHeading) {
        Log.d("setItemNews", "$item")
        circular_news_heading.text = item.headingCharacter
        circular_news_heading.setCircleBackgroundColor(item.color)
        news_heading.text = item.headingName
        updated_date.text = item.updatedDate
    }


}