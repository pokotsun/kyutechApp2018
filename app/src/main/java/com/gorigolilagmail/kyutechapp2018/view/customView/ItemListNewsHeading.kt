package com.gorigolilagmail.kyutechapp2018.view.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.gorigolilagmail.kyutechapp2018.R

/**
 * Created by pokotsun on 18/03/10.
 */

class ItemListNewsHeading @JvmOverloads constructor(context: Context,
                                                    attrs: AttributeSet? = null,
                                                    defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {

    private var view: View? = null

    init {
//        view = View.inflate(context, R.layout.tab_layout, null)
        view = LayoutInflater.from(context).inflate(R.layout.news_heading_list_item, this)
    }


}