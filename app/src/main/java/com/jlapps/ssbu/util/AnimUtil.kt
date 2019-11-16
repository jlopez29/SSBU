package com.jlapps.ssbu.util

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.jlapps.ssbu.R

object AnimUtil {

    fun fadeView(context: Context, fadeIn:Boolean, view: View){
        lateinit var animation: Animation

        if(fadeIn) {
            animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                    view.visibility = View.VISIBLE
                }

                override fun onAnimationStart(animation: Animation?) {
                }
            })
        }else
        {
            animation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                    view.visibility = View.GONE
                }

                override fun onAnimationStart(animation: Animation?) {
                }
            })
        }
        view.startAnimation(animation)
    }
    fun animateView(context: Context, anim:Int, view: View, onEnd: () -> Unit) {
        val animation = AnimationUtils.loadAnimation(context, anim)
        animation.setAnimationListener(object : Animation.AnimationListener{

            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                onEnd()
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
        view.startAnimation(animation)
    }
}