package com.gorigolilagmail.kyutechapp2018.model

data class UserSchedule(
        val id: Int,
        val syllabus: Syllabus,
        val day: Int,
        val period: Int,
        val quarter: Int,
        val memo: String,
        val lateNum: Int,
        val absentNum: Int
) {
    companion object {
        fun createDummy(day: Int, period: Int, quarter: Int): UserSchedule = UserSchedule(id=2222,
                syllabus = Syllabus.createDummy(),
                day = day, period = period, quarter = quarter,
                memo = "メモ", lateNum = 13, absentNum = 12
        )

        val FIRST_PERIOD = 0
        val SECOND_PERIOD = 1
        val THIRD_PERIOD = 2
        val FOURTH_PERIOD = 3
        val FIFTH_PERIOD = 4

        val FIRST_QUARTER = 0
        val SECOND_QUARTER = 1
        val THIRD_QUARTER = 2
        val FOURTH_QUARTER = 3

        val MON_DAY = 0
        val TUES_DAY = 1
        val WEDNES_DAY = 2
        val THURS_DAY = 3
        val FRI_DAY = 4
    }
}