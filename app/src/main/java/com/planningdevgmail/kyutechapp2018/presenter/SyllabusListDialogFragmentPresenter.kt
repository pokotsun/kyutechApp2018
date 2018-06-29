package com.planningdevgmail.kyutechapp2018.presenter

import android.util.Log
import com.planningdevgmail.kyutechapp2018.client.RetrofitServiceGenerator.createService
import com.planningdevgmail.kyutechapp2018.model.Syllabus
import com.planningdevgmail.kyutechapp2018.model.UserSchedule
import com.planningdevgmail.kyutechapp2018.view.adapter.SyllabusListAdapter
import com.planningdevgmail.kyutechapp2018.view.fragment.MvpSyllabusListDialogFramgnetView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


interface ImplSyllabusListDialogFragmentPresenter: Presenter {
    fun onClickDeleteButton(currentUserScheduleId: Int)

    fun onScrollSyllabusList(listAdapter: SyllabusListAdapter)

    fun setSyllabuses2List(listAdapter: SyllabusListAdapter, dayId: Int, period: Int, userDepartment: String)

    fun onClickSyllabusListItem(listAdapter: SyllabusListAdapter, position: Int, userId: Int, dayId: Int, period: Int, quarter: Int)
}

class SyllabusListDialogFragmentPresenter(private val view: MvpSyllabusListDialogFramgnetView)
    : ImplSyllabusListDialogFragmentPresenter {

    private var nextUrl: String = ""

    // セットされているuserScheduleのDELETEボタンが押された時の挙動
    override fun onClickDeleteButton(currentUserScheduleId: Int) {
        createService().deleteUserSchedule(currentUserScheduleId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    if(response.isSuccessful) {
                        view.submitResult(true)
                    }
                    else {
                        view.showToast("削除に失敗しました")
                        view.dismissView()
                    }
                }
    }

    // シラバスのリストをListViewにセットする
    override fun setSyllabuses2List(listAdapter: SyllabusListAdapter, dayId: Int, period: Int, userDepartment: String) {
        createService().listSyllabusByDayAndPeriod(Syllabus.convertDayId2Str(dayId), period)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    view.dismissSyllabusListContainer()
                    view.showProgress()
                }
                .doOnComplete {
                    view.showSyllabusListContainer()
                    view.dismissProgress()
                }
                .doOnError { Log.d("error", "${it.message}") }
                .subscribe { apiRequest ->
                    listAdapter.items = apiRequest.results
                    listAdapter.userDepartment = userDepartment
                    view.setAdapter2list(listAdapter)
                    nextUrl = apiRequest.next?: ""
                }
    }

    // リスト表示されているシラバスが選択された時の挙動
    override fun onClickSyllabusListItem(listAdapter: SyllabusListAdapter, position: Int, userId: Int, dayId: Int, period: Int, quarter: Int) {
        val item = listAdapter.items[position]
        createService().createUserSchedule(
                UserSchedule.createJson(
                        userId, item.id, dayId, period,
                        quarter, "", 0, 0
                )
        )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { Log.d("UserSchedulePostError", "${it.message}") }
                .subscribe { userSchedule ->
                    Log.d("PostCreated", "UserScheduleのPOST完了: ${userSchedule}")
                    view.submitResult(true)
                    view.dismissView()
                }
    }

    // シラバスがリスト表示されているときにスクロールした場合の挙動
    override fun onScrollSyllabusList(listAdapter: SyllabusListAdapter) {
        view.getObservableAbsListViewScrollEvent()
        .filter { scrollEvent ->
            Log.d("SyllabusScrollEvents", "${scrollEvent.firstVisibleItem()}, ${scrollEvent.visibleItemCount()} ${scrollEvent.totalItemCount()}")
            scrollEvent.firstVisibleItem() + scrollEvent.visibleItemCount() >= scrollEvent.totalItemCount()
        }
                .filter{ nextUrl.isNotEmpty() }
                .take(1)
                .flatMap {
                    createService().getNextSyllabusList(nextUrl)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSubscribe { view.showProgress() }
                            .doOnComplete { view.dismissProgress() }
                }
                .subscribe { apiRequest ->
                    nextUrl = apiRequest.next?: ""
                    listAdapter.items.plusAssign(apiRequest.results)
                    listAdapter.notifyDataSetChanged()
                    view.showToast("追加で授業情報${apiRequest.results.size}件を取得しました")
                }
    }
}
