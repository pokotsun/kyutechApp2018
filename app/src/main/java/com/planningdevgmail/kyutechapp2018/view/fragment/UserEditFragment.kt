package com.planningdevgmail.kyutechapp2018.view.fragment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast

import com.planningdevgmail.kyutechapp2018.R
import com.planningdevgmail.kyutechapp2018.client.LoginClient
import com.planningdevgmail.kyutechapp2018.client.RetrofitServiceGenerator.createService
import com.planningdevgmail.kyutechapp2018.model.User
import com.planningdevgmail.kyutechapp2018.view.activity.LoginActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

// ユーザー情報を更新するフラグメント
class UserEditFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(ContextThemeWrapper(activity, R.style.MyAlertDialogStyle))
        val inflater = activity.layoutInflater

        val view = inflater.inflate(R.layout.fragment_user_edit, null)
        val schoolYearSpinner: Spinner = view.findViewById(R.id.school_year_spinner)
        val departmentSpinner = view.findViewById<Spinner>(R.id.department_spinner)

        val loginUser = LoginClient.getCurrentUserInfo()

        // 各スピナーの設定
        val schoolYarSpinnerAdapter = ArrayAdapter<String>(context, R.layout.spinner_item, LoginActivity.schoolYears)
        schoolYarSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        schoolYearSpinner.adapter = schoolYarSpinnerAdapter

        val departmentSpinnerAdapter = ArrayAdapter<String>(context, R.layout.spinner_item, LoginActivity.departments)
        departmentSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        departmentSpinner.adapter = departmentSpinnerAdapter

        Log.d("LoginUserInfo", "$loginUser")
        schoolYearSpinner.setSelection(loginUser?.schoolYear?: 0)
        departmentSpinner.setSelection((loginUser?.department?: 200) - 200)

        // ビルダーの設定
        builder.setView(view)
                .setTitle("ユーザー情報の変更")
                .setPositiveButton("変更") { dialog, which ->
                }
                .setNegativeButton("キャンセル") { dialog, which ->
                    dialog.cancel()
                }
        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        // ポジティブボタンとネガティブボタンの色を変更
        val nButton = (dialog as? AlertDialog)?.getButton(DialogInterface.BUTTON_NEGATIVE)
        val pButton = (dialog as? AlertDialog)?.getButton(DialogInterface.BUTTON_POSITIVE)
        val schoolYearSpinner: Spinner = dialog.findViewById(R.id.school_year_spinner)
        val departmentSpinner = dialog.findViewById<Spinner>(R.id.department_spinner)
        nButton?.setTextColor(ContextCompat.getColor(context, R.color.kyutech_main_color_dark))
        pButton?.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))

        val loginUser = LoginClient.getCurrentUserInfo()
        val progressBar: ProgressBar = dialog.findViewById(R.id.progress_bar)
        pButton?.setOnClickListener {
            createService().updateUser(loginUser?.id?: throw NullPointerException(),
                    User.createJson(
                            schoolYear = schoolYearSpinner.selectedItemPosition,
                            departmentId =  convertToDepartmentId(departmentSpinner.selectedItemPosition))
            )
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { progressBar.visibility = View.VISIBLE }
                    .doOnComplete { progressBar.visibility = View.GONE }
                    .subscribe {
                        LoginClient.signOut()
                        LoginClient.signUp(it)
                        dismiss()
                    }
        }
    }

    private fun convertToDepartmentId(position: Int): Int = 200 + position


    companion object {

        @JvmStatic
        fun newInstance() =
                UserEditFragment()
    }
}
