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
import com.jakewharton.rxbinding2.support.design.widget.TabLayoutSelectionEvent
import com.planningdevgmail.kyutechapp2018.view.fragment.UserScheduleFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*

interface MainMvpView: MvpView {
    fun setToolBarTitle(title: String)
    fun setToolBarBackground(colorId: Int)
    fun getRxTabEvent(): Observable<TabLayoutSelectionEvent>
}

class MainActivity : MvpAppCompatActivity(), MainMvpView {

    private val tabItems: ITabItems by lazy {
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
        getRxTabEvent()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { tabEvent ->
                    val selectedTab = tabItems.selectedTab
                    val currentTab = tabEvent.tab()

                    // まずTabのタイトルテキストを変更する
                    setToolBarTitle(tabItems.titles[currentTab.position])

                    selectedTab?.icon = ContextCompat.getDrawable(this@MainActivity, tabItems.icons[selectedTab?.position?: 0])
                    currentTab.icon = ContextCompat.getDrawable(this@MainActivity, tabItems.selectedIcons[currentTab.position])
                    tabItems.selectedTab = currentTab

                    if(currentTab.position == TabItems.SCHEDULE_POSITION) { // 時間割画面が選択された場合
                        val scheduleFragment: UserScheduleFragment = tabItems.getScheduleFragment()

                        val loginUserId: Int = LoginClient.getCurrentUserInfo()?.id
                                ?: throw NullPointerException()

                        tool_bar.menu.clear()
                        tool_bar.inflateMenu(R.menu.menu_schedule_fragment)

                        setToolBarTitle("時間割(第${scheduleFragment.currentQuarter.id + 1}クォーター)")

                        if(scheduleFragment.isEditing) { // 編集状態で別のタブに移動していたら編集状態に戻す
                            scheduleToEditMode(loginUserId, scheduleFragment.currentQuarter.id, tool_bar.menu.findItem(R.id.schedule_edit))
                        }

                        // ツールバーのメニューアイテムが押された時の挙動
                        tool_bar.setOnMenuItemClickListener { item ->
                            onClickUserScheduleMenuItem(item, loginUserId, scheduleFragment)
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

    override fun onDestroy() {
        super.onDestroy()
        LoginClient.close() // RealmをClose
    }

    // 編集ボタンが今どの状態にあるかで表示する内容を変更し、編集中か閲覧中かの状態をBoolで返す
    private fun toolBarEditBtnToggle(loginUserId: Int, quarter: Int): Boolean {
        val scheduleFragment = tabItems.getScheduleFragment()
        val item: MenuItem = tool_bar.menu.findItem(R.id.schedule_edit)
        val isEditing: Boolean = scheduleFragment.isEditing
        if(isEditing) { // 編集状態のため閲覧モードにする
            scheduleToBrowseMode(loginUserId, quarter, item)
        } else { // 閲覧中のため編集モードにする
            scheduleToEditMode(loginUserId, quarter, item)
        }
        scheduleFragment.isEditing = isEditing.not()
        return scheduleFragment.isEditing
    }

    // UserScheduleフラグメントにおいてツールバーのメニューアイテムが押された時の挙動
    private fun onClickUserScheduleMenuItem(
            item: MenuItem,
            loginUserId: Int,
            scheduleFragment: UserScheduleFragment): Boolean {
        when (item.itemId) {
            R.id.schedule_edit -> { // 編集ボタンが押された時
                toolBarEditBtnToggle(loginUserId, scheduleFragment.currentQuarter.id)
            }
            else -> { // クオーターの変更の場合
                val quarter: Int = when (item.itemId) {
                    R.id.first_quarter -> 0
                    R.id.second_quarter -> 1
                    R.id.third_quarter -> 2
                    else -> 3
                }
                setToolBarTitle("時間割(第${quarter + 1}クォーター)")
                scheduleFragment.setScheduleItems(loginUserId, quarter, isEditing= scheduleFragment.isEditing)
            }
        }
        return true
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

    // タブイベントをObservableで取得
    override fun getRxTabEvent(): Observable<TabLayoutSelectionEvent> = RxTabLayout.selectionEvents(tab_layout)

    // タブのアイコンを初期化
    private fun initializeTabIcons() {
        tabItems.selectedTab = tab_layout.getTabAt(tab_layout.selectedTabPosition)
        for(i in 0 until tab_layout.tabCount) {
            val tab: TabLayout.Tab = tab_layout.getTabAt(i)?: throw NullPointerException("can't get Tab")
            tab.icon = ContextCompat.getDrawable(this, tabItems.icons[tab.position])
        }
    }

}

