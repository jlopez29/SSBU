package com.jlapps.ssbu.util.SwipeView

import android.view.View

interface SwipeAnimator {
    fun onUpdateSwipeProgress(view: View, progress: Float, minActivationProgress: Float)

    fun onActivate()
}
