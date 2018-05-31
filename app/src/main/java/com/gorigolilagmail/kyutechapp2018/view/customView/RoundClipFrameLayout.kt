package com.gorigolilagmail.kyutechapp2018.view.customView

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.FrameLayout
import com.gorigolilagmail.kyutechapp2018.R

class RoundClipFrameLayout @JvmOverloads constructor(context: Context,
                                                    attrs: AttributeSet? = null,
                                                    defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {
    private val mPath: Path = Path()
    private val mRect: RectF = RectF()

    private var mCornerRadius: Int = 0

    init {
        val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundClipFrameLayout, defStyleAttr, 0)
        mCornerRadius = ta.getDimensionPixelSize(R.styleable.RoundClipFrameLayout_cornerRadius, 0)

        ta.recycle()
    }

    fun setCornerRadius(radiusPx: Int) {
        if(mCornerRadius != radiusPx) {
            mCornerRadius = radiusPx
            rebuildPath()
            invalidate()
        }
    }

    private fun rebuildPath() {
        mPath.reset()
        mPath.addRoundRect(mRect, mCornerRadius.toFloat(), mCornerRadius.toFloat(), Path.Direction.CW)
        mPath.close()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mRect.set(0f, 0f, width.toFloat(), height.toFloat())
        rebuildPath()
    }

    override fun dispatchDraw(canvas: Canvas) {
        val save: Int = canvas.save()
        canvas.clipPath(mPath)
        super.dispatchDraw(canvas)
        canvas.restoreToCount(save)
    }
}