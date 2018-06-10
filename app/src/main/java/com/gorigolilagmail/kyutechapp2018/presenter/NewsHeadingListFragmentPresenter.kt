package com.gorigolilagmail.kyutechapp2018.presenter

import com.gorigolilagmail.kyutechapp2018.client.RetrofitServiceGenerator.createService
import com.gorigolilagmail.kyutechapp2018.model.NewsHeading
import com.gorigolilagmail.kyutechapp2018.view.adapter.NewsHeadingListAdapter
import com.gorigolilagmail.kyutechapp2018.view.fragment.MvpNewsHeadingListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface MvpNewsHeadingListFragmentPresenter {
    fun setNewsHeadings2ListAdapter(adapter: NewsHeadingListAdapter)
    fun onNewsHeadingsListClicked(position: Int, adapter: NewsHeadingListAdapter)
}

class NewsHeadingListFragmentPresenter(private val view: MvpNewsHeadingListView)
    : MvpNewsHeadingListFragmentPresenter {

    override fun setNewsHeadings2ListAdapter(adapter: NewsHeadingListAdapter) {
        createService().listNewsHeadings()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{ view.visibleProgressbar() }
                .doOnComplete { view.invisibleProgressbar() }
                .subscribe { apiRequest ->
                    val newsHeadings: List<NewsHeading> = apiRequest.results
                    adapter.items = newsHeadings
                    view.setItems2ListAdapter(adapter)
                }
    }

    // NewsHeadingのListItemが押された時の挙動
    override fun onNewsHeadingsListClicked(position: Int, adapter: NewsHeadingListAdapter) {
        val item = adapter.items[position]
        view.goToNewsListActivity(item.name, item.newsHeadingCode)
    }


}