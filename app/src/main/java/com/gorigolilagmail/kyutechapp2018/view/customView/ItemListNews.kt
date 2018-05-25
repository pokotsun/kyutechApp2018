package com.gorigolilagmail.kyutechapp2018.view.customView

import android.content.Context
import android.util.AttributeSet
import android.util.Log
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
        val titleInfos: HashMap<String, String> = news.getTitleInfos()
        view = LayoutInflater.from(context).inflate(R.layout.news_list_item, this)
        view?.find<TextView>(R.id.main_title)?.text = titleInfos["mainTitle"]
        val subTitleField: TextView? = view?.findViewById<TextView>(R.id.sub_title)

        // サブタイトルフィールドの処理
        if(subTitleField != null) {
            if((titleInfos["subTitle"] ?: "").isNullOrEmpty()) { // サブタイトルの欄が空白ならば
                Log.d("titlesInfo", "${titleInfos}")
                subTitleField.visibility = View.GONE
            } else {
                subTitleField.text = titleInfos["subTitle"]
            }
        }
    }
}