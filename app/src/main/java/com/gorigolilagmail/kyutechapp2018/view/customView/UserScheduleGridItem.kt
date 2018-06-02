package com.gorigolilagmail.kyutechapp2018.view.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.model.UserSchedule
import org.jetbrains.anko.find

/**
 * Created by pokotsun on 18/03/12.
 */

class UserScheduleGridItem @JvmOverloads constructor(context: Context,
                                                     attrs: AttributeSet? = null,
                                                     defStyleAttr: Int = 0,
                                                     item: UserSchedule)
    : LinearLayout(context, attrs, defStyleAttr) {

    var view: View? = null

    init {
        view = LayoutInflater.from(context).inflate(R.layout.class_grid_item, this)
        view?.find<TextView>(R.id.class_name)?.text = item.syllabus.title

        view?.setOnClickListener {

        }
    }

}