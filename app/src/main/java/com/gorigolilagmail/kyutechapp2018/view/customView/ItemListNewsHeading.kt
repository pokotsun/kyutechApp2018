package com.gorigolilagmail.kyutechapp2018.view.customView

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.gorigolilagmail.kyutechapp2018.extensions.circularTextView
import com.gorigolilagmail.kyutechapp2018.model.NewsHeading
import org.jetbrains.anko.*

/**
 * Created by pokotsun on 18/03/10.
 */

class ItemListNewsHeadingUi(context: Context): LinearLayout(context), AnkoComponent<Context> {
    private val circularNewsHeadingId = View.generateViewId()
    private val newsHeadingId = View.generateViewId()
    private val updatedDateId = View.generateViewId()

    override fun createView(ui: AnkoContext<Context>): View =
            ui.run {
                relativeLayout {
                    lparams(width= matchParent, height= wrapContent)
                    padding = dip(8)
                    gravity = Gravity.CENTER_VERTICAL

                    circularTextView {
                        id = circularNewsHeadingId
                        text = "呼"
                        textColor  = Color.WHITE
                        textSize = 16f
                        gravity = Gravity.CENTER

                    }.lparams(width = dip(35), height = dip(35)) {
                        margin = dip(2)
                    }

                    textView("学生呼び出し") {
                        id = newsHeadingId
                        textSize = 16f
                        textColor = Color.BLACK
                    }.lparams(width = dip(200), height = wrapContent) {
                        leftMargin = dip(20)
                        rightOf(circularNewsHeadingId)
                        centerVertically()
                    }

                    textView("2018/3/10 18:00") {
                        id = updatedDateId
                        textColor = Color.BLACK
                        textSize = 9f
                        textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                    }.lparams(width = dip(70), height = wrapContent) {
                        rightOf(newsHeadingId)
                        centerVertically()
                        alignParentRight()
                    }
                }
            }.apply { this@ItemListNewsHeadingUi.addView(this) }

    // Itemをviewにセットしていく
    fun setItem(item: NewsHeading) {
        val circularNewsHeading = find<CircularTextView>(circularNewsHeadingId)
        val newsHeading = find<TextView>(newsHeadingId)
        val updatedDate = find<TextView>(updatedDateId)

//        Log.d("アイテム", item.toString())

        circularNewsHeading.text = item.shortName
        circularNewsHeading.setCircleBackgroundColor(Color.parseColor(item.colorCode))
        circularNewsHeading.setStrokeWidth(0f)

        newsHeading.text = item.name
        updatedDate.text = item.updatedAt
    }
}