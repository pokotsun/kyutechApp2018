package com.gorigolilagmail.kyutechapp2018.view.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.client.ApiClient
import com.gorigolilagmail.kyutechapp2018.client.RetrofitServiceGenerator.Companion.createService
import com.gorigolilagmail.kyutechapp2018.model.ApiRequest
import com.gorigolilagmail.kyutechapp2018.model.News
import com.gorigolilagmail.kyutechapp2018.view.adapter.NewsListAdapter
import com.jakewharton.rxbinding2.widget.RxAbsListView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_news_list.*
import com.jakewharton.rxbinding2.widget.AbsListViewScrollEvent
import io.reactivex.Observable


class NewsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)

        val newsHeadingName: String = intent.getStringExtra("newsHeadingName")
        val newsHeadingCode: Int = intent.getIntExtra("newsHeadingCode", 357)

        // toolbarの設定
        tool_bar.title = ""
        toolbar_title.text = newsHeadingName
        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val listAdapter = NewsListAdapter(this)

        val service: ApiClient = createService()
        service.listNewsByNewsHeadingCode(newsHeadingCode)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<ApiRequest<News>> {
                    override fun onComplete() {
                        //すべてStreamを流しきった時に呼ばれる
                        progress_bar.visibility = View.GONE
                        Log.d("onComplete", "完遂")
                    }

                    override fun onError(e: Throwable) {
                        Log.d("通信失敗", "${e.message}")
                    }

                    override fun onNext(response: ApiRequest<News>) {
                        val nextUrl: String = response.next?: ""
                        val newsList = response.results

                        listAdapter.items = newsList
                        news_list.adapter = listAdapter
                        news_list.setOnItemClickListener { _, _, position, _ ->
                            val item: News = listAdapter.items[position]
                            Log.d("Parce前", "$item")
                            NewsDetailActivity.intent(this@NewsListActivity, item).let { startActivity(it) }
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                        // Subscribeした瞬間に呼ばれる
                        progress_bar.visibility = View.VISIBLE
                        Log.d("OnSubscribe", "${d.isDisposed}")
                    }
                })

        // スクロールイベントを取得
        RxAbsListView.scrollEvents(news_list)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .filter { scrollEvent ->
                    scrollEvent.firstVisibleItem() + scrollEvent.visibleItemCount() + 3 >= scrollEvent.totalItemCount()
                } // スクロール位置が下から3つめまでスクロールしたかを監視
                .flatMap { scrollEvent ->
                        Observable.fromArray(mutableListOf(
                            News.createDummy(), News.createDummy(), News.createDummy()
                        ))
                } // データ取得のObservableに処理をつなげる
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ newsList ->
                    listAdapter.items.plusAssign(newsList)
                    listAdapter.notifyDataSetChanged()
                }, { t ->
                    Log.d("エラー", "アイテムエラー")
                })

    }


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
