package com.gorigolilagmail.kyutechapp2018.view.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Html
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.model.AttachmentInfo
import com.gorigolilagmail.kyutechapp2018.model.News
import com.gorigolilagmail.kyutechapp2018.model.NewsInfo
import com.jakewharton.rxbinding2.view.attaches
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.themedToolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.nestedScrollView

class NewsDetailActivity : AppCompatActivity() {

    private val ui: NewsDetailActivityUI = NewsDetailActivityUI()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val news: News = intent.getParcelableExtra(NewsDetailActivity.NEWS_EXTRA)

        ui.newsInfos = news.infos
        ui.newsAttachmentInfos = news.attachmentInfos

        Log.d("News情報", "$news")
        ui.setContentView(this)
        setSupportActionBar(ui.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id: Int = item?.itemId ?: android.R.id.home

        return when(id) {
        // 戻るボタン
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    companion object {
        private const val NEWS_EXTRA: String = "news"
        fun intent(context: Context, news: News): Intent =
                Intent(context, NewsDetailActivity::class.java)
                        .putExtra(NEWS_EXTRA, news)
    }

    private class NewsDetailActivityUI: AnkoComponent<NewsDetailActivity> {
        var toolBar: Toolbar? = null
        var newsInfos: List<NewsInfo>? = null
        var newsAttachmentInfos: List<AttachmentInfo>? = null

        override fun createView(ui: AnkoContext<NewsDetailActivity>): View = ui.run {
            coordinatorLayout {
                fitsSystemWindows = true
                appBarLayout {
                    layoutParams.apply {
                        background = ContextCompat.getDrawable(context, R.color.kyuTechMainColor)
                    }

                    toolBar = themedToolbar(theme= R.style.ToolbarColoredBackArrow) {
                        title = "お知らせ詳細画面"
                        setTitleTextColor(Color.WHITE)
                    }.lparams(width= matchParent, height= wrapContent) {
                        scrollFlags = SCROLL_FLAG_SCROLL or SCROLL_FLAG_ENTER_ALWAYS
                    }
                }.lparams(width= matchParent, height = dip(60))

                nestedScrollView {
                    verticalLayout {
                        newsInfos?.forEach { newsInfo ->
                            //                            R.style.AppTheme
                            textView(newsInfo.title) {
                                textColor = Color.WHITE
                                textSize = 20.toFloat()
                                backgroundColor = Color.GRAY
                                padding = dip(4)
                            }.lparams(width = matchParent, height = wrapContent)

                            textView(newsInfo.content) {
                            }.lparams(width = matchParent, height = wrapContent) {
                                margin = dip(16)
                            }
                        }

                        // 添付情報
                        newsAttachmentInfos?.forEach { attachmentInfo ->
                            textView(attachmentInfo.title) {
                                textColor = Color.WHITE
                                textSize = 20.toFloat()
                                backgroundColor = Color.GRAY
                                padding = dip(4)
                            }.lparams(width = matchParent, height = wrapContent)

                            textView(attachmentInfo.linkName) {
                                textColor = ContextCompat.getColor(context, R.color.kyuTechMainColor)
                                paintFlags = this.paintFlags or Paint.UNDERLINE_TEXT_FLAG // 下線を引く
                            }.lparams(width = matchParent, height = wrapContent) {
                                margin = dip(16)

                                onClick { browse(attachmentInfo.url) } // urlを閲覧する
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
