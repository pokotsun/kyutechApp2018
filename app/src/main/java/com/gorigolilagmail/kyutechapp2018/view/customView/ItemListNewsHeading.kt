package com.gorigolilagmail.kyutechapp2018.view.customView

import android.content.Context
import android.graphics.Color
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
                    padding = dip(12)
                    gravity = Gravity.CENTER_VERTICAL

                    circularTextView {
                        id = circularNewsHeadingId
                        text = "呼"
                        textColor  = Color.WHITE
                        textSize = 20f
                        gravity = Gravity.CENTER

                    }.lparams(width = dip(35), height = dip(35)) {
                        margin = dip(4)
                    }

                    textView("学生呼び出し") {
                        id = newsHeadingId
                        textSize = 20f
                        textColor = Color.BLACK
                    }.lparams(width = dip(200), height = wrapContent) {
                        leftMargin = dip(25)
                        rightOf(circularNewsHeadingId)
                        centerVertically()
                    }

                    textView("2018/3/10 18:00") {
                        id = updatedDateId
                        textColor = Color.BLACK
                        textSize = 9f
                        textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                    }.lparams(width = dip(50), height = wrapContent) {
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

        circularNewsHeading.text = item.headingCharacter
        circularNewsHeading.setCircleBackgroundColor(item.color)
        circularNewsHeading.setStrokeWidth(0f)

        newsHeading.text = item.headingName
        updatedDate.text = "${item.updatedDate} 18:00"
    }
}