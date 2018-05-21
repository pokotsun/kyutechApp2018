package com.gorigolilagmail.kyutechapp2018.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.view.adapter.NewsListAdapter
import kotlinx.android.synthetic.main.activity_news_list.*

class NewsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)

        val newsHeadingName: String = intent.getStringExtra("newsHeadingName")
        val newsHeadingCode: Int = intent.getIntExtra("newsHeadingCode", 0)

        // toolbarの設定
        tool_bar.title = ""
        toolbar_title.text = newsHeadingName
        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val listAdapter = NewsListAdapter(this)
        val items = mutableListOf<String>()
        for(i in 0 until 30) {
            items.add(i, "$newsHeadingName${i + 1}")
        }
        listAdapter.items = items
        news_list.adapter = listAdapter

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
}
