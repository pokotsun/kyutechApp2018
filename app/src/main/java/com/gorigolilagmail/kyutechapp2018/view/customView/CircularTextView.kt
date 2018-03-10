package com.gorigolilagmail.kyutechapp2018.view.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView

/**
 * Created by pokotsun on 18/03/10.
 */

class CircularTextView @JvmOverloads constructor(context: Context,
                                                attrs: AttributeSet? = null,
                                                defStyleAttr: Int = 0)
    : TextView(context, attrs, defStyleAttr) {

    private val strokeWidth: Float = 0.toFloat()
    private var strokeColor = 0
    private var solidedColor = 0


    override fun draw(canvas: Canvas?) {
        val circlePaint: Paint = Paint()
        circlePaint.color = solidedColor
        circlePaint.flags = Paint.ANTI_ALIAS_FLAG

        val strokePaint = Paint()
        strokePaint.color = strokeColor
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

    fun setStrokeColor(color: String) {
        strokeColor = Color.parseColor(color)
    }

    fun setSolidColor(color: String) {
        solidedColor = Color.parseColor(color)
    }
}