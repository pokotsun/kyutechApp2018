package com.gorigolilagmail.kyutechapp2018.extensions

import android.content.Context

/**
 * Created by pokotsun on 18/03/10.
 */

fun Float.toDip(context: Context): Float =
        this * context.resources.displayMetrics.density