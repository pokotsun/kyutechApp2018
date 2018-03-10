package com.gorigolilagmail.kyutechapp2018.model

import android.content.Context
import android.support.v4.content.ContextCompat
import com.gorigolilagmail.kyutechapp2018.R
import java.util.*

/**
 * Created by pokotsun on 18/03/10.
 */

data class NewsHeading(
        val color: Int,
        val headingCharacter: String,
        val headingName: String,
        val updatedDate: String) {

    companion object {
        fun getList(context: Context): List<NewsHeading> =
                listOf(
                        NewsHeading(
                                ContextCompat.getColor(context, R.color.newsTopic1),
                                "呼", "学生呼出",
                                "2018/2/13"),
                        NewsHeading(
                                ContextCompat.getColor(context, R.color.newsTopic2),
                                "休", "休講通知",
                                "2018/2/14"
                        ),
                        NewsHeading(
                                ContextCompat.getColor(context, R.color.newsTopic3),
                                "知", "お知らせ",
                                "2018/2/14"
                        ),
                        NewsHeading(
                                ContextCompat.getColor(context, R.color.newsTopic4),
                                "変", "時間割・講義室変更",
                                "2018/2/13"
                        ),
                        NewsHeading(
                                ContextCompat.getColor(context, R.color.newsTopic5),
                                "補", "補講通知",
                                "2018/2/14"
                        ),
                        NewsHeading(
                                ContextCompat.getColor(context, R.color.newsTopic6),
                                "休", "授業調整・期末試験",
                                "2018/2/14"
                        ),
                        NewsHeading(
                                ContextCompat.getColor(context, R.color.newsTopic7),
                                "奨", "奨学金",
                                "2018/2/14"
                        ),
                        NewsHeading(
                                ContextCompat.getColor(context, R.color.newsTopic8),
                                "集", "集中講義",
                                "2018/2/14"
                        ),
                        NewsHeading(
                                ContextCompat.getColor(context, R.color.newsTopic9),
                                "留", "留学・国際関連",
                                "2018/2/14"
                        ),
                        NewsHeading(
                                ContextCompat.getColor(context, R.color.newsTopic10),
                                "手", "学部生",
                                "2018/2/14"
                        ),
                        NewsHeading(
                                ContextCompat.getColor(context, R.color.newsTopic11),
                                "学", "大学院生",
                                "2018/2/14"
                        ),
                        NewsHeading(
                                ContextCompat.getColor(context, R.color.newsTopic12),
                                "院", "各種手続き",
                                "2018/2/14"
                        )
                )
    }
}