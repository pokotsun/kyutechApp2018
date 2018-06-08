package com.gorigolilagmail.kyutechapp2018.view.customView

import android.content.Context
import android.support.v4.content.ContextCompat
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
                                                     item: UserSchedule,
                                                     userDepartment: String,
                                                     isEditing: Boolean=false)
    : LinearLayout(context, attrs, defStyleAttr) {

    var view: View? = null

    init {
        view = LayoutInflater.from(context).inflate(R.layout.class_grid_item, this)
        view?.find<TextView>(R.id.syllabus_name)?.text = item.syllabus.title
        view?.find<TextView>(R.id.class_room_name)?.text = item.syllabus.targetPlace
        view?.find<LinearLayout>(R.id.selected_period_container)?.background = ContextCompat.getDrawable(context, item.getScheduleKindColorId(userDepartment))
    }

    fun setBlankSchedule() {
        view?.find<LinearLayout>(R.id.selected_period_container)?.visibility = View.GONE
    }

}