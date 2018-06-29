package com.planningdevgmail.kyutechapp2018.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmUser : RealmObject() {
    var id: Int = 0
    var schoolYear: Int = 0
    var department: Int = 0
}