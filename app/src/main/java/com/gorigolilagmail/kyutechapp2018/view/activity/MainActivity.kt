package com.gorigolilagmail.kyutechapp2018.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
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
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*

interface MainMvpView: MvpView {
}

class MainActivity : AppCompatActivity(), MainMvpView {

    private val presenter: MainActivityPresenter = MainActivityPresenter()

    private val tabItems: ITabItems = TabItems()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = TabAdapter(supportFragmentManager, tabItems.fragments)

        view_pager.adapter = adapter
        tab_layout.setupWithViewPager(view_pager)

        initializeTabIcons() // タブアイコンの初期化処理

        // Tab情報取得
        RxTabLayout.selectionEvents(tab_layout)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { tabEvent ->
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
                            val loginUserId: Int = LoginClient.getCurrentUserInfo()?.id?: throw NullPointerException()
                            when(item.itemId) {
                                R.id.schedule_edit -> { // 編集ボタンが押された時
                                    val quarter = tabItems.getScheduleFragment().currentQuarter.id
                                    val isEditing: Boolean = toolBarEditBtnToggle(loginUserId, quarter) // スケジュール画面の状態を変更
                                    tabItems.getScheduleFragment().setScheduleItems(loginUserId, quarter, isEditing=isEditing)
                                }
                                else -> { // クオーターの変更の場合
                                    val quarter: Int = when (item.itemId) {
                                        R.id.first_quarter -> 0
                                        R.id.second_quarter -> 1
                                        R.id.third_quarter -> 2
                                        else -> 3
                                    }
                                    toolbar_title.text = "時間割(第${quarter+1}クォーター)"
                                    tabItems.getScheduleFragment().setScheduleItems(loginUserId, quarter)
                                }
                            }
                            true
                        }
                    } else { // 時間割画面でなかったら
                        tool_bar.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.kyutech_main_color))
                        tool_bar.menu.clear()
                    }
                }



        // toolbarの設定
        tool_bar.title = ""
        setSupportActionBar(tool_bar)
    }

    // 編集ボタンが今どの状態にあるかで表示する内容を変更し、編集中か閲覧中かの状態をBoolで返す
    private fun toolBarEditBtnToggle(loginUserId: Int, quarter: Int): Boolean {
        val item = tool_bar.menu.findItem(R.id.schedule_edit)
        if(item.title == resources.getString(R.string.schedule_save)) {
            tool_bar.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.kyutech_main_color))
            tabItems.getScheduleFragment().setScheduleItems(loginUserId, quarter, isEditing=false)
            item.icon = ContextCompat.getDrawable(this, android.R.drawable.ic_menu_edit)
            item.title = resources.getString(R.string.schedule_edit)
            return false
        } else {
            tool_bar.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.newsTopic5))
            item.icon = ContextCompat.getDrawable(this, android.R.drawable.ic_menu_save)
            item.title = resources.getString(R.string.schedule_save)
            return true
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

