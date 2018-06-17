package com.gorigolilagmail.kyutechapp2018.view.customView

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.model.News
import org.jetbrains.anko.*

/**
 * Created by pokotsun on 18/03/16.
 */

class ItemListNewsUi(context: Context): LinearLayout(context), AnkoComponent<Context> {
    private val mainTitleId: Int = View.generateViewId()
    private val subTitleId: Int = View.generateViewId()

    override fun createView(ui: AnkoContext<Context>): View = ui.run {
        linearLayout {
            layoutParams = LayoutParams(matchParent, dip(60))
            padding = dip(18)
            leftPadding = dip(4)
            gravity = Gravity.CENTER_VERTICAL

            textView("歴史学?U(01) 1限") {
                id = mainTitleId
                textSize = 13f
                ellipsize = TextUtils.TruncateAt.MIDDLE
                singleLine = true
            }.lparams(0, wrapContent) {
                weight = 7f
            }

            textView("2018年8月11日") {
                id = subTitleId
                textSize = 10f
                textColor = R.color.gray_little_dark
                gravity = Gravity.END
                singleLine = true
                ellipsize = TextUtils.TruncateAt.END
            }.lparams(0, wrapContent) {
                weight = 3f
            }
        }
    }.apply { this@ItemListNewsUi.addView(this) }

    fun setItem(news: News) {
        val titleInfos: HashMap<String, String> = news.getTitleInfos()

        val mainTitleTextView: TextView? = find(mainTitleId)
        val subTitleTextView: TextView? = find(subTitleId)

        mainTitleTextView?.text = titleInfos["mainTitle"]

//         サブタイトルフィールドの処理
        if(subTitleTextView != null) {
            // サブタイトルの欄が空白ならばそこのViewを空白にする
            if((titleInfos["subTitle"] ?: "").isNullOrEmpty()) {
                Log.d("titlesInfo", "${titleInfos}")
                subTitleTextView.visibility = View.GONE
            } else {
                subTitleTextView.text = titleInfos["subTitle"]
            }
        }
    }
}