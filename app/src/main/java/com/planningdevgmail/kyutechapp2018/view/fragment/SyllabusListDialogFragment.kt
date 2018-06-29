package com.planningdevgmail.kyutechapp2018.view.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.planningdevgmail.kyutechapp2018.R
import com.planningdevgmail.kyutechapp2018.client.LoginClient
import com.planningdevgmail.kyutechapp2018.presenter.ImplSyllabusListDialogFragmentPresenter
import com.planningdevgmail.kyutechapp2018.presenter.SyllabusListDialogFragmentPresenter
import com.planningdevgmail.kyutechapp2018.view.adapter.SyllabusListAdapter
import com.jakewharton.rxbinding2.widget.AbsListViewScrollEvent
import com.jakewharton.rxbinding2.widget.RxAbsListView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_syllabus_list.*

interface MvpSyllabusListDialogFramgnetView {
    fun showProgress()
    fun dismissProgress()
    fun showSyllabusListContainer()
    fun dismissSyllabusListContainer()
    fun dismissView()
    fun showToast(msg: String)
    fun setAdapter2list(adapter: SyllabusListAdapter)
    fun submitResult(isPosted: Boolean)
    fun getObservableAbsListViewScrollEvent(): Observable<AbsListViewScrollEvent>
}

class SyllabusListDialogFragment : DialogFragment(), MvpSyllabusListDialogFramgnetView {

    private val presenter: ImplSyllabusListDialogFragmentPresenter = SyllabusListDialogFragmentPresenter(this)
    private val userId: Int = LoginClient.getCurrentUserInfo()?.id?: throw NullPointerException()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_syllabus_list, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val period = arguments.getInt(PERIOD_EXTRA)
        val dayId = arguments.getInt(DAY_EXTRA)
        val quarter = arguments.getInt(QUARTER_EXTRA)
        val currentUserScheduleId = arguments.getInt(CURRENT_SCHEDULE_ID)
        val userDepartment = arguments.getString(USER_DEPARTMENT_NAME)

        Log.d("currentUserScheduleId", "$currentUserScheduleId")

        if(currentUserScheduleId > 0) {
            remove_btn.setOnClickListener { // 削除ボタンが押された時の挙動
                presenter.onClickDeleteButton(currentUserScheduleId)
            }
        } else {
            remove_btn.visibility = View.GONE
        }

        val listAdapter = SyllabusListAdapter(context)

        // シラバスを取得
        presenter.setSyllabuses2List(listAdapter, dayId, period, userDepartment)

        // リスト表示されているシラバスが選択された時の挙動
        syllabus_list.setOnItemClickListener { _, _, position, _ ->
            presenter.onClickSyllabusListItem(listAdapter, position, userId, dayId, period, quarter)
        }

        presenter.onScrollSyllabusList(listAdapter)
    }

    // ダイアログ上でのリクエスト結果をBoolで親Viewに返す
    override fun submitResult(isPosted: Boolean) {
        if(targetFragment != null) {
            Intent().run {
                putExtra("isSubmitted", isPosted)
                targetFragment.onActivityResult(targetRequestCode, Activity.RESULT_OK, this)
            }
        }
    }

    override fun showToast(msg: String) = Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

    override fun showProgress() {
//        syllabus_list_container.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE
    }

    override fun dismissProgress() {
//        syllabus_list_container.visibility = View.VISIBLE
        progress_bar.visibility = View.GONE
    }

    override fun showSyllabusListContainer() { syllabus_list_container.visibility = View.VISIBLE }

    override fun dismissSyllabusListContainer() { syllabus_list_container.visibility = View.GONE }

    override fun setAdapter2list(adapter: SyllabusListAdapter) {
        syllabus_list.adapter = adapter
    }

    // Dialogを閉じる
    override fun dismissView() = dismiss()

    override fun getObservableAbsListViewScrollEvent(): Observable<AbsListViewScrollEvent> =
            RxAbsListView.scrollEvents(syllabus_list)
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())


    companion object {
        private val PERIOD_EXTRA: String = "period"
        private val DAY_EXTRA: String = "day"
        private val QUARTER_EXTRA: String = "quarter"
        private val CURRENT_SCHEDULE_ID: String = "current_user_schedule_id"
        private val USER_DEPARTMENT_NAME: String = "user_department_name"


        @JvmStatic
        fun newInstance(period: Int, day: Int, quarter: Int, currentUserScheduleId: Int, userDepartment: String) =
                SyllabusListDialogFragment().apply {
                    arguments = Bundle().apply {
                        putInt(PERIOD_EXTRA, period)
                        putInt(DAY_EXTRA, day)
                        putInt(QUARTER_EXTRA, quarter)
                        putInt(CURRENT_SCHEDULE_ID, currentUserScheduleId)
                        putString(USER_DEPARTMENT_NAME, userDepartment)
                    }
                }
    }
}
