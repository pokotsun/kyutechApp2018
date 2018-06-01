package com.gorigolilagmail.kyutechapp2018.client

import android.content.Context
import android.util.Log
import com.gorigolilagmail.kyutechapp2018.model.RealmUser
import com.gorigolilagmail.kyutechapp2018.model.User
import io.realm.Realm
import io.realm.RealmConfiguration

object LoginClient {
    private var realm: Realm? = null

    fun init(context: Context) {
        Realm.init(context)
        val config = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(config)
        realm = Realm.getDefaultInstance()
    }

    // 登録ユーザー情報をRealmにセットする
    fun signUp(user: User) {
        realm?.executeTransaction {
            val loginUser = realm?.createObject(RealmUser::class.java)?.apply {
                id = user.id
                schoolYear = user.schoolYear
                department = user.department
            }
            realm?.copyToRealm(loginUser)
        }
    }

    // データを全削除
    fun signOut() = realm?.executeTransaction { realm ->
        realm.deleteAll()
    }

    // SignUpしているかどうかの判断
    fun isSignedUp(): Boolean = realm?.where(RealmUser::class.java)?.findFirst() != null

    // 今現在SignUpしているユーザーを取ってくる
    fun getCurrentUserInfo(): User? = realm?.where(RealmUser::class.java)?.findFirst()?.let { realmUser ->
        User(realmUser.id, realmUser.schoolYear, realmUser.department)
    }?: null

    fun printAllUser() {
        val loginUsers = realm?.where(RealmUser::class.java)?.findAll()
        for((i,k) in loginUsers!!.withIndex()) {
            Log.d("login_users[$i]", k.toString())
        }
    }

}

