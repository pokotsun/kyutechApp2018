package com.gorigolilagmail.kyutechapp2018.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.model.NewsHeading
import com.gorigolilagmail.kyutechapp2018.view.adapter.NewsHeadingListAdapter
import kotlinx.android.synthetic.main.fragment_news.*

/**
 * Created by pokotsun on 18/03/10.
 */
class NewsFragment: Fragment() {

    private val newsHeadings: List<NewsHeading> by lazy {
        NewsHeading.getList(activity.applicationContext)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_news, container, false)
    }

    override fun onResume() {
        super.onResume()

        val adapter = NewsHeadingListAdapter(context)
        Log.d("newsHeadings", "$newsHeadings")
        adapter.items = newsHeadings
        news_list.adapter = NewsHeadingListAdapter(context)
    }

    companion object {
        fun newInstance(page: Int): NewsFragment {
            val args: Bundle = Bundle().apply {
                putInt("page", page)
            }
            return NewsFragment().apply {
                arguments = args
            }
        }
    }
}