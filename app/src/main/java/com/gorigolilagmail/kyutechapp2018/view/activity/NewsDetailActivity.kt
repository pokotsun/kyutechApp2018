package com.gorigolilagmail.kyutechapp2018.view.activity

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import com.gorigolilagmail.kyutechapp2018.R
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.themedToolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.support.v4.nestedScrollView

class NewsDetailActivity : AppCompatActivity() {

    private val ui: NewsDetailActivityUI = NewsDetailActivityUI()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui.setContentView(this)
//        setContentView(R.layout.activity_news_detail)
//        setSupportActionBar(ui.toolBar)

    }


    private class NewsDetailActivityUI: AnkoComponent<NewsDetailActivity> {
        var toolBar: Toolbar? = null
        //        var infoContainer: LinearLayout? = null
        var infoContainer: NestedScrollView? = null


        override fun createView(ui: AnkoContext<NewsDetailActivity>): View = ui.run {
            coordinatorLayout {
                fitsSystemWindows = true

                appBarLayout {

                    layoutParams.apply {
                        background = ContextCompat.getDrawable(context, R.color.kyuTechMainColor)
                    }

                    toolBar = themedToolbar(theme= R.style.ToolbarColoredBackArrow) {

                        title = "はいっていないかも"
                    }.lparams(width= matchParent, height= wrapContent) {
                        scrollFlags = SCROLL_FLAG_SCROLL or SCROLL_FLAG_ENTER_ALWAYS
                    }
                }.lparams(width= matchParent, height = dip(60))

                infoContainer = nestedScrollView {
                    verticalLayout {
                        for(i in 0..5) {
//                            R.style.AppTheme
                            textView("私は犬かも") {
                                textColor = Color.WHITE
                                textSize = 20.toFloat()
                                backgroundColor = Color.GRAY
                                padding = dip(4)
                            }.lparams(width = matchParent, height = wrapContent)

                            textView("やっぱ違うかも") {

                            }.lparams(width = matchParent, height = wrapContent) {
                                margin = dip(16)
                            }
                        }
                    }.lparams(width= matchParent, height = matchParent)
                }.lparams(width=matchParent, height= matchParent) {
                    behavior = AppBarLayout.ScrollingViewBehavior()
                }
            }
        }
    }
}
