package com.gorigolilagmail.kyutechapp2018.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.client.LoginClient
import com.gorigolilagmail.kyutechapp2018.client.RetrofitServiceGenerator.createService
import com.gorigolilagmail.kyutechapp2018.model.Syllabus
import com.gorigolilagmail.kyutechapp2018.model.UserSchedule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user_schedule_detail.*

class UserScheduleDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_schedule_detail)

        // アクティビティ起動時にキーボードを表示しないようにする
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
//        setSupportActionBar(toolbar)

        val userSchedule: UserSchedule = intent.getParcelableExtra(UserScheduleDetailActivity.USER_SCHEDULE_EXTRA)

        val userId = LoginClient.getCurrentUserInfo()?.id?: throw NullPointerException()
        fab.setOnClickListener { view ->
            createService().updateUserSchedule(
                    userSchedule.id,
                    UserSchedule.createJson(
                            userId = userId, syllabusId = userSchedule.syllabus.id,
                            day = userSchedule.day, period = userSchedule.period,
                            quarter=userSchedule.quarter,
                            memo=memo_edit_field.text.toString(),
                            lateNum=userSchedule.lateNum, absentNum=userSchedule.absentNum
                    )
            )
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe{ update_progress.visibility = View.VISIBLE }
                    .doOnComplete { update_progress.visibility = View.GONE }
                    .subscribe { userSchedule ->
                        Log.d("updateComplete", "UserComplete $userSchedule")
                        Toast.makeText(this, "更新が完了しました!!", Toast.LENGTH_SHORT).show()
                    }

            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        setSyllabusInfos2View(userSchedule.syllabus)
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

    private inline fun setSyllabusInfo2View(title: String, content: String) {
        val contentView = layoutInflater.inflate(R.layout.syllabus_content, null)
        contentView.findViewById<TextView>(R.id.title).text = title
        if(title.isNullOrEmpty()) {
            contentView.findViewById<TextView>(R.id.content).text = "\n"
        }
        else {
            contentView.findViewById<TextView>(R.id.content).text = content
        }
        contentView.findViewById<TextView>(R.id.content).text = content
        content_wrapper.addView(contentView)
    }

    companion object {
        private const val USER_SCHEDULE_EXTRA: String = "userSchedule"
        fun intent(context: Context, userSchedule: UserSchedule): Intent =
                Intent(context, UserScheduleDetailActivity::class.java)
                        .putExtra(USER_SCHEDULE_EXTRA, userSchedule)
    }
}
