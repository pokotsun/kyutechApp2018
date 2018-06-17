package com.gorigolilagmail.kyutechapp2018.view.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.client.LoginClient
import com.gorigolilagmail.kyutechapp2018.client.RetrofitServiceGenerator.createService
import com.gorigolilagmail.kyutechapp2018.model.Syllabus
import com.gorigolilagmail.kyutechapp2018.model.UserSchedule
import com.gorigolilagmail.kyutechapp2018.view.adapter.SyllabusListAdapter
import com.jakewharton.rxbinding2.widget.RxAbsListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_syllabus_list.*

interface MvpSyllabusListDialogFramgnetView {
    fun showProgress()
    fun dismissProgress()
}


class SyllabusListDialogFragment : DialogFragment(), MvpSyllabusListDialogFramgnetView {

    val userId: Int = LoginClient.getCurrentUserInfo()?.id?: throw NullPointerException()
    private var nextUrl: String = ""

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
        val day = arguments.getInt(DAY_EXTRA)
        val quarter = arguments.getInt(QUARTER_EXTRA)
        val currentUserScheduleId = arguments.getInt(CURRENT_SCHEDULE_ID)
        val userDepartment = arguments.getString(USER_DEPARTMENT_NAME)

        Log.d("currentUserScheduleId", "$currentUserScheduleId")

        if(currentUserScheduleId > 0) {
            remove_btn.setOnClickListener { // 削除ボタンが押された時の挙動
                createService().deleteUserSchedule(currentUserScheduleId)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { response ->
                            if(response.isSuccessful) {
                                submitResult(true)
                            }
                            else { Toast.makeText(context, "削除に失敗しました", Toast.LENGTH_SHORT).show() }
                            dismiss()
                        }
            }
        } else {
            remove_btn.visibility = View.GONE
        }

        val listAdapter = SyllabusListAdapter(context)

        createService().listSyllabusByDayAndPeriod(Syllabus.convertDayId2Str(day), period)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    syllabus_list_container.visibility = View.GONE
                    showProgress()
                }
                .doOnComplete {
                    syllabus_list_container.visibility = View.VISIBLE
                    dismissProgress()
                }
                .doOnError { Log.d("error", "${it.message}") }
                .subscribe { apiRequest ->
                    listAdapter.items = apiRequest.results
                    listAdapter.userDepartment = userDepartment
                    syllabus_list.adapter = listAdapter

                    // リスト表示されているシラバスが選択された時の挙動
                    syllabus_list.setOnItemClickListener { parent, view, position, id ->
                        val item = listAdapter.items[position]
                        createService()
                                .createUserSchedule(
                                        UserSchedule.createJson(
                                                userId, item.id, day, period,
                                                quarter, "", 0, 0
                                        )
                                )
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnError { Log.d("UserSchedulePostError", "${it.message}") }
                                .subscribe { userSchedule ->
                                    Log.d("PostCreated", "UserScheduleのPOST完了: ${userSchedule}")
                                    submitResult(true)
                                    dismiss()
                                }
                    }
                }

        scrollEvent(listAdapter)
    }

    private fun scrollEvent(listAdapter: SyllabusListAdapter) {
        // スクロールイベントを取得
        RxAbsListView.scrollEvents(syllabus_list)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .filter { scrollEvent ->
                    //                    Log.d("scrollEvents", "${scrollEvent.firstVisibleItem()}, ${scrollEvent.visibleItemCount()} ${scrollEvent.totalItemCount()}")
                    scrollEvent.firstVisibleItem() + scrollEvent.visibleItemCount() >= scrollEvent.totalItemCount()
                }
                .filter{ nextUrl.isNotEmpty() }
                .take(1)
                .flatMap {
                    createService().getNextSyllabusList(nextUrl)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSubscribe { progress_bar.visibility = View.VISIBLE }
                            .doOnComplete { progress_bar.visibility = View.GONE }
                }
                .subscribe { apiRequest ->
                    nextUrl = apiRequest.next?: ""
                    listAdapter.items = apiRequest.results
                    listAdapter.notifyDataSetChanged()

                    if(apiRequest.next.isNullOrEmpty())
                        Toast.makeText(context, "一番古いお知らせ${apiRequest.results.size}件です", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(context, "次のお知らせ${apiRequest.results.size}件を取得しました", Toast.LENGTH_SHORT).show()
                }
    }

    override fun onDetach() {
        super.onDetach()
    }

    private fun submitResult(isPosted: Boolean) {
        if(targetFragment != null) {
            Intent().run {
                putExtra("isSubmitted", isPosted)
                targetFragment.onActivityResult(targetRequestCode, Activity.RESULT_OK, this)
            }
        }
    }

    override fun showProgress() {
//        syllabus_list_container.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE
    }

    override fun dismissProgress() {
//        syllabus_list_container.visibility = View.VISIBLE
        progress_bar.visibility = View.GONE
    }


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
