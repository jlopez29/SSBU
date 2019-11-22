package com.jlapps.ssbu.util

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jlapps.ssbu.R
import com.jlapps.ssbu.view.fragment.CharacterCompare
import com.jlapps.ssbu.view.fragment.CharacterSelection
import com.jlapps.ssbu.view.fragment.CharacterStats

object FragUtil {

    const val fragmentCharacterSelect = "CharSelect"
    const val fragmentCharacterStats = "CharStats"
    const val fragmentCharacterComp = "CharComp"


    fun swapFragment(act: AppCompatActivity,frag: String, backStack: Boolean,args: Bundle) {
        when (frag) {
            fragmentCharacterSelect -> replaceFragment(
                act,
                CharacterSelection(),
                fragmentCharacterSelect,
                backStack,
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right,
                args
            )
            fragmentCharacterStats -> replaceFragment(
                act,
                CharacterStats(),
                fragmentCharacterStats,
                backStack,
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right,
                args
            )
            fragmentCharacterComp -> addFragment(
                    act,
                    CharacterCompare(),
                    fragmentCharacterStats,
                    true,
                    R.anim.slide_up,
                    R.anim.slide_down,
                    R.anim.slide_up,
                    R.anim.slide_down,
                    args
            )
        }
    }

    private fun replaceFragment(act:AppCompatActivity, frag: Fragment, tag: String, bs: Boolean, animIn: Int, animOut: Int,popIn: Int, popOut: Int,args:Bundle) {
        frag.arguments = args

        val fragmentTransaction = act.supportFragmentManager.beginTransaction()

        fragmentTransaction.setCustomAnimations(animIn, animOut, popIn, popOut)
            .replace(R.id.cl_main, frag, tag)

        if (bs)
            fragmentTransaction.addToBackStack(tag)

        fragmentTransaction.commit()
    }
    private fun addFragment(act:AppCompatActivity, frag: Fragment, tag: String, bs: Boolean, animIn: Int, animOut: Int,popIn: Int, popOut: Int,args:Bundle) {
        frag.arguments = args

        val fragmentTransaction = act.supportFragmentManager.beginTransaction()

        fragmentTransaction.setCustomAnimations(animIn, animOut, popIn, popOut)
                .add(R.id.cl_main, frag, tag)

        if (bs)
            fragmentTransaction.addToBackStack(tag)

        fragmentTransaction.commit()
    }
}