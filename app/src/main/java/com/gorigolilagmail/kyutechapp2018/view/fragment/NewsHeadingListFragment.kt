package com.gorigolilagmail.kyutechapp2018.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ProgressBar
import com.gorigolilagmail.kyutechapp2018.client.ApiClient
import com.gorigolilagmail.kyutechapp2018.client.RetrofitServiceGenerator.createService
import com.gorigolilagmail.kyutechapp2018.model.ApiRequest
import com.gorigolilagmail.kyutechapp2018.model.NewsHeading
import com.gorigolilagmail.kyutechapp2018.presenter.NewsHeadingListFragmentPresenter
import com.gorigolilagmail.kyutechapp2018.view.activity.MvpView
import com.gorigolilagmail.kyutechapp2018.view.activity.NewsListActivity
import com.gorigolilagmail.kyutechapp2018.view.adapter.NewsHeadingListAdapter
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.*

/**
 * Created by pokotsun on 18/03/10.
 */

interface MvpNewsHeadingListView: MvpView {
    fun goToNewsListActivity(newsName: String, newsHeadingCode: Int)
    fun invisibleProgressbar()
    fun visibleProgressbar()
    fun setItems2ListAdapter(adapter: NewsHeadingListAdapter)
}

class NewsHeadingListFragment: MvpAppCompatFragment(), MvpNewsHeadingListView {

    private val ui = NewsFragmentUi()
    private val presenter = NewsHeadingListFragmentPresenter(this)

//    private val newsHeadings: List<NewsHeading> by lazy {
//        NewsHeading.getList(activity.applicationContext)
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            ui.createView(AnkoContext.create(context, this))


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NewsHeadingListAdapter(context)
        presenter.setNewsHeadings2ListAdapter(adapter)

        ui.newsList?.setOnItemClickListener { _, _, position, _ ->
            presenter.onNewsHeadingsListClicked(position, adapter)
        }
    }

    override fun goToNewsListActivity(newsName: String, newsHeadingCode: Int) = Intent(context, NewsListActivity::class.java).run {
        putExtra("newsHeadingName", newsName)
        putExtra("newsHeadingCode", newsHeadingCode)
        startActivity(this)
    }

    // Progerssbarを扱う
    override fun visibleProgressbar() { ui.progressBar?.visibility = View.VISIBLE }
    override fun invisibleProgressbar() { ui.progressBar?.visibility = View.GONE }

    override fun setItems2ListAdapter(adapter: NewsHeadingListAdapter) {
        ui.newsList?.adapter = adapter
    }


    private class NewsFragmentUi: AnkoComponent<NewsHeadingListFragment> {
        var newsList: ListView? = null
        var progressBar: ProgressBar? = null
        override fun createView(ui: AnkoContext<NewsHeadingListFragment>): View = ui.run {
            frameLayout {
                newsList = listView {
                }.lparams(width = matchParent, height = wrapContent)

                progressBar = progressBar {
                    visibility = View.GONE
                }.lparams {
                    gravity = Gravity.CENTER
                }
            }
        }
    }

    companion object {
        fun newInstance(page: Int): NewsHeadingListFragment {
            val args: Bundle = Bundle().apply {
                putInt("page", page)
            }
            return NewsHeadingListFragment().apply {
                arguments = args
            }
        }
    }
}