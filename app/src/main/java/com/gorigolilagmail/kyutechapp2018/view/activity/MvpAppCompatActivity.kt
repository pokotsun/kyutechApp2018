package com.gorigolilagmail.kyutechapp2018.view.activity

import android.support.v7.app.AppCompatActivity
import android.widget.Toast

interface MvpView {
    fun showToast(msg: String)
}

abstract class MvpAppCompatActivity: AppCompatActivity(), MvpView {
    override fun showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}