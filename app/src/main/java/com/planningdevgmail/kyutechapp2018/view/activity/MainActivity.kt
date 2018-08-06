package com.planningdevgmail.kyutechapp2018.view.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import com.planningdevgmail.kyutechapp2018.R
import com.planningdevgmail.kyutechapp2018.client.LoginClient
import com.planningdevgmail.kyutechapp2018.client.ITabItems
import com.planningdevgmail.kyutechapp2018.client.TabItems
import com.planningdevgmail.kyutechapp2018.view.adapter.TabAdapter
import com.jakewharton.rxbinding2.support.design.widget.RxTabLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*

interface MainMvpView: MvpView {
    fun setToolBarTitle(title: String)
    fun setToolBarBackground(colorId: Int)
}

class MainActivity : MvpAppCompatActivity(), MainMvpView {

    private val tabItems: ITabItems by lazy {
        LoginClient.init(applicationContext)
        TabItems()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LoginClient.init(applicationContext)

        val adapter = TabAdapter(supportFragmentManager, tabItems.fragments)
        view_pager.adapter = adapter
        tab_layout.setupWithViewPager(view_pager)

        initializeTabIcons() // タブアイコンの初期化処理

        // Tab情報取得
        RxTabLayout.selectionEvents(tab_layout)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { tabEvent ->
                    val currentTab = tabEvent.tab()
                    // まずTabのタイトルテキストを変更する
                    setToolBarTitle(tabItems.titles[currentTab.position])

                    tabItems.selectedTab?.icon = ContextCompat.getDrawable(this@MainActivity, tabItems.icons[tabItems.selectedTab?.position?: 0])
                    currentTab.icon = ContextCompat.getDrawable(this@MainActivity, tabItems.selectedIcons[currentTab.position])
                    tabItems.selectedTab = currentTab

                    if(currentTab.position == SCHEDULE_POSITION) { // 時間割画面が選択された場合
                        tool_bar.menu.clear()
                        tool_bar.inflateMenu(R.menu.menu_schedule_fragment)
                        tool_bar.setOnMenuItemClickListener { item ->
                            onUserScheduleMenuItemClicked(item)
                        }
                    } else { // 時間割画面以外が選択された場合
                        tool_bar.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.kyutech_main_color))
                        tool_bar.menu.clear()
                    }
                }

        // toolbarの設定
        tool_bar.title = ""
        setSupportActionBar(tool_bar)
    }



    // ToolBarのタイトルを変更する
    override fun setToolBarTitle(title: String) { toolbar_title.text = title }

    // Toolbarの背景色を変更する
    override fun setToolBarBackground(colorId: Int) { tool_bar.setBackgroundColor(ContextCompat.getColor(this, colorId)) }


    // タブのアイコンを初期化
    private fun initializeTabIcons() {
        tabItems.selectedTab = tab_layout.getTabAt(tab_layout.selectedTabPosition)
        for(i in 0 until tab_layout.tabCount) {
            val tab: TabLayout.Tab = tab_layout.getTabAt(i)?: throw NullPointerException("can't get Tab")
            tab.icon = ContextCompat.getDrawable(this, tabItems.icons[tab.position])
        }
    }

    // UserScheduleのメニューアイテムがクリックされた時の挙動
    private fun onUserScheduleMenuItemClicked(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.schedule_edit -> { // 編集ボタンが押された時
//                                    toolBarEditBtnToggle(loginUserId, tabItems.getScheduleFragment().currentQuarter.id)
                true
            }
            else -> { // クオーターの変更の場合
                val quarter: Int = when (item.itemId) {
                    R.id.first_quarter -> 0
                    R.id.second_quarter -> 1
                    R.id.third_quarter -> 2
                    else -> 3
                }
                setToolBarTitle("時間割(第${quarter + 1}クォーター)")
                true
            }
        }

    companion object {
        private const val SCHEDULE_POSITION = 1
    }
}

