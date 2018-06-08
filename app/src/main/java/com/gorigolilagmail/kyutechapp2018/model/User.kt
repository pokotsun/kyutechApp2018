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