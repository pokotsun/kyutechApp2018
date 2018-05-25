package com.gorigolilagmail.kyutechapp2018.view.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
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


class NewsListActivity : AppCompatActivity() {

    private var nextUrl: String = ""

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
                        nextUrl = response.next?: ""
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

        scrollEvent(listAdapter)

    }

    private fun scrollEvent( listAdapter: NewsListAdapter) {
        // スクロールイベントを取得
        RxAbsListView.scrollEvents(news_list)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .filter { scrollEvent ->
                    Log.d("scrollEvents", "${scrollEvent.firstVisibleItem()}, ${scrollEvent.visibleItemCount()} ${scrollEvent.totalItemCount()}")
                    scrollEvent.firstVisibleItem() + scrollEvent.visibleItemCount()  >= scrollEvent.totalItemCount()
                }
                .filter {  nextUrl.isNotEmpty() } // next_urlが空ではないか？
                .take(1) // いっぱいイベント拾ってきちゃうけどとりあえずここで渋滞するので上からひとつだけ発火させる
                .flatMap {  // データ取得のObservableに処理をつなげる
                    createService().getNextNewsList(nextUrl)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                }
                .subscribe( object: Observer<ApiRequest<News>> {

                    override fun onComplete() {
                        Log.d("onComplete", "ページスクロールCompleted")
                        scrollEvent(listAdapter)
                    }
                    override fun onSubscribe(d: Disposable) {
                        Log.d("onSubscribe", "ページスクロールSubscribe中")
                    }

                    override fun onNext(apiRequest: ApiRequest<News>) {
                        nextUrl = apiRequest.next?: ""
                        listAdapter.items.plusAssign(apiRequest.results)
                        listAdapter.notifyDataSetChanged()

                        if(apiRequest.next.isNullOrEmpty())
                            Toast.makeText(this@NewsListActivity, "一番古いお知らせ100件です   ", Toast.LENGTH_SHORT).show()
                        else
                            Toast.makeText(this@NewsListActivity, "次のお知らせ100件を取得しました", Toast.LENGTH_SHORT).show()
                    }//                .take(1)


                    override fun onError(e: Throwable) {
                        Log.d("onErrored", "${e.message}")
                    }

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
