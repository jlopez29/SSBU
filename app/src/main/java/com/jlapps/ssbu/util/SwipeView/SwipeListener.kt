package com.jlapps.ssbu.util.SwipeView

interface SwipeListener {
    fun onSwipedLeft(swipeActionView: SwipeView): Boolean
    fun onSwipedRight(swipeActionView: SwipeView): Boolean
}