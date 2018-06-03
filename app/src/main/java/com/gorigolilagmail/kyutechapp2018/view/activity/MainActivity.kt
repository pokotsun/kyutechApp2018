package com.gorigolilagmail.kyutechapp2018.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.Log
import android.widget.Toast
import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.client.LoginClient
import com.gorigolilagmail.kyutechapp2018.model.ITabItems
import com.gorigolilagmail.kyutechapp2018.model.TabItems
import com.gorigolilagmail.kyutechapp2018.presenter.MainActivityPresenter
import com.gorigolilagmail.kyutechapp2018.view.MvpView
import com.gorigolilagmail.kyutechapp2018.view.adapter.TabAdapter
import com.jakewharton.rxbinding2.support.design.widget.RxTabLayout
import com.jakewharton.rxbinding2.support.design.widget.TabLayoutSelectionEvent
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

interface MainMvpView: MvpView {
}

class MainActivity : AppCompatActivity(),  ViewPager.OnPageChangeListener, MainMvpView {

    private val presenter: MainActivityPresenter = MainActivityPresenter()

    private val tabItems: ITabItems = TabItems()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = TabAdapter(supportFragmentManager, tabItems.fragments)

        view_pager.adapter = adapter
        view_pager.addOnPageChangeListener(this)
        tab_layout.setupWithViewPager(view_pager)

        initializeTabIcons() // タブアイコンの初期化処理

        // Tab情報取得
        RxTabLayout.selectionEvents(tab_layout)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<TabLayoutSelectionEvent> {
                    override fun onComplete() {
                        Log.d("onComplete", "TabEvent完遂")
                    }

                    override fun onError(e: Throwable) {
                        Log.d("タブに関してエラー発生", "${e.message}")
                    }

                    override fun onNext(tabEvent: TabLayoutSelectionEvent) {
                        val currentTab = tabEvent.tab()
                        toolbar_title.text = tabItems.titles[currentTab.position]
                        tabItems.selectedTab?.icon = ContextCompat.getDrawable(this@MainActivity, tabItems.icons[tabItems.selectedTab!!.position])
//                        tabItems.selectedTab?.text = "TAB TITLE"
                        currentTab.icon = ContextCompat.getDrawable(this@MainActivity, tabItems.selectedIcons[currentTab.position])
//                        currentTab.text = "SELECTED"
                        tabItems.selectedTab = currentTab

                        if(currentTab.position == SCHEDULE_POSITION) { // 時間割画面が選択された場合
                            tool_bar.inflateMenu(R.menu.menu_schedule_fragment)
                            tool_bar.setOnMenuItemClickListener { item ->
                                Log.d("Menu Clicked", "${item.title} , ${item.itemId}")
                                val quarter: Int = when(item.itemId) {
                                    R.id.first_quarter -> 0
                                    R.id.second_quarter -> 1
                                    R.id.third_quarter -> 2
                                    R.id.fourth_quarter -> 3
                                    else -> { // 編集完了ボタンが押された時
                                        toolBarEditBtnToggle() // スケジュール画面の状態を変更
                                        tabItems.getScheduleFragment().currentQuarter.id
                                    }
                                }
                                val loginUserId: Int = LoginClient.getCurrentUserInfo()?.id?: throw NullPointerException()
                                tabItems.getScheduleFragment().setScheduleItems(loginUserId, quarter)
                                true
                            }
                        } else { // 時間割画面でなかったら

                            tool_bar.menu.clear()
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.d("OnSubscribe", "TabEvent: ${d.isDisposed}")
                    }

                })

        // toolbarの設定
        tool_bar.title = ""
        setSupportActionBar(tool_bar)
    }

    override fun onPageScrollStateChanged(state: Int) {
        // スクロールし終わった時に呼ばれる
        Log.d("MainActivity", "onPageStateChanged() position = $state")
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        // ページが横にスクロールした時に動く
        Log.d("MainActivity", "onPageScrolled() position = $position")
    }

    override fun onPageSelected(position: Int) {
        // ページがタブで選択された時に呼ばれる
        Log.d("MainActivity", "onPageSelected() position = $position")
    }

    private fun toolBarEditBtnToggle() {
        if(tool_bar.menu.findItem(R.id.schedule_edit).title == "完了") {
            tool_bar.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.kyutech_main_color))
            tool_bar.menu.findItem(R.id.schedule_edit).title = "編集"
        } else {
            tool_bar.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.newsTopic5))
            tool_bar.menu.findItem(R.id.schedule_edit).title = "完了"
        }
    }

    override fun showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    // タブのアイコンを初期化
    private fun initializeTabIcons() {
        tabItems.selectedTab = tab_layout.getTabAt(tab_layout.selectedTabPosition)
        for(i in 0 until tab_layout.tabCount) {
            val tab: TabLayout.Tab = tab_layout.getTabAt(i)?: throw NullPointerException("can't get Tab")
            tab.icon = ContextCompat.getDrawable(this, tabItems.icons[tab.position])
        }
    }

    companion object {
        private val SCHEDULE_POSITION = 1
    }
}

