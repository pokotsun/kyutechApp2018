package com.gorigolilagmail.kyutechapp2018.view.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.model.News
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.themedToolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.nestedScrollView

class NewsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val news: News = intent.getParcelableExtra(NewsDetailActivity.NEWS_EXTRA)

        val ui = NewsDetailActivityUI(news)

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

    private class NewsDetailActivityUI(private val news: News): AnkoComponent<NewsDetailActivity> {
        var toolBar: Toolbar? = null

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
                        news.infos.forEach { newsInfo ->
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
                        news.attachmentInfos.forEach { attachmentInfo ->
                            textView(attachmentInfo.title) {
                                textColor = Color.WHITE
                                textSize = 20.toFloat()
                                backgroundColor = Color.GRAY
                                padding = dip(4)
                            }.lparams(width = matchParent, height = wrapContent)

                            textView(attachmentInfo.linkName) {
                                textColor = ContextCompat.getColor(context, R.color.kyuTechMainColor)
                                paintFlags = this.paintFlags or Paint.UNDERLINE_TEXT_FLAG // 下線を引く
                                onClick { browse(attachmentInfo.url) } // urlを閲覧する
                            }.lparams(width = matchParent, height = wrapContent) {
                                margin = dip(16)
                            }
                        }



                        textView("ソースURL") {
                            textColor = Color.WHITE
                            textSize = 20.toFloat()
                            backgroundColor = Color.GRAY
                            padding = dip(4)
                        }.lparams(width = matchParent, height = wrapContent)

                        textView(news.sourceUrl) {
                            textColor = ContextCompat.getColor(context, R.color.kyuTechMainColor)
                            paintFlags = this.paintFlags or Paint.UNDERLINE_TEXT_FLAG // 下線を引く
                            onClick { browse(news.sourceUrl) } // urlを閲覧する
                        }.lparams(width = matchParent, height = wrapContent) {
                            margin = dip(16)
                        }
                    }.lparams(width= matchParent, height = matchParent)
                }.lparams(width=matchParent, height= matchParent) {
                    behavior = AppBarLayout.ScrollingViewBehavior()
                }
            }
        }
    }
}
