package com.gorigolilagmail.kyutechapp2018.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.Log
import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.model.ITabItems
import com.gorigolilagmail.kyutechapp2018.model.TabItems
import com.gorigolilagmail.kyutechapp2018.presenter.MainActivityPresenter
import com.gorigolilagmail.kyutechapp2018.view.adapter.TabAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),  ViewPager.OnPageChangeListener {

    private val presenter: MainActivityPresenter = MainActivityPresenter()

    private val tabItems: ITabItems = TabItems()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = TabAdapter(supportFragmentManager, tabItems.fragments)

        view_pager.adapter = adapter
        view_pager.addOnPageChangeListener(this)
        tab_layout.setupWithViewPager(view_pager)

        initializeTabIcons() // タブアイコンの初期化処理

        // toolbarの設定
        tool_bar.title = ""
        toolbar_title.text = "お知らせ"
        setSupportActionBar(tool_bar)

        tab_layout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabItems.selectedTab?.icon = ContextCompat.getDrawable(this@MainActivity, tabItems.icons[tabItems.selectedTab!!.position])
                tab?.icon = ContextCompat.getDrawable(this@MainActivity, tabItems.selectedIcons[tab!!.position])
                tabItems.selectedTab = tab
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })
    }

    override fun onPageScrollStateChanged(state: Int) {
        Log.d("MainActivity", "onPageStateChanged() position = $state")

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        Log.d("MainActivity", "onPageScrolled() position = $position")

    }

    override fun onPageSelected(position: Int) {
        Log.d("MainActivity", "onPageSelected() position = $position")
    }


    // タブのアイコンを初期化
    private fun initializeTabIcons() {
        tabItems.selectedTab = tab_layout.getTabAt(tab_layout.selectedTabPosition)

        for(i in 0 until tab_layout.tabCount) {
            val tab: TabLayout.Tab = tab_layout.getTabAt(i)?: throw NullPointerException("can't get Tab")
            tab.icon = ContextCompat.getDrawable(this, tabItems.icons[tab.position])
        }
    }
}

