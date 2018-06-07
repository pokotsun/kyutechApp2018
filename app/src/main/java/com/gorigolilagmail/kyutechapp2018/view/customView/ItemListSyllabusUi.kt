package com.gorigolilagmail.kyutechapp2018.view.customView

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.extensions.circularTextView
import com.gorigolilagmail.kyutechapp2018.model.Syllabus
import org.jetbrains.anko.*


class ItemListSyllabusUi(context: Context): LinearLayout(context), AnkoComponent<Context> {
    private val circularSyllabusKindId = View.generateViewId()

    private val titleId = View.generateViewId()
    private val professorNameId = View.generateViewId()

    override fun createView(ui: AnkoContext<Context>): View =
            ui.run {
                relativeLayout {
                    lparams(width= matchParent, height= wrapContent)
                    padding = dip(4)
                    gravity = Gravity.CENTER_VERTICAL

                    circularTextView {
                        id = circularSyllabusKindId
                        text = "必"
                        textColor  = Color.WHITE
                        textSize = 10f
                        gravity = Gravity.CENTER

                    }.lparams(width = dip(30), height = dip(30)) {
                        margin = dip(2)
                    }

                    textView("離散数学") {
                        id = titleId
                        textSize = 14f
                        textColor = Color.BLACK
                        singleLine = true
                        ellipsize = TextUtils.TruncateAt.END
                    }.lparams(width = dip(150), height = wrapContent) {
                        leftMargin = dip(5)
                        rightOf(circularSyllabusKindId)
                        centerVertically()
                    }

                    textView("2018/3/10 18:00") {
                        id = professorNameId
                        textColor = Color.BLACK
                        textSize = 7f
                        textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                        singleLine = true
                        ellipsize = TextUtils.TruncateAt.END
                    }.lparams(width = dip(50), height = wrapContent) {
                        rightOf(titleId)
                        centerVertically()
                        alignParentRight()
                    }
                }
            }.apply {this@ItemListSyllabusUi.addView(this) }

    // Itemをviewにセットしていく
    fun setItem(item: Syllabus) {
        val circularNewsHeading = find<CircularTextView>(circularSyllabusKindId)
        val newsHeading = find<TextView>(titleId)
        val updatedDate = find<TextView>(professorNameId)

//        Log.d("アイテム", item.toString())

        circularNewsHeading.text = "必"
        circularNewsHeading.setCircleBackgroundColor(ContextCompat.getColor(context, R.color.newsTopic1))
        circularNewsHeading.setStrokeWidth(0f)

        newsHeading.text = item.title
        updatedDate.text = item.teacherName
    }
}