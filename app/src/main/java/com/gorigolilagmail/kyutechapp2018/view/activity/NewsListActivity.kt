package com.gorigolilagmail.kyutechapp2018.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gorigolilagmail.kyutechapp2018.R
import kotlinx.android.synthetic.main.activity_news_list.*

class NewsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)

        // toolbarの設定
        tool_bar.title = ""
        toolbar_title.text = "お知らせ"
        setSupportActionBar(tool_bar)
    }
}
