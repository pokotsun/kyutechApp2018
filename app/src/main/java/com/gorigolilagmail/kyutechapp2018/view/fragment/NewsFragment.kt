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
import org.jetbrains.anko.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by pokotsun on 18/03/10.
 */
class NewsFragment: Fragment() {

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
        val call: Call<ApiRequest<NewsHeading>> = service.listNewsHeadings()
        call.enqueue(object: Callback<ApiRequest<NewsHeading>> {
            override fun onResponse(call: Call<ApiRequest<NewsHeading>>?, response: Response<ApiRequest<NewsHeading>>?) {
                if(response != null) {
                    val newsHeadings: List<NewsHeading> = response.body()?.results?: throw NullPointerException()
//                    Log.d("newsHeadings", newsHeadings.toString()) // 表示
                    adapter.items = newsHeadings
                    ui.newsList?.adapter = adapter

                    // NewsHeadingがクリックされた時の挙動
                    ui.newsList?.setOnItemClickListener { _, _, position, _ ->
                        Intent(context, NewsListActivity::class.java).run {
                            val item = adapter.items[position]
                            putExtra("news_name", item.name)
                            putExtra("news_id", position)

                            startActivity(this)
                        }
                    }

                }
            }

            override fun onFailure(call: Call<ApiRequest<NewsHeading>>?, t: Throwable?) {
                Log.d("Retrofiterror", "${t?.message}")

            }

        })


//        adapter.items = newsHeadings



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