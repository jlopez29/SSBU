package com.jlapps.ssbu.view.custom

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.ViewAnimationUtils
import kotlin.math.hypot

fun circularRevealView(view: View) {
    val cx: Int = view.width / 2
    val cy: Int = view.height / 2
    val finalRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()
    val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, finalRadius)
    view.visibility = View.VISIBLE
    anim.start()
}

fun circularHideView(view: View) {
    val cx: Int = view.getWidth() / 2
    val cy: Int = view.getHeight() / 2
    val initialRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()
    val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0f)
    anim.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            super.onAnimationEnd(animation)
            view.visibility = View.INVISIBLE
        }
    })
    anim.start()
}