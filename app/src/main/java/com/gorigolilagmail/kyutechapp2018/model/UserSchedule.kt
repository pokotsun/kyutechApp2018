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
        
    }
}