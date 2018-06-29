package com.planningdevgmail.kyutechapp2018.view.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.planningdevgmail.kyutechapp2018.R

/**
 * Created by pokotsun on 18/03/10.
 */
class CustomTab @JvmOverloads constructor(context: Context,
                                          attrs: AttributeSet? = null,
                                          defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {

    var view: View? = null


    init {
        view = LayoutInflater.from(context).inflate(R.layout.tab_layout, this)
    }

    fun setText(text: String) {
        view?.findViewById<TextView>(R.id.tab_text)?.text = text
    }
}