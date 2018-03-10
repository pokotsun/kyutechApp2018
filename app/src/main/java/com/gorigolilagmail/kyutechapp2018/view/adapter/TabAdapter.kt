package com.gorigolilagmail.kyutechapp2018.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.gorigolilagmail.kyutechapp2018.TestFragment

/**
 * Created by pokotsun on 18/03/10.
 */

class TabAdapter(fm: FragmentManager, private val fragments: Array<Fragment>): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): android.support.v4.app.Fragment = fragments[position]

    override fun getCount(): Int = fragments.count()

    override fun getPageTitle(position: Int): CharSequence = ""
}