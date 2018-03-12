package com.gorigolilagmail.kyutechapp2018.view.customView

import android.content.Context
import android.util.AttributeSet
import android.widget.GridView

/**
 * Created by pokotsun on 18/03/13.
 */

class CustomGridView @JvmOverloads constructor(context: Context,
                                               attrs: AttributeSet? = null,
                                               defStyleAttr: Int = 0)
    : GridView(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val expandSpec = MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK,
                MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, expandSpec)

        val params = layoutParams
        params.height = measuredHeight
    }
}