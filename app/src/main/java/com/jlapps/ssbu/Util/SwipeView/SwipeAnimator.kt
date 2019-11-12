package com.jlapps.ssbu.Util.SwipeView

import android.view.View

interface SwipeAnimator {
    fun onUpdateSwipeProgress(view: View, progress: Float, minActivationProgress: Float)

    fun onActivate()
}
