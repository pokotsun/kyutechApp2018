package com.planningdevgmail.kyutechapp2018.extensions

import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.view.ViewManager
import com.planningdevgmail.kyutechapp2018.view.customView.CircularTextView
import org.jetbrains.anko.custom.ankoView

/**
 * Created by pokotsun on 18/03/11.
 */

inline fun ViewManager.circularTextView(theme: Int = 0) = circularTextView(theme) {}
inline fun ViewManager.circularTextView(theme: Int = 0, init: CircularTextView.() -> Unit) = ankoView({ CircularTextView(it) }, theme, init)
