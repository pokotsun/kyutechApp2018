package com.gorigolilagmail.kyutechapp2018.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by pokotsun on 18/03/10.
 */

class TabAdapter(fm: FragmentManager, private val fragments: Array<Fragment>): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.count()

    override fun getPageTitle(position: Int): CharSequence = ""
}