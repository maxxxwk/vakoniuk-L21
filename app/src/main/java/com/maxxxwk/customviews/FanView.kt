package com.maxxxwk.customviews

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class FanView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attributeSet, defStyleAttr) {

    private var power: FanPower = DEFAULT_POWER
    private var rotationDegrees = power.speed

    init {
        BitmapFactory.decodeResource(resources, R.drawable.fan).apply {
            setImageBitmap(this)
        }
        val typedArray = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.FanView
        )
        power =
            FanPower.values()[typedArray.getInt(R.styleable.FanView_power, FanPower.OFF.ordinal)]
        typedArray.recycle()
    }

    companion object {
        private val DEFAULT_POWER = FanPower.OFF
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            it.translate((width / 2).toFloat(), (height / 2).toFloat())
            it.rotate(rotation(power.speed).toFloat())
            it.translate(-(width / 2).toFloat(), -(height / 2).toFloat())
        }
        postInvalidateOnAnimation()
        super.onDraw(canvas)
    }

    private fun rotation(delta: Int): Int {
        rotationDegrees += delta
        return rotationDegrees
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