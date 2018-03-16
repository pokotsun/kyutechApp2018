package com.gorigolilagmail.kyutechapp2018.view.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.gorigolilagmail.kyutechapp2018.model.NewsHeading
import com.gorigolilagmail.kyutechapp2018.view.activity.NewsListActivity
import com.gorigolilagmail.kyutechapp2018.view.adapter.NewsHeadingListAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onItemLongClick

/**
 * Created by pokotsun on 18/03/10.
 */
class NewsFragment: Fragment() {

    private val ui = NewsFragmentUi()

    private val newsHeadings: List<NewsHeading> by lazy {
        NewsHeading.getList(activity.applicationContext)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ui.createView(AnkoContext.create(context, this))
    }

    override fun onResume() {
        super.onResume()

        val adapter = NewsHeadingListAdapter(context)
        adapter.items = newsHeadings
        ui.newsList?.adapter = adapter
        ui.newsList?.setOnItemClickListener { _, _, position, _ ->
            Intent(context, NewsListActivity::class.java).run {
                val item = adapter.items[position]
                putExtra("news_name", item.headingName)
                putExtra("news_id", position)

                startActivity(this)
            }
        }



    }

    private class NewsFragmentUi: AnkoComponent<NewsFragment> {
        var newsList: ListView? = null
        override fun createView(ui: AnkoContext<NewsFragment>): View = ui.run {
            verticalLayout {
                newsList = listView {
                }.lparams(width = matchParent, height = wrapContent)
            }
        }
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