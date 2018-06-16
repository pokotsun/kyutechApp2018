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
import com.gorigolilagmail.kyutechapp2018.presenter.LoginActivityPresenter

import kotlinx.android.synthetic.main.activity_login.*

interface LoginMvpView: MvpView {
    fun goToMainActivity(msg: String = "", msgShown: Boolean=true)
    fun isSignedUp(): Boolean
}

class LoginActivity : MvpAppCompatActivity(), LoginMvpView {
    private val presenter = LoginActivityPresenter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        LoginClient.init(applicationContext)

        if(LoginClient.isSignedUp()) { // ログイン済みであれば
            goToMainActivity(msgShown = false)
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
        val schoolYear: Int = school_year_spinner.selectedItemPosition
        val department: Int = convertToDepartmentId(department_spinner.selectedItemPosition)
        Log.d("items", "schoolYear: $schoolYear, departmentId: $department")

        disableUi() // UIを使えなくする

        // ユーザー作成
        presenter.createUser(schoolYear, department)
    }

    private fun disableUi() { // Uiを使えなくする
        sign_up_btn.isEnabled = false
        school_year_spinner.isEnabled = false
        department_spinner.isEnabled = false
        sign_up_progress.visibility = View.VISIBLE
    }

    private fun enableUi() { // UIを使えるようにする
        sign_up_btn.isEnabled = true
        school_year_spinner.isEnabled = true
        department_spinner.isEnabled = true
        sign_up_progress.visibility = View.GONE
    }

    override fun goToMainActivity(msg: String, msgShown: Boolean) {
        Intent(this, MainActivity::class.java).run {
            if(msgShown) {
                showToast(msg)
            }
            finish()
            startActivity(this)
        }
    }



    override fun isSignedUp(): Boolean = LoginClient.isSignedUp()

    private fun convertToDepartmentId(position: Int): Int = 200 + position

    companion object {

        val schoolYears: Array<String> = arrayOf(
                "1年生", "2年生", "3年生", "4年生"
        )

        val departments: Array<String> = arrayOf(
                "情工１類　Ⅰクラス", "情工１類　Ⅱクラス", "情工２類　Ⅲクラス",
                "情工３類　Ⅳクラス", "情工３類　Ⅴクラス",
                "知能情報工学科", "知能情報工学科（編入）",
                "電子情報工学科", "電子情報工学科（編入）",
                "システム創成情報工学科", "システム創成情報工学科（編入）",
                "機械情報工学科", "機械情報工学科（編入）",
                "生命情報工学科", "生命情報工学科（編入）"
        )
    }
}
