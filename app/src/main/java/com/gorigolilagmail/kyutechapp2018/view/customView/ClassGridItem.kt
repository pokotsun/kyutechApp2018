package com.gorigolilagmail.kyutechapp2018.view.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.extensions.toDip

/**
 * Created by pokotsun on 18/03/12.
 */

class ClassGridItem @JvmOverloads constructor(context: Context,
                                              attrs: AttributeSet? = null,
                                              defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {

    var view: View? = null

    init {
        view = LayoutInflater.from(context).inflate(R.layout.class_grid_item, this)
    }

}