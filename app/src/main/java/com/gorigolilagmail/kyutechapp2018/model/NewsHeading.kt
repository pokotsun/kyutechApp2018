package com.gorigolilagmail.kyutechapp2018.model

import android.content.Context
import android.support.v4.content.ContextCompat
import com.gorigolilagmail.kyutechapp2018.R
import java.util.*

/**
 * Created by pokotsun on 18/03/10.
 */

data class NewsHeading(
        val newsHeadingCode: Int,
        val shortName: String,
        val name: String,
        val colorCode: String,
        val updatedAt: String) {
}