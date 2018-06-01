package com.gorigolilagmail.kyutechapp2018.model

import com.google.gson.JsonObject
import io.realm.RealmObject


data class User(val id: Int, val schoolYear: Int, val department: Int) {

    companion object {
        fun createUserJson(schoolYear: Int, departmentId: Int): JsonObject =
                JsonObject().apply {
                    addProperty("school_year", schoolYear)
                    addProperty("department", departmentId)
                }
    }
}