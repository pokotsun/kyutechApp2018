package com.planningdevgmail.kyutechapp2018.view.activity

import android.graphics.Color
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast

interface MvpView {
    fun showToast(msg: String)
    fun showShortSnackBar(msg: String, view: View)
}

abstract class MvpAppCompatActivity: AppCompatActivity(), MvpView {
    override fun showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    // SnackBarを表示する
    override fun showShortSnackBar(msg: String, view: View) {
        val snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
//        snackbar.view.setBackgroundColor(Color.BLACK)
        val textView = snackbar.view.findViewById(android.support.design.R.id.snackbar_text) as? TextView
        textView?.setTextColor(Color.WHITE)
        snackbar.show()
    }
}