package com.planningdevgmail.kyutechapp2018.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.support.design.widget.Snackbar
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.planningdevgmail.kyutechapp2018.R
import com.planningdevgmail.kyutechapp2018.client.LoginClient
import com.planningdevgmail.kyutechapp2018.client.RetrofitServiceGenerator.createService
import com.planningdevgmail.kyutechapp2018.model.Syllabus
import com.planningdevgmail.kyutechapp2018.model.UserSchedule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user_schedule_detail.*

class UserScheduleDetailActivity : MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_schedule_detail)

        // アクティビティ起動時にキーボードを表示しないようにする
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
//        setSupportActionBar(toolbar)

        val userSchedule: UserSchedule = intent.getParcelableExtra(UserScheduleDetailActivity.USER_SCHEDULE_EXTRA)

        Log.d("userScheduleDetail", "${userSchedule.lateNum}, ${userSchedule.absentNum}, ${userSchedule.memo}")

        // 各データをUIにセットしていく
        late_count.text = userSchedule.lateNum.toString()
        absent_count.text = userSchedule.absentNum.toString()
        memo_edit_field.setText(userSchedule.memo, TextView.BufferType.EDITABLE)

        val userId = LoginClient.getCurrentUserInfo()?.id?: throw NullPointerException()

        // Saveボタンが押された時の挙動
        fab.setOnClickListener { view ->
            createService().updateUserSchedule(
                    userSchedule.id,
                    UserSchedule.createJson(
                            userId = userId, syllabusId = userSchedule.syllabus.id,
                            day = userSchedule.day, period = userSchedule.period,
                            quarter=userSchedule.quarter,
                            memo=memo_edit_field.text.toString(),
                            lateNum=late_count.text.toString().toInt(), absentNum=absent_count.text.toString().toInt()
                    )
            )
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe{ update_progress.visibility = View.VISIBLE }
                    .doOnComplete { update_progress.visibility = View.GONE }
                    .doOnError { Log.d("onNewsHeadListFm", "${it.message}") }
                    .subscribe { userSchedule ->
                        Log.d("updateComplete", "UserComplete $userSchedule")

                        showShortSnackBar("遅刻・欠席・メモの更新が完了しました!!", view)
                    }
        }
        setSyllabusInfos2View(userSchedule.syllabus)

        content_wrapper.setOnClickListener {
            if(memo_edit_field.isFocused) {
                memo_edit_field.clearFocus()
                content_wrapper.requestFocus()
                hideSoftKeyboard()
            }
        }

        memo_edit_field.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus) {
                // フォーカスが外れた場合キーボードを非表示にする
                val inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(v.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }

        // 遅刻の➖ボタンが押された時の挙動
        late_minus_btn.setOnClickListener {
            val lateCount = late_count.text.toString().toInt()
            if(lateCount > 0)
                late_count.text = (lateCount - 1).toString()
        }

        // 遅刻のプラスボタンが押された時の挙動
        late_plus_btn.setOnClickListener {
            val lateCount = late_count.text.toString().toInt()
            if(lateCount < 15)
                late_count.text = (lateCount + 1).toString()
        }

        // 欠席の➖ボタンが押された時の挙動
        absent_minus_btn.setOnClickListener {
            val absentCount = absent_count.text.toString().toInt()
            if(absentCount > 0)
                absent_count.text = (absentCount - 1).toString()
        }

        // 欠席のプラスボタンが押された時の挙動
        absent_plus_btn.setOnClickListener {
            val absentCount = absent_count.text.toString().toInt()
            if(absentCount < 15)
                absent_count.text = (absentCount + 1).toString()
        }
    }

    private fun setSyllabusInfos2View(syllabus: Syllabus) {
        syllabus.run {
            listOf(
                    listOf("教科名", title),
                    listOf("科目コード", subjectCode.toString()),
                    listOf("担当教員", teacherName),
                    listOf("学科別情報", convertTargetParticipants2showingText()),
                    listOf("対象学年", targetSchoolYear),
                    listOf("開講学期", targetTerm),
                    listOf("クラス", classNumber.toString()),
                    listOf("曜日・時限", targetPeriod),
                    listOf("講義室", targetPlace),
                    listOf("更新日", publishedDate),
                    listOf("授業の概要", abstract),
                    listOf("カリキュラムにおけるこの授業の位置づけ)", positioning),
                    listOf("授業項目" , lectureContent),
                    listOf("授業の進め方" , lectureProcessing),
                    listOf("授業の達成目標" , performanceTarget),
                    listOf("成績評価の基準および評価方法" , valuationBasis),
                    listOf("授業外学習（予習・復習）の指示" , instructionOutLearning),
                    listOf("キーワード" , keywords),
                    listOf("教科書" , textBooks),
                    listOf("参考書" , studyAidBooks),
                    listOf("備考", notes),
                    listOf("担当教員のメアド", professorEmail)
            )
                    .forEach { list ->
                        setSyllabusInfo2View(list[0], list[1])
                    }
        }
    }

    // キーボードを隠す
    private fun hideSoftKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(this.currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    // シラバスの情報の一つをviewにセットする
    private inline fun setSyllabusInfo2View(title: String, content: String) {
        val contentView = layoutInflater.inflate(R.layout.syllabus_content, null)
        val titleTextView = contentView.findViewById<TextView>(R.id.title)
        val contentTextView = contentView.findViewById<TextView>(R.id.content)
        titleTextView.text = title
        contentTextView.text = if(title.isNullOrEmpty()) "\n" else content
        contentTextView.setTextIsSelectable(true) // 内容部分は選択可能にする
        content_wrapper.addView(contentView)
    }

    companion object {
        private const val USER_SCHEDULE_EXTRA: String = "userSchedule"
        fun intent(context: Context, userSchedule: UserSchedule): Intent =
                Intent(context, UserScheduleDetailActivity::class.java)
                        .putExtra(USER_SCHEDULE_EXTRA, userSchedule)


    }
}
