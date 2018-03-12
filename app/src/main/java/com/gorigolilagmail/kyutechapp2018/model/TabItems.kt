package com.gorigolilagmail.kyutechapp2018.model

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.view.fragment.NewsFragment
import com.gorigolilagmail.kyutechapp2018.view.fragment.ScheduleFragment
import com.gorigolilagmail.kyutechapp2018.view.fragment.TestFragment

/**
 * Created by pokotsun on 18/03/11.
 */

interface ITabItems {
    val fragments: Array<Fragment>
    val icons: Array<Int>
    val selectedIcons: Array<Int>
    var selectedTab: TabLayout.Tab?
}

class TabItems: ITabItems {
    override val fragments: Array<Fragment> = arrayOf(
            NewsFragment.newInstance(0), ScheduleFragment.newInstance(1),
            TestFragment.newInstance(2), TestFragment.newInstance(3)
    )

    override val icons: Array<Int> = arrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher_round
    )

    override val selectedIcons: Array<Int> = arrayOf(
            R.mipmap.ic_launcher_round, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher_round, R.mipmap.ic_launcher
    )

    override var selectedTab: TabLayout.Tab? = null
}
