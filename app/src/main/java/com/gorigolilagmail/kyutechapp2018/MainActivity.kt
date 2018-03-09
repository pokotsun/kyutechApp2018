package com.gorigolilagmail.kyutechapp2018

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabs = arrayOf(
                tab_layout.newTab(),
                tab_layout.newTab(),
                tab_layout.newTab())
        tabs.forEachIndexed { i, v ->
            v.text = "タブ$i"
            tab_layout.addTab(v)
        }
    }
}
