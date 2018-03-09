package com.gorigolilagmail.kyutechapp2018

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tab_layout.*

class MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabs = arrayOf(
                tab_layout.newTab(),
                tab_layout.newTab(),
                tab_layout.newTab())
//        tabs.forEachIndexed { i, v ->
//            v.text = "タブ$i"
//            tab_layout.addTab(v)
//        }

        val adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): android.support.v4.app.Fragment {
                return TestFragment.newInstance(position + 1)
            }

            override fun getCount(): Int {
                return 4
            }

            override fun getPageTitle(position: Int): CharSequence {
                return "タブ${position + 1}"
            }
        }

        view_pager.adapter = adapter
        view_pager.addOnPageChangeListener(this)
        tab_layout.setupWithViewPager(view_pager)

        // Custom Layoutにする
        val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val tab1 = tab_layout.getTabAt(0)
        tab1?.text = "Home"

//        val tab1View: View = inflater.inflate(R.layout.tab_layout, null)
        val tab1View = CustomTab(this)
        tab1View.setText("なんじゃそりゃ")
        tab1?.customView = tab1View
//        tab_text.text = "こらんばんだ"

        val tab2 = tab_layout.getTabAt(2)
//        tab1?.text = "こりゃなんだ"

        val tab2View = CustomTab(this)
        tab2View.setText("わかんまべ")
        tab2?.customView = tab2View
//
//        val tab2View: View = inflater.inflate(R.layout.tab_layout, null)
//        tab2?.customView = tab2View
//        tab_text.text = "なんだば"
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

}

class TestFragment : Fragment() {

    companion object {
        fun newInstance(page: Int): TestFragment {
            val args: Bundle = Bundle().apply {
                putInt("page", page)
            }
            return TestFragment().apply {
                arguments = args
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val page = arguments.getInt("page", 0)
        val view = inflater!!.inflate(R.layout.fragment_test, container, false)
        view.findViewById<TextView>(R.id.page_text).text = "Page$page"
        return view
    }

}
