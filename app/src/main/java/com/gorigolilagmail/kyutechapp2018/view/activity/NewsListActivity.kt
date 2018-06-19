package com.gorigolilagmail.kyutechapp2018.view.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.client.ApiClient
import com.gorigolilagmail.kyutechapp2018.client.RetrofitServiceGenerator.createService
import com.gorigolilagmail.kyutechapp2018.model.ApiRequest
import com.gorigolilagmail.kyutechapp2018.model.News
import com.gorigolilagmail.kyutechapp2018.model.NewsHeading
import com.gorigolilagmail.kyutechapp2018.presenter.NewsListActivityPresenter
import com.gorigolilagmail.kyutechapp2018.view.adapter.NewsListAdapter
import com.jakewharton.rxbinding2.widget.AbsListViewScrollEvent
import com.jakewharton.rxbinding2.widget.RxAbsListView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_news_list.*


interface NewsListMvpAppCompatActivity: MvpView {
    fun showProgress()
    fun hideProgress()
    fun setAdapter2list(adapter: NewsListAdapter)
    fun goToNewsDetailActivity(item: News)
    fun getRxAbsListViewScrollEvent(): Observable<AbsListViewScrollEvent>
    fun showShortSnackbarWithoutView(msg: String)
}

class NewsListActivity : MvpAppCompatActivity(), NewsListMvpAppCompatActivity {

    private val presenter = NewsListActivityPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)

        val newsHeadingName: String = intent.getStringExtra("newsHeadingName")
        val newsHeadingCode: Int = intent.getIntExtra("newsHeadingCode", 357)

        // toolbarの設定
        initializeToolBar(newsHeadingName)

        val listAdapter = NewsListAdapter(this)

        // News情報を取得してリストで表示する
        presenter.setNews2list(listAdapter, newsHeadingCode)
        news_list.setOnItemClickListener { parent, view, position, id ->
            presenter.onNewsListItemClick(listAdapter, position)
        }

        presenter.onScrolled2lastItem(listAdapter)
    }

    override fun showProgress() { progress_bar.visibility = View.VISIBLE }
    override fun hideProgress() { progress_bar.visibility = View.GONE }

    override fun setAdapter2list(adapter: NewsListAdapter) {
        news_list.adapter = adapter
    }

    // 詳細ページに移動する
    override fun goToNewsDetailActivity(item: News) =
        NewsDetailActivity.intent(this@NewsListActivity, item).let { startActivity(it) }

    override fun getRxAbsListViewScrollEvent(): Observable<AbsListViewScrollEvent> =
        RxAbsListView.scrollEvents(news_list)

    override fun showShortSnackbarWithoutView(msg: String) = showShortSnackBar(msg, news_list_root)

    // toolbarの設定
    private fun initializeToolBar(newsHeadingName: String) {
        tool_bar.title = "" // Defaultで用意されているToolbarのTitleを無効化する
        toolbar_title.text = newsHeadingName
        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Homeボタンを表示するかどうか
        supportActionBar?.setHomeButtonEnabled(true) // Homeボタンを有効にするかどうか
    }

    // OptionItemが押された時の挙動を決める(Homeボタンとか)
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id: Int = item?.itemId ?: android.R.id.home
        return when(id) {
        // 戻るボタン
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
