package com.gorigolilagmail.kyutechapp2018.client

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.view.fragment.*

/**
 * Created by pokotsun on 18/03/11.
 */

interface ITabItems {
    val fragments: Array<Fragment>
    val icons: Array<Int>
    val titles: Array<String>
    val selectedIcons: Array<Int>
    var selectedTab: TabLayout.Tab?
    fun getScheduleFragment(): UserScheduleFragment
}

class TabItems: ITabItems {

    private var scheduleFragment = UserScheduleFragment.newInstance(1)

    override val fragments: Array<Fragment> = arrayOf(
            NewsHeadingListFragment.newInstance(0), getScheduleFragment(),
            SchoolBusFragment.newInstance(), SettingFragment.newInstance()
    )

    override val icons: Array<Int> = arrayOf(
            R.mipmap.news_gray, R.mipmap.schedule_gray,
            R.mipmap.bus_gray, R.mipmap.setting_gray
    )

    override val titles: Array<String> = arrayOf("お知らせ", "時間割(第1クォーター)", "バス情報", "その他")

    override val selectedIcons: Array<Int> = arrayOf(
            R.mipmap.news_blue, R.mipmap.schedule_blue,
            R.mipmap.bus_blue, R.mipmap.setting_blue
    )

    override var selectedTab: TabLayout.Tab? = null

    // TabにセットされているScheduleFragmentにアクセスするためのもの
    override fun getScheduleFragment(): UserScheduleFragment = try {
        scheduleFragment
    } catch(e: NullPointerException) {
        scheduleFragment = UserScheduleFragment.newInstance(3)
        scheduleFragment
    }
}
