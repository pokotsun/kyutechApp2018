package com.gorigolilagmail.kyutechapp2018.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter

import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.client.RetrofitServiceGenerator.Companion.createService
import com.gorigolilagmail.kyutechapp2018.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_login.*
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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

        disableUi()

        createService().createUser(User.createUserJson(schoolYear, department))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { user ->
                            Log.d("accepted", "user:$user")
                        },
                        { t ->
                            Log.d("denied", "UserPostDenied, ${t.message}")
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

//    private fun attemptLogin() {
//
//        // Reset errors.
//        email.error = null
//        password.error = null
//
//        // Store values at the time of the login attempt.
//        val emailStr = email.text.toString()
//        val passwordStr = password.text.toString()
//
//        var cancel = false
//        var focusView: View? = null
//
//        // Check for a valid password, if the user entered one.
//        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
//            password.error = getString(R.string.error_invalid_password)
//            focusView = password
//            cancel = true
//        }
//
//        // Check for a valid email address.
//        if (TextUtils.isEmpty(emailStr)) {
//            email.error = getString(R.string.error_field_required)
//            focusView = email
//            cancel = true
//        } else if (!isEmailValid(emailStr)) {
//            email.error = getString(R.string.error_invalid_email)
//            focusView = email
//            cancel = true
//        }
//
//        if (cancel) {
//            // There was an error; don't attempt login and focus the first
//            // form field with an error.
//            focusView?.requestFocus()
//        } else {
//            // Show a progress spinner, and kick off a background task to
//            showProgress(true)
//        }
//    }
//
//    private fun isEmailValid(email: String): Boolean {
//        //TODO: Replace this with your own logic
//        return email.contains("@")
//    }
//
//    private fun isPasswordValid(password: String): Boolean {
//        //TODO: Replace this with your own logic
//        return password.length > 4
//    }

//    private fun showProgress(show: Boolean) {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
//
//            login_form.visibility = if (show) View.GONE else View.VISIBLE
////            login_form.animate()
////                    .setDuration(shortAnimTime)
////                    .alpha((if (show) 0 else 1).toFloat())
////                    .setListener(object : AnimatorListenerAdapter() {
////                        override fun onAnimationEnd(animation: Animator) {
//////                            login_form.visibility = if (show) View.GONE else View.VISIBLE
////                        }
////                    })
//
//            login_progress.visibility = if (show) View.VISIBLE else View.GONE
////            login_progress.animate()
////                    .setDuration(shortAnimTime)
////                    .alpha((if (show) 1 else 0).toFloat())
////                    .setListener(object : AnimatorListenerAdapter() {
////                        override fun onAnimationEnd(animation: Animator) {
////                            login_progress.visibility = if (show) View.VISIBLE else View.GONE
////                        }
////                    })
//        } else { // API level < 23のとき
//            login_progress.visibility = if (show) View.VISIBLE else View.GONE
//            login_form.visibility = if (show) View.GONE else View.VISIBLE
//        }
//    }

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

        /**
         * Id to identity READ_CONTACTS permission request.
         */
        private val REQUEST_READ_CONTACTS = 0
    }
}
