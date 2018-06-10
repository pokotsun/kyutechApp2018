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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_syllabus_list.*

class SyllabusListDialogFragment : DialogFragment() {

    val userId: Int = LoginClient.getCurrentUserInfo()?.id?: throw NullPointerException()

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

        if(currentUserScheduleId > 0) {
            remove_btn.setOnClickListener {
                createService().deleteUserSchedule(currentUserScheduleId)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { response ->
                            if(response.isSuccessful) {
                                submitResult(true)
                            }
                            else {
                                Toast.makeText(context, "削除に失敗しました", Toast.LENGTH_SHORT).show()
                            }
                            dismiss()
                        }
            }
        } else {
            remove_btn.visibility = View.GONE
        }


        createService().listSyllabusByDayAndPeriod(day, period)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    syllabus_list_container.visibility = View.GONE
                    progress_bar.visibility = View.VISIBLE
                }
                .doOnComplete {
                    syllabus_list_container.visibility = View.VISIBLE
                    progress_bar.visibility = View.GONE
                }
                .doOnError { Log.d("error", "${it.message}") }
                .subscribe { apiRequest ->
                    val adapter = SyllabusListAdapter(context)
                    adapter.items = apiRequest.results
                    syllabus_list.adapter = adapter

                    syllabus_list.setOnItemClickListener { parent, view, position, id ->
                        val item = adapter.items[position]
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


        // ダミー
//        val mutableSyllabuses = mutableListOf<Syllabus>()
//        for (i in 0 until 30) {
//            mutableSyllabuses.add(Syllabus.createDummy())
//        }
//        val syllabuses: List<Syllabus> = mutableSyllabuses

//        adapter.items = syllabuses
//        syllabus_list.adapter = adapter
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


    companion object {
        private val PERIOD_EXTRA: String = "period"
        private val DAY_EXTRA: String = "day"
        private val QUARTER_EXTRA: String = "quarter"
        private val CURRENT_SCHEDULE_ID: String = "current_user_schedule_id"

        @JvmStatic
        fun newInstance(period: Int, day: Int, quarter: Int, currentUserScheduleId: Int) =
                SyllabusListDialogFragment().apply {
                    arguments = Bundle().apply {
                        putInt(PERIOD_EXTRA, period)
                        putInt(DAY_EXTRA, day)
                        putInt(QUARTER_EXTRA, quarter)
                        putInt(CURRENT_SCHEDULE_ID, currentUserScheduleId)
                    }
                }
    }
}
