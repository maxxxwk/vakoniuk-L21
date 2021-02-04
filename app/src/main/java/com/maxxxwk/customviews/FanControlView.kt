package com.maxxxwk.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class FanControlView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attributeSet, defStyleAttr, defStyleRes) {

    var power: FanPower = DEFAULT_FAN_POWER
        set(value) {
            field = value
            invalidate()
        }
    private var size: Int = 0
    private val paint = Paint()
    var circleColor: Int = DEFAULT_CIRCLE_COLOR
    var marksColor: Int = DEFAULT_MARKS_COLOR
    var stateIndicatorColor = DEFAULT_STATE_INDICATOR_COLOR
    var marksTextSize = resources.getDimension(R.dimen.fan_control_view_marks_default_textSize)

    init {
        val typedArray = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.FanControlView,
            defStyleAttr,
            defStyleRes
        )
        power =
            FanPower.values()[typedArray.getInt(
                R.styleable.FanControlView_fanPower,
                DEFAULT_FAN_POWER.ordinal
            )]
        circleColor =
            typedArray.getColor(R.styleable.FanControlView_circleColor, DEFAULT_CIRCLE_COLOR)
        marksColor = typedArray.getColor(R.styleable.FanControlView_marksColor, DEFAULT_MARKS_COLOR)
        stateIndicatorColor = typedArray.getColor(
            R.styleable.FanControlView_stateIndicatorColor,
            DEFAULT_STATE_INDICATOR_COLOR
        )
        marksTextSize = typedArray.getDimension(
            R.styleable.FanControlView_marksTextSize,
            resources.getDimension(R.dimen.fan_control_view_marks_default_textSize)
        )
        typedArray.recycle()
    }

    companion object {
        private const val DEFAULT_CIRCLE_COLOR = Color.GREEN
        private const val DEFAULT_MARKS_COLOR = Color.BLACK
        private const val DEFAULT_STATE_INDICATOR_COLOR = Color.RED
        private val DEFAULT_FAN_POWER = FanPower.OFF
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        size = measuredWidth.coerceAtLeast(measuredHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            drawCircle(it)
            drawMarks(it)
            drawStateIndicator(it)
        }
        super.onDraw(canvas)
    }

    private fun drawCircle(canvas: Canvas) {
        paint.apply {
            color = circleColor
            style = Paint.Style.FILL
        }
        val radius = (size / 2f) * 0.8f
        canvas.drawCircle(size / 2f, size / 2f, radius, paint)
    }

    private fun drawMarks(canvas: Canvas) {
        paint.apply {
            color = marksColor
            style = Paint.Style.FILL
            textSize = marksTextSize
            textAlign = Paint.Align.CENTER
        }
        val numberOfMarks = FanPower.values().size
        val deltaAngle = PI / (numberOfMarks + 1)
        val radius = (size / 2f) * 0.85f
        canvas.translate(size / 2f, size / 2f)
        for (i in 1..numberOfMarks) {
            val x = (radius * cos(deltaAngle * i + PI)).toFloat()
            val y = (radius * sin(deltaAngle * i + PI)).toFloat()
            canvas.drawText((i - 1).toString(), x, y, paint)
        }
        canvas.translate(-size / 2f, -size / 2f)
    }

    private fun drawStateIndicator(canvas: Canvas) {
        paint.apply {
            color = stateIndicatorColor
            style = Paint.Style.FILL
        }
        val numberOfMarks = FanPower.values().size
        val angle = (PI / (numberOfMarks + 1)) * (power.ordinal + 1)
        val radius = (size / 2f) * 0.6f
        val indicatorRadius = size * 0.03f
        val x = (radius * cos(angle + PI)).toFloat()
        val y = (radius * sin(angle + PI)).toFloat()
        canvas.translate(size / 2f, size / 2f)
        canvas.drawCircle(x, y, indicatorRadius, paint)
        canvas.translate(-size / 2f, -size / 2f)
    }

    fun increasePower() {
        if ((power.ordinal + 1) != FanPower.values().size) {
            power = FanPower.values()[power.ordinal + 1]
        }
    }

    fun decreasePower() {
        if (power.ordinal != 0) {
            power = FanPower.values()[power.ordinal - 1]
        }
    }
}