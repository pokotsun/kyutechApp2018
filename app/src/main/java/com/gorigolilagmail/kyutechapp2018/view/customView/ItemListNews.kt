package com.gorigolilagmail.kyutechapp2018.view.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.model.News
import org.jetbrains.anko.find

/**
 * Created by pokotsun on 18/03/16.
 */

class ItemListNews @JvmOverloads constructor(context: Context,
                                             attrs: AttributeSet? = null,
                                             defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {

    var view: View? = null

    fun setView(news: News) {
        view = LayoutInflater.from(context).inflate(R.layout.news_list_item, this)
        view?.find<TextView>(R.id.news_name)?.text = news.infos[0].content
    }
}