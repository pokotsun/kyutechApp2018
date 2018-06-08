package com.gorigolilagmail.kyutechapp2018.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.JsonObject

data class User(
        val id: Int,
        val schoolYear: Int,
        val department: Int
): Parcelable {

    override fun describeContents(): Int  = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.run {
            writeInt(id)
            writeInt(schoolYear)
            writeInt(department)
        }
    }

    fun getDepartmentName(): String = when(department) {
        200 -> "情報工学部　情工１類　Ⅰクラス"
        201 -> "情報工学部　情工１類　Ⅱクラス"
        202 -> "情報工学部　情工２類　Ⅲクラス"
        203 -> "情報工学部　情工３類　Ⅳクラス"
        204 -> "情報工学部　情工３類　Ⅴクラス"
        205 -> "知能情報工学科"
        206 -> "知能情報工学科　知能情報工学科（編入）"
        207 -> "電子情報工学科"
        208 -> "電子情報工学科　電子情報工学科（編入）"
        209 -> "システム創成情報工学科　システム創成情報工学科"
        210 -> "システム創成情報工学科　システム創成情報工学科（編入）"
        211 -> "機械情報工学科　機械情報工学科"
        212 -> "機械情報工学科　機械情報工学科（編入）"
        213 -> "生命情報工学科　生命情報工学科"
        else -> "生命情報工学科　生命情報工学科（編入）"

    }



    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object: Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = source.run {
                User(readInt(), readInt(), readInt())
            }

            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }

        fun createJson(schoolYear: Int, departmentId: Int): JsonObject =
                JsonObject().apply {
                    addProperty("school_year", schoolYear)
                    addProperty("department", departmentId)
                }


    }
}