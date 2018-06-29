package com.planningdevgmail.kyutechapp2018.view.fragment

import android.graphics.Color
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.planningdevgmail.kyutechapp2018.view.activity.MvpView

abstract class MvpAppCompatFragment: Fragment(), MvpView {
    override fun showToast(msg: String) = Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

    // SnackBarを表示する
    override fun showShortSnackBar(msg: String, view: View) {
        val snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
//        snackbar.view.setBackgroundColor(Color.BLACK)
        val textView = snackbar.view.findViewById(android.support.design.R.id.snackbar_text) as? TextView
        textView?.setTextColor(Color.WHITE)
        snackbar.show()
    }
}