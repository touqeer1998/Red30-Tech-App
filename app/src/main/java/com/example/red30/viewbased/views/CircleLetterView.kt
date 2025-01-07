package com.example.red30.viewbased.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.red30.viewbased.pickBackgroundColorForName
import kotlin.math.min

class CircleLetterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var name: String = ""
        set(value) {
            field = value
            letterColor = pickBackgroundColorForName(context, value)

            invalidate()
            requestLayout()
        }

    private var letterColor: Int = Color.CYAN
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = (width / 2).toFloat()
        val centerY = (height / 2).toFloat()
        val radius = min(centerX, centerY) - 5

        paint.color = letterColor
        canvas.drawCircle(centerX, centerY, radius, paint)

        paint.color = Color.WHITE
        paint.textSize = radius * 0.9f
        paint.textAlign = Paint.Align.CENTER

        // Center the letter
        canvas.drawText(
            name.first().uppercase(),
            centerX,
            centerY - ((paint.descent() + paint.ascent()) / 2),
            paint
        )
    }
}
