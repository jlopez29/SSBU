package com.jlapps.ssbu.util.SwipeView

import android.animation.ObjectAnimator
import android.graphics.*
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.util.Property
import androidx.annotation.ColorInt

internal class SwipeRippleDrawable : Drawable(), Animatable {
    companion object {
        private val PROGRESS =
            object : Property<SwipeRippleDrawable, Float>(Float::class.java, "progress") {
                override fun get(drawable: SwipeRippleDrawable) = drawable.progress

                override fun set(drawable: SwipeRippleDrawable, value: Float) {
                    drawable.progress = value
                    drawable.invalidateSelf()
                }
            }
    }

    private val animator = ObjectAnimator.ofFloat(this, PROGRESS, 0f, 1f)
    private val drawBounds = Rect()
    private val paint = Paint()

    private var centerX = 0f
    private var centerY = 0f
    internal var progress = 0f

    fun restart() {
        stop()
        start()
    }

    fun setCenter(x: Float, y: Float) {
        centerX = x
        centerY = y
        invalidateSelf()
    }

    val hasColor: Boolean
        get() = color != -1

    var maxRadius = 0f
        set(value) {
            field = value
            invalidateSelf()
        }

    var color: Int
        @ColorInt get() = paint.color
        set(@ColorInt value) {
            paint.color = value
            invalidateSelf()
        }

    var duration: Long
        get() = animator.duration
        set(value) {
            animator.duration = value
        }

    override fun draw(canvas: Canvas) {
        paint.alpha = ((1f - progress) * 255f).toInt()

        val saveCount = canvas.save()
        canvas.clipRect(drawBounds)
        canvas.drawCircle(centerX, centerY, maxRadius * progress, paint)
        canvas.restoreToCount(saveCount)
    }

    override fun isRunning() = animator.isRunning

    override fun start() = animator.start()

    override fun stop() = animator.cancel()

    override fun onBoundsChange(bounds: Rect) {
        drawBounds.set(bounds)
    }

    override fun getOpacity() = PixelFormat.OPAQUE

    override fun setColorFilter(colorFilter: ColorFilter) {
    }

    override fun setAlpha(value: Int) {
    }
}