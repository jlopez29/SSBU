package com.jlapps.ssbu.Util.SwipeView

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.Region
import android.view.View
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.GravityCompat
import androidx.core.view.MarginLayoutParamsCompat
import androidx.core.view.ViewCompat

/**
 * Clamp the value between a minimum float and maximum float value.
 *
 * @param value The value to clamp.
 * @param min The minimum float value.
 * @param max The maximum float value.
 *
 * @return Value clamped between a minimum float and maximum float value.
 */
internal fun clamp(value: Float, min: Float, max: Float) = Math.max(min, Math.min(value, max))

/**
 * Set the rectangle's coordinates from coordinates of the specified view.
 *
 * @param view View from which to take coordinates.
 */
internal fun Rect.setBoundsFrom(view: View) = set(view.left, view.top, view.right, view.bottom)

/**
 * Calculate circle radius from x and y coordinates.
 *
 * @param x The x coordinate.
 * @param y The y coordinate.
 *
 * @return The circle radius.
 */
internal fun radius(x: Double, y: Double) = Math.sqrt(Math.pow(x, 2.0) + Math.pow(y, 2.0))

/**
 * Perform draw actions in bounds of the specified [View] with selected [Region.Op] modification
 * applied.
 *
 * @param view View from which to get bounds.
 * @param includePadding Whether padding should reduce size of drawing bounds.
 * @param drawAction The draw action to perform.
 */
internal fun <R> Canvas.drawInBoundsOf(
    view: View,
    includePadding: Boolean = false,
    drawAction: (Int) -> R
): R {
    val saveCount = save()

    val translationX = view.translationX.toInt()
    val bounds = clipBounds

    var left = view.left + translationX
    var top = view.top
    var right = view.right + translationX
    var bottom = view.bottom

    if (includePadding) {
        left += view.paddingLeft
        top += view.paddingTop
        right -= view.paddingRight
        bottom -= view.paddingBottom
    }

    bounds.set(left, top, right, bottom)
    clipRect(bounds)

    val result = drawAction(saveCount)

    restoreToCount(saveCount)

    return result
}

/**
 * Set both vertical and horizontal scale of the view to the specified value.
 *
 * @param scale The target scale.
 */
internal fun View.setScale(scale: Float) {
    this.scaleX = scale
    this.scaleY = scale
}

/**
 * Get whether the view is right aligned.
 *
 * @return Whether the view is right aligned.
 */
@SuppressLint("RtlHardcoded")
internal fun View.isRightAligned(): Boolean {
    val gravity = (layoutParams as FrameLayout.LayoutParams).gravity
    val layoutDirection = ViewCompat.getLayoutDirection(this)

    val absGravity = GravityCompat.getAbsoluteGravity(gravity, layoutDirection)
    if (absGravity <= 0) return false

    return absGravity and Gravity.END == Gravity.END ||
            absGravity and Gravity.RIGHT == Gravity.RIGHT
}

/**
 * The total width of the view including both start and end margins.
 */
internal val View.totalWidth: Int
    get() {
        val layoutParams = layoutParams as ViewGroup.MarginLayoutParams
        val marginEnd = MarginLayoutParamsCompat.getMarginEnd(layoutParams)
        val marginStart = MarginLayoutParamsCompat.getMarginStart(layoutParams)

        return marginStart + width + marginEnd
    }

/**
 * The start margin of the view.
 */
internal val View.marginStart: Int
    get() {
        val layoutParams = layoutParams as ViewGroup.MarginLayoutParams
        return MarginLayoutParamsCompat.getMarginStart(layoutParams)
    }

/**
 * The end margin of the view.
 */
internal val View.marginEnd: Int
    get() {
        val layoutParams = layoutParams as ViewGroup.MarginLayoutParams
        return MarginLayoutParamsCompat.getMarginEnd(layoutParams)
    }