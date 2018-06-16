package com.gorigolilagmail.kyutechapp2018.view.fragment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.ContextThemeWrapper
import android.widget.ArrayAdapter
import android.widget.Spinner

import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.view.activity.LoginActivity

class UserEditFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(ContextThemeWrapper(activity, R.style.MyAlertDialogStyle))
        val inflater = activity.layoutInflater

        val view = inflater.inflate(R.layout.fragment_user_edit, null)
        val schoolYearSpinner: Spinner = view.findViewById(R.id.school_year_spinner)
        val departmentSpinner = view.findViewById<Spinner>(R.id.department_spinner)

        // 各スピナーの設定
        val schoolYarSpinnerAdapter = ArrayAdapter<String>(context, R.layout.spinner_item, LoginActivity.schoolYears)
        schoolYarSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        schoolYearSpinner.adapter = schoolYarSpinnerAdapter

        val departmentSpinnerAdapter = ArrayAdapter<String>(context, R.layout.spinner_item, LoginActivity.departments)
        departmentSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        departmentSpinner.adapter = departmentSpinnerAdapter

        // ビルダーの設定
        builder.setView(view)
                .setTitle("ユーザー情報の変更")
                .setPositiveButton("変更", { dialog, which ->

                })
                .setNegativeButton("キャンセル", { dialog, which ->
                    dialog.cancel()
                })
        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        // ポジティブボタンとネガティブボタンの色を変更
        val nButton = (dialog as? AlertDialog)?.getButton(DialogInterface.BUTTON_NEGATIVE)
        val pButton = (dialog as? AlertDialog)?.getButton(DialogInterface.BUTTON_POSITIVE)
        nButton?.setTextColor(ContextCompat.getColor(context, R.color.kyutech_main_color_dark))
        pButton?.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
    }

    companion object {

        @JvmStatic
        fun newInstance() =
                UserEditFragment()
    }
}
