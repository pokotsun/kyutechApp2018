package com.gorigolilagmail.kyutechapp2018.view.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.gorigolilagmail.kyutechapp2018.client.ApiClient
import com.gorigolilagmail.kyutechapp2018.client.RetrofitServiceGenerator.Companion.createService
import com.gorigolilagmail.kyutechapp2018.model.ApiRequest
import com.gorigolilagmail.kyutechapp2018.model.NewsHeading
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
class NewsHeadingFragment: Fragment() {

    private val ui = NewsFragmentUi()

//    private val newsHeadings: List<NewsHeading> by lazy {
//        NewsHeading.getList(activity.applicationContext)
//    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ui.createView(AnkoContext.create(context, this))
    }

    override fun onResume() {
        super.onResume()

        val adapter = NewsHeadingListAdapter(context)

        // サービス生成
        val service: ApiClient = createService()

        service.listNewsHeadings()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<ApiRequest<NewsHeading>> {
                    override fun onComplete() {
                        //すべてStreamを流しきった時に呼ばれる
                        Log.d("onComplete", "完遂")
                    }

                    override fun onError(e: Throwable) {
                        Log.d("通信失敗", "${e.message}")
                    }
                    // 次のdataを呼ぶ
                    override fun onNext(response: ApiRequest<NewsHeading>) {
                        val newsHeadings: List<NewsHeading> = response.results
//                    Log.d("newsHeadings", newsHeadings.toString()) // 表示
                        adapter.items = newsHeadings
                        ui.newsList?.adapter = adapter

                        // NewsHeadingがクリックされた時の挙動
                        ui.newsList?.setOnItemClickListener { _, _, position, _ ->
                            Intent(context, NewsListActivity::class.java).run {
                                val item = adapter.items[position]
                                putExtra("newsHeadingName", item.name)
                                putExtra("newsHeadingCode", item.newsHeadingCode)

                                startActivity(this)
                            }
                        }
                    }
                    override fun onSubscribe(d: Disposable) {
                        // Subscribeした瞬間に呼ばれる
                        Log.d("OnSubscribe", "${d.isDisposed}")
                    }
                })
    }

    private class NewsFragmentUi: AnkoComponent<NewsHeadingFragment> {
        var newsList: ListView? = null
        override fun createView(ui: AnkoContext<NewsHeadingFragment>): View = ui.run {
            verticalLayout {
                newsList = listView {
                }.lparams(width = matchParent, height = wrapContent)
            }
        }
    }

    companion object {
        fun newInstance(page: Int): NewsHeadingFragment {
            val args: Bundle = Bundle().apply {
                putInt("page", page)
            }
            return NewsHeadingFragment().apply {
                arguments = args
            }
        }
    }
}