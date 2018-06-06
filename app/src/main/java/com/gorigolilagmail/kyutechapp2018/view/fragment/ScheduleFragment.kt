package com.gorigolilagmail.kyutechapp2018.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.GridLayout
import android.widget.Toast

import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.client.LoginClient
import com.gorigolilagmail.kyutechapp2018.client.RetrofitServiceGenerator.createService
import com.gorigolilagmail.kyutechapp2018.model.ApiRequest
import com.gorigolilagmail.kyutechapp2018.model.UserSchedule
import com.gorigolilagmail.kyutechapp2018.view.customView.UserScheduleGridItem
import com.trello.rxlifecycle2.RxLifecycle
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_schedule.*
import org.jetbrains.anko.forEachChild

class ScheduleFragment : Fragment() {


    enum class Quarter(val id: Int) {
        FIRST_QUARTER(0),
        SECOND_QUARTER(1),
        THIRD_QUARTER(2),
        FOURTH_QUARTER(3)
    }

    var isEditing: Boolean = false

    var currentQuarter: Quarter = Quarter.FIRST_QUARTER

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onResume() {
        super.onResume()
        // クラスを全25コマ入れていく
        val userId: Int = LoginClient.getCurrentUserInfo()?.id ?: throw NullPointerException()
        setScheduleItems( userId, currentQuarter.id)
    }

    fun setScheduleItems(userId: Int, quarter: Int, isEditing: Boolean = false) {

        currentQuarter = Quarter.values().filter{it.id == quarter}.first()
        Log.d("isEditing", "$isEditing")
        // まず空のスケジュールを入れていく
        for(i in 0 until 5) {
            for( j in 0 until 5) {
                setScheduleItem(UserSchedule.createDummy(i, j, 0), isBlank = true, isEditing=isEditing)
            }
        }

        // スケジュール情報を取得してデータをsetしていく
        Log.d("QuarterItem", "userId:$userId, 現在第${quarter+1} クオーターです")
        createService().listUserScheduleByQuarter(userId, quarter)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<ApiRequest<UserSchedule>> {
                    override fun onComplete() {
                        try {
                            Log.d("onComplete", "UserScheduleComplete")
                            schedule_progress.visibility = View.GONE
                        } catch(e: IllegalStateException) {
                            Log.w("イリーガル", "${e.message}")
                        }
                    }

                    override fun onError(e: Throwable) {
                        Log.d("onError", "userSchedule OnError ${e.message}")
                        schedule_progress.visibility = View.GONE

                    }

                    override fun onNext(apiRequest: ApiRequest<UserSchedule>) {
                        val userSchedules: List<UserSchedule> = apiRequest.results
                        try {
                            userSchedules.forEach { userSchedule ->
                                setScheduleItem(userSchedule, isEditing = isEditing)
                            }
                        } catch(e: Exception) {
                            when(e) {
                                is NullPointerException, is java.lang.NullPointerException -> {
                                    Log.w("ぬるぽ", "${e.message}")
                                }
                                is IllegalStateException -> {
                                    Log.w("イリーガル", "${e.message}")
                                }
                            }
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                        schedule_progress.visibility = View.VISIBLE
                        Log.d("onSubscribe", "UserSchedule onSubscribe")
                    }
                })

    }

    private fun setScheduleItem(userSchedule: UserSchedule, isBlank: Boolean =false, isEditing: Boolean=false) {
        val item = UserScheduleGridItem(context, item = userSchedule)
        item.layoutParams = GridLayout.LayoutParams().apply {
            columnSpec = GridLayout.spec(userSchedule.day, GridLayout.FILL, 1f)
            rowSpec = GridLayout.spec(userSchedule.period, GridLayout.FILL, 1f)
        }

        if(isEditing) {
            item.setOnClickListener {
                Toast.makeText(context, "(${userSchedule.day}, ${userSchedule.period})" +
                        "のアイテムがタップされました", Toast.LENGTH_SHORT).show()
                showSyllabusListDialog()
            }
        } else {
            item.setOnClickListener {

            }
        }
        if(isBlank) {// BlankフラグがTrueだった場合
            item.setBlankSchedule()
        }
        schedule_container.addView(item)
    }


    private fun showSyllabusListDialog() {
        val dialog = SyllabusListFragment()
        dialog.show(fragmentManager, "fragment_dialog")
    }

    companion object {
        fun newInstance(page: Int): ScheduleFragment {
            val args: Bundle = Bundle().apply {
                putInt("page", page)
            }
            return ScheduleFragment().apply {
                arguments = args
            }
        }
    }



}// Required empty public constructor
