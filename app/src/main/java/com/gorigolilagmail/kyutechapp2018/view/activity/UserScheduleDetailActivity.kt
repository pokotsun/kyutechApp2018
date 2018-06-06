package com.gorigolilagmail.kyutechapp2018.view.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.model.Syllabus
import kotlinx.android.synthetic.main.activity_user_schedule_detail.*

class UserScheduleDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_schedule_detail)
//        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val syllabus = Syllabus.createDummy()
        setSyllabusInfos2View(syllabus)


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
}
