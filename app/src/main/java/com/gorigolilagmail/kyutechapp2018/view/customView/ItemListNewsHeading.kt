package com.gorigolilagmail.kyutechapp2018.view.customView

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.extensions.circularTextView
import com.gorigolilagmail.kyutechapp2018.model.NewsHeading
import kotlinx.android.synthetic.main.news_heading_list_item.view.*
import org.jetbrains.anko.*

/**
 * Created by pokotsun on 18/03/10.
 */

//class ItemListNewsHeading @JvmOverloads constructor(context: Context,
//                                                    attrs: AttributeSet? = null,
//                                                    defStyleAttr: Int = 0)
//    : LinearLayout(context, attrs, defStyleAttr) {
//
//    private var view: View? = null
//
//    init {
////        view = ui.createView(AnkoContext.create(context, this))
//        view = LayoutInflater.from(context).inflate(R.layout.news_heading_list_item, this)
//    }
//
//    fun setItem(item: NewsHeading) {
//        Log.d("setItemNews", "$item")
////        val circularNewsHeading = view?.findViewById<CircularTextView>(circularNewsHeading)
////        val newsHeading = view?.findViewById<TextView>(newsHeading)
////        val updatedDate = view?.findViewById<TextView>(updated_date)
////
////        circularNewsHeading?.text = item.headingCharacter
////        circularNewsHeading?.setCircleBackgroundColor(item.color)
////        circularNewsHeading?.setStrokeWidth(0.toFloat())
////        newsHeading?.text = item.headingName
////        updatedDate?.text = "${item.updatedDate} 更新"
//
//        circularNewsHeading.text = item.headingCharacter
//        circularNewsHeading.setCircleBackgroundColor(item.color)
//        circularNewsHeading.setStrokeWidth(0.toFloat())
//        newsHeading.text = item.headingName
//        updatedDate.text = item.updatedDate
//    }
//
//
//}

class ItemListNewsHeadingUi(context: Context): LinearLayout(context), AnkoComponent<Context> {
    val circular_news_heading = View.generateViewId()
    val news_heading = View.generateViewId()
    val updated_date = View.generateViewId()

    override fun createView(ui: AnkoContext<Context>): View =
            ui.run {
                relativeLayout {
                    lparams(width= matchParent, height= wrapContent)
                    padding = dip(12)
                    gravity = Gravity.CENTER_VERTICAL

                    circularTextView {
                        id = circular_news_heading
                        text = "呼"
                        textColor  = Color.BLACK
                        textSize = 20f
                        gravity = Gravity.CENTER

                    }.lparams(width = dip(35), height = dip(35)) {
                        margin = dip(4)
                    }

                    textView("学生呼び出し") {
                        id = news_heading
                        textSize = 20f
                        textColor = Color.BLACK
                    }.lparams(width = dip(200), height = wrapContent) {
                        leftMargin = dip(50)
                        rightOf(circular_news_heading)
                        centerVertically()
                    }

                    textView("2018/3/10 18:00") {
                        textColor = Color.BLACK
                        textSize = 10f
                        textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                    }.lparams(width = dip(50), height = wrapContent) {
                        rightOf(news_heading)
                        centerVertically()
                        alignParentRight()
                    }
                }
            }.apply { this@ItemListNewsHeadingUi.addView(this) }

    fun update(item: NewsHeading) {
        find<CircularTextView>(circular_news_heading).text = item.headingCharacter
    }
}