package com.gorigolilagmail.kyutechapp2018.view.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast

import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.client.LoginClient
import com.gorigolilagmail.kyutechapp2018.client.RetrofitServiceGenerator.createService
import com.gorigolilagmail.kyutechapp2018.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        LoginClient.init(applicationContext)

        if(LoginClient.isSignedUp()) { // ログイン済みであれば
            goToMainActivity()
        }

        sign_up_btn.setOnClickListener {
            attemptSignUp()
        }

        // 各スピナーの設定
        val schoolYarSpinnerAdapter = ArrayAdapter<String>(this, R.layout.spinner_item, LoginActivity.schoolYears)
        schoolYarSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        school_year_spinner.adapter = schoolYarSpinnerAdapter

        val departmentSpinnerAdapter = ArrayAdapter<String>(this, R.layout.spinner_item, LoginActivity.departments)
        departmentSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        department_spinner.adapter = departmentSpinnerAdapter
    }

    private fun attemptSignUp() {
        val schoolYear: Int = convertToSchoolYear(school_year_spinner.selectedItemPosition)
        val department: Int = convertToDepartmentId(department_spinner.selectedItemPosition)
        Log.d("items", "schoolYear: $schoolYear, departmentId: $department")

        disableUi()

        createService().createUser(User.createUserJson(schoolYear, department))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { user -> // リクエストが成功した場合
                            Log.d("accepted", "user:$user")
                            LoginClient.signUp(user)
                            Log.d("loginuserInfo", "${LoginClient.isSignedUp()}, ${LoginClient.getCurrentUserInfo()}")
                            if(LoginClient.isSignedUp()) {  // ユーザー登録が無事済めば
                                Intent(this, MainActivity::class.java).run {
                                    goToMainActivity()
                                }
                            } else { // ユーザー登録ができていなければ
                                Toast.makeText(this, "ユーザー登録が正常に行えませんでした", Toast.LENGTH_SHORT).show()
                            }
                        },
                        { t -> // POSTでエラーが生じた場合
                            Log.d("denied", "UserPostDenied, ${t.message}\n body: ${t.stackTrace}")
                        }
                )
    }

    private fun disableUi() {
        sign_up_btn.isEnabled = false
        school_year_spinner.isEnabled = false
        department_spinner.isEnabled = false
        sign_up_progress.visibility = View.VISIBLE
    }

    private fun enableUi() {
        sign_up_btn.isEnabled = true
        school_year_spinner.isEnabled = true
        department_spinner.isEnabled = true
        sign_up_progress.visibility = View.GONE
    }

    private fun goToMainActivity() {
        Intent(this, MainActivity::class.java).run {
            finish()
            startActivity(this)
        }
    }

    private inline fun convertToSchoolYear(position: Int) = position + 1
    private fun convertToDepartmentId(position: Int): Int = 200 + position

    companion object {

        private val schoolYears: Array<String> = arrayOf(
                "1年生", "2年生", "3年生", "4年生"
        )

        private val departments: Array<String> = arrayOf(
                "情報工学部　情工１類　Ⅰクラス", "情報工学部　情工１類　Ⅱクラス", "情報工学部　情工２類　Ⅲクラス",
                "情報工学部　情工３類　Ⅳクラス", "情報工学部　情工３類　Ⅴクラス",
                "情報工学部　知能情報工学科", "情報工学部　知能情報工学科（編入）",
                "情報工学部　電子情報工学科", "情報工学部　電子情報工学科（編入）",
                "情報工学部　システム創成情報工学科", "情報工学部　システム創成情報工学科（編入）",
                "情報工学部　機械情報工学科", "情報工学部　機械情報工学科（編入）",
                "情報工学部　生命情報工学科", "情報工学部　生命情報工学科（編入）"
        )
    }
}
