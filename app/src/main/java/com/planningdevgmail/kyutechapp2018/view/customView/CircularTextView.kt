package com.planningdevgmail.kyutechapp2018.view.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView
import com.planningdevgmail.kyutechapp2018.extensions.toDip

/**
 * Created by pokotsun on 18/03/10.
 */

class CircularTextView @JvmOverloads constructor(context: Context,
                                                attrs: AttributeSet? = null,
                                                defStyleAttr: Int = 0)
    : TextView(context, attrs, defStyleAttr) {

    private var strokeWidth: Float = 1.toFloat().toDip(context)
    private var circleBackgroundColor: Int = Color.WHITE
    private var circleStrokeColor: Int = Color.BLACK


    override fun draw(canvas: Canvas?) {
        val circlePaint = Paint()
        circlePaint.color = circleBackgroundColor
        circlePaint.flags = Paint.ANTI_ALIAS_FLAG

        val strokePaint = Paint()
        strokePaint.color = circleStrokeColor
        strokePaint.flags = Paint.ANTI_ALIAS_FLAG

        val h: Int = height
        val w: Int = width

        val diameter: Int = if(h > w) h else w
        val radius: Int = diameter / 2

        height = diameter
        width = diameter

        canvas!!.drawCircle((diameter/2).toFloat(), (diameter/2).toFloat(), radius.toFloat(), strokePaint)
        canvas!!.drawCircle((diameter/2).toFloat(), (diameter/2).toFloat(), (radius - strokeWidth), circlePaint)

        super.draw(canvas)
    }

    fun setStrokeWidth(dp: Float) {
        strokeWidth = dp.toDip(context)
    }

    fun setCircleBackgroundColor(color: Int) {
        circleBackgroundColor = color
    }

    fun setCircleStrokeColor(color: Int) {
        circleStrokeColor = color
    }
}