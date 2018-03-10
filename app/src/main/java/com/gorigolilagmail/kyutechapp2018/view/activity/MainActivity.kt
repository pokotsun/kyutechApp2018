package com.gorigolilagmail.kyutechapp2018.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.Log
import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.view.adapter.TabAdapter
import com.gorigolilagmail.kyutechapp2018.view.fragment.NewsFragment
import com.gorigolilagmail.kyutechapp2018.view.fragment.TestFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private val fragments: Array<Fragment> = arrayOf(
            NewsFragment.newInstance(0), TestFragment.newInstance(1),
            TestFragment.newInstance(2), TestFragment.newInstance(3)
    )

    private val tabIcons: Array<Int> = arrayOf(
            R.mipmap.ic_launcher, R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher_round
    )

    private val tabIconsSelected: Array<Int> = arrayOf(
            R.mipmap.ic_launcher_round, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher_round, R.mipmap.ic_launcher
    )

    private var selectedTab: TabLayout.Tab? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = TabAdapter(supportFragmentManager, fragments)

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
                selectedTab?.icon = ContextCompat.getDrawable(this@MainActivity, tabIcons[selectedTab!!.position])
                tab?.icon = ContextCompat.getDrawable(this@MainActivity, tabIconsSelected[tab!!.position])
                selectedTab = tab
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

    private fun initializeTabIcons() {
        selectedTab = tab_layout.getTabAt(tab_layout.selectedTabPosition)

        for(i in 0 until tab_layout.tabCount) {
            val tab: TabLayout.Tab = tab_layout.getTabAt(i)?: throw NullPointerException("can't get Tab")
            tab.icon = ContextCompat.getDrawable(this, tabIcons[tab.position])
        }
    }
}

