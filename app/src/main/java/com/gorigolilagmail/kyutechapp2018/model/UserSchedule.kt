package com.gorigolilagmail.kyutechapp2018.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.JsonObject
import com.gorigolilagmail.kyutechapp2018.R

data class UserSchedule(
        val id: Int,
        val syllabus: Syllabus,
        val day: Int,
        val period: Int,
        val quarter: Int,
        val memo: String,
        val lateNum: Int,
        val absentNum: Int
): Parcelable {

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = dest.run {
        writeInt(id)
        writeParcelable(syllabus, flags)
        writeInt(day)
        writeInt(period)
        writeInt(quarter)
        writeString(memo)
        writeInt(lateNum)
        writeInt(absentNum)
    }


    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<UserSchedule> = object: Parcelable.Creator<UserSchedule> {
            override fun createFromParcel(source: Parcel): UserSchedule = source.run {
                UserSchedule(readInt(), readParcelable(Syllabus::class.java.classLoader),
                        readInt(),readInt(), readInt(),
                        readString(), readInt(), readInt()
                )
            }

            override fun newArray(size: Int): Array<UserSchedule?> = arrayOfNulls(size)
        }

        fun createDummy(day: Int, period: Int, quarter: Int): UserSchedule = UserSchedule(id=0,
                syllabus = Syllabus.createDummy(),
                day = day, period = period, quarter = quarter,
                memo = "メモ", lateNum = 13, absentNum = 12
        )

        fun createJson(userId: Int, syllabusId: Int, day: Int,
                       period: Int, quarter: Int, memo: String,
                       lateNum: Int, absentNum: Int): JsonObject =
                JsonObject().apply {
                    addProperty("user_id", userId)
                    addProperty("syllabus_id", syllabusId)
                    addProperty("day", day)
                    addProperty("period", period)
                    addProperty("quarter", quarter)
                    addProperty("memo", memo)
                    addProperty("late_num", lateNum)
                    addProperty("absent_num", absentNum)
                }

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