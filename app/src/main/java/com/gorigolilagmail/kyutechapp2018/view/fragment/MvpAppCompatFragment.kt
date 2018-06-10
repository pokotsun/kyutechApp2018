package com.gorigolilagmail.kyutechapp2018.view.fragment

import android.support.v4.app.Fragment
import android.widget.Toast
import com.gorigolilagmail.kyutechapp2018.view.activity.MvpView

abstract class MvpAppCompatFragment: Fragment(), MvpView {
    override fun showToast(msg: String) = Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}