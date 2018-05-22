package com.gorigolilagmail.kyutechapp2018.view.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.gorigolilagmail.kyutechapp2018.R
import kotlinx.android.synthetic.main.activity_news_detail.*
import org.jetbrains.anko.*

class NewsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        setSupportActionBar(toolbar)
    }

    override fun onResume() {
        super.onResume()

        toolbar.title = "はいっているよー"
//
//        val infoLayout = linearLayout().apply {
//            layoutParams.apply {
//                width = matchParent
//                height = wrapContent
////                setHorizontalGravity(dip(5))
//            }
//            textView("わたしはねこ？") {
//                width = matchParent
//                height = wrapContent
//                background = getDrawable(R.color.kyuTechMainColor)
//            }
//            textView("いいえ、犬です。") {
//                width = matchParent
//                height = wrapContent
//                backgroundColor = Color.BLACK
//            }
//        }
//
//        Log.d("nestedScroll", "${nested_scrollview != null}")
//        if(nested_scrollview != null) {
//            nested_scrollview.addView(infoLayout)
//        }
//
//        for(i in 0 until 5) {
//            nested_scrollview.addView(infoLayout)
//        }
    }
}
