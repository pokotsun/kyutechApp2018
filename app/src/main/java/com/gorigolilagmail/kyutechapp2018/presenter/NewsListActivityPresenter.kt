package com.gorigolilagmail.kyutechapp2018.presenter

import android.util.Log
import com.gorigolilagmail.kyutechapp2018.client.RetrofitServiceGenerator.createService
import com.gorigolilagmail.kyutechapp2018.model.News
import com.gorigolilagmail.kyutechapp2018.view.activity.NewsListMvpAppCompatActivity
import com.gorigolilagmail.kyutechapp2018.view.adapter.NewsListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface ImplNewsListActivityPresenter {
    fun setNews2list(adapter: NewsListAdapter, newsHeadingCode: Int)
    fun onNewsListItemClick(adapter: NewsListAdapter, position: Int)
    fun onScrolled2lastItem(adapter: NewsListAdapter)
}

class NewsListActivityPresenter(private val view: NewsListMvpAppCompatActivity): Presenter, ImplNewsListActivityPresenter {
    private var nextUrl: String = ""

    // Apiサーバーから取得したNews一覧をListViewにセット
    override fun setNews2list(adapter: NewsListAdapter, newsHeadingCode: Int) {
        createService().listNewsByNewsHeadingCode(newsHeadingCode)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showProgress() }
                .doOnComplete { view.hideProgress() }
                .doOnError { view.hideProgress() }
                .subscribe { apiRequest ->
                    nextUrl = apiRequest.next?: ""
                    val newsList = apiRequest.results
                    adapter.items = newsList
                    view.setAdapter2list(adapter)
                }
    }

    override fun onNewsListItemClick(adapter: NewsListAdapter, position: Int) {
        val item: News = adapter.items[position]
        view.goToNewsDetailActivity(item)
    }

    // Scroll情報を監視して下の方に行った時に次の100件を取得するようにする
    override fun onScrolled2lastItem(adapter: NewsListAdapter) {
        // スクロールイベントを取得
        view.getRxAbsListViewScrollEvent()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .filter { scrollEvent ->
//                    Log.d("scrollEvents", "${scrollEvent.firstVisibleItem()}, ${scrollEvent.visibleItemCount()} ${scrollEvent.totalItemCount()}")
                    scrollEvent.firstVisibleItem() + scrollEvent.visibleItemCount() >= scrollEvent.totalItemCount()
                }
                .filter { nextUrl.isNotEmpty() } // next_urlが空ではないか？
                .take(1) // いっぱいイベント拾ってしまうがとりあえずここで渋滞するので上からひとつだけ発火させる
                .flatMap {  // データ取得のObservableに処理をつなげる
                    createService().getNextNewsList(nextUrl)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSubscribe { view.showProgress() }
                            .doOnComplete { view.hideProgress() }
                }
                .doOnComplete { onScrolled2lastItem(adapter) }
                .subscribe { apiRequest ->
                    nextUrl = apiRequest.next?: ""
                    adapter.items.plusAssign(apiRequest.results)
                    adapter.notifyDataSetChanged()

                    if(nextUrl.isNullOrEmpty()) {
                        view.showShortSnackbarWithoutView("一番古いお知らせ${apiRequest.results.size}件を取得しました")
                    }
                    else {
                        view.showShortSnackbarWithoutView("次のお知らせ${apiRequest.results.size}件を取得しました")
                    }
                }
    }
}