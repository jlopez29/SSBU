package com.jlapps.ssbu.Util.SwipeView

interface SwipeListener {
    fun onSwipedLeft(swipeActionView: SwipeView): Boolean
    fun onSwipedRight(swipeActionView: SwipeView): Boolean
}