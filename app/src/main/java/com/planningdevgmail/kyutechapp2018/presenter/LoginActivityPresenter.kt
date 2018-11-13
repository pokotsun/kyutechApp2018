package com.planningdevgmail.kyutechapp2018.presenter

import android.util.Log
import com.planningdevgmail.kyutechapp2018.client.LoginClient
import com.planningdevgmail.kyutechapp2018.client.RetrofitServiceGenerator.createService
import com.planningdevgmail.kyutechapp2018.model.User
import com.planningdevgmail.kyutechapp2018.view.activity.LoginMvpView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class LoginActivityPresenter(private val view: LoginMvpView): Presenter {

    // ユーザーの登録を行う
    fun createUser(schoolYear: Int, department: Int) {
        createService().createUser(User.createJson(schoolYear, department))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {  t -> // POSTでエラーが生じた場合
                    Log.d("denied", "UserPostDenied, ${t.message}\n body: ${t.stackTrace}")
                }
                .subscribe { user -> // リクエストが成功した場合
                            Log.d("accepted", "user:$user")
                            // TODO ここのuserを渡すのをどうにかしたい
                            LoginClient.signUp(user)
                            Log.d("loginuserInfo", "${view.isSignedUp()}, ${LoginClient.getCurrentUserInfo()}")
                            if(view.isSignedUp()) {  // ユーザー登録が無事済めば
                                view.goToMainActivity("ユーザー登録が完了しました！", true)
                            } else { // ユーザー登録ができていなければ
                                view.showToast("ユーザー登録が正常に行えませんでした")
                            }
                        }


    }
}