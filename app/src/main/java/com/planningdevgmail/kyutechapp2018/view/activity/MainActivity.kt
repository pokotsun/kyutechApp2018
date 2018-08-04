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
//                        tabItems.selectedTab?.text = "TAB TITLE"
                    currentTab.icon = ContextCompat.getDrawable(this@MainActivity, tabItems.selectedIcons[currentTab.position])
//                        currentTab.text = "SELECTED"
                    tabItems.selectedTab = currentTab

                    if(currentTab.position == SCHEDULE_POSITION) { // 時間割画面が選択された場合
                        val loginUserId: Int = LoginClient.getCurrentUserInfo()?.id
                                ?: throw NullPointerException()
                        tool_bar.menu.clear()
                        tool_bar.inflateMenu(R.menu.menu_schedule_fragment)

                        setToolBarTitle("時間割(第${tabItems.getScheduleFragment().currentQuarter.id + 1}クォーター)")

                        if(tabItems.getScheduleFragment().isEditing) { // 編集状態で別のタブに移動していたら編集状態に戻す
                            scheduleToEditMode(loginUserId, tabItems.getScheduleFragment().currentQuarter.id, tool_bar.menu.findItem(R.id.schedule_edit))
                        }

                        tool_bar.setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                R.id.schedule_edit -> { // 編集ボタンが押された時
                                    toolBarEditBtnToggle(loginUserId, tabItems.getScheduleFragment().currentQuarter.id)
                                }
                                else -> { // クオーターの変更の場合
                                    val quarter: Int = when (item.itemId) {
                                        R.id.first_quarter -> 0
                                        R.id.second_quarter -> 1
                                        R.id.third_quarter -> 2
                                        else -> 3
                                    }
                                    setToolBarTitle("時間割(第${quarter + 1}クォーター)")
                                    tabItems.getScheduleFragment().setScheduleItems(loginUserId, quarter, isEditing=tabItems.getScheduleFragment().isEditing)
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
        val item: MenuItem = tool_bar.menu.findItem(R.id.schedule_edit)
        val isEditing: Boolean = tabItems.getScheduleFragment().isEditing
        if(isEditing) {
            scheduleToBrowseMode(loginUserId, quarter, item)
        } else { // 編集状態にする
            scheduleToEditMode(loginUserId, quarter, item)
        }
        tabItems.getScheduleFragment().isEditing = isEditing.not()
        return tabItems.getScheduleFragment().isEditing
    }

    private fun scheduleToEditMode(loginUserId: Int, quarter: Int, item: MenuItem) {
        changeStateOfSchedule(loginUserId, quarter, item, R.color.newsTopic5, R.mipmap.check_icon, true)
    }

    private inline fun scheduleToBrowseMode(loginUserId: Int, quarter: Int, item: MenuItem) {
        changeStateOfSchedule(loginUserId, quarter, item, R.color.kyutech_main_color, R.mipmap.edit_icon, false)
    }

    private inline fun changeStateOfSchedule(loginUserId: Int, quarter: Int, item: MenuItem, backgroundColorId: Int, iconId: Int, isEditing: Boolean) {
        setToolBarBackground(backgroundColorId)
        item.icon = ContextCompat.getDrawable(this, iconId)
        tabItems.getScheduleFragment().setScheduleItems(loginUserId, quarter, isEditing)
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

    companion object {
        private const val SCHEDULE_POSITION = 1
    }
}

