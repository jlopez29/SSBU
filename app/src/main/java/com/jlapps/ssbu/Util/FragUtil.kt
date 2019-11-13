package com.jlapps.ssbu.Util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jlapps.ssbu.R
import com.jlapps.ssbu.View.Fragment.CharacterSelection
import com.jlapps.ssbu.View.Fragment.CharacterStats

object FragUtil {


    fun swapFragment(act: AppCompatActivity,frag: String, backStack: Boolean) {
        when (frag) {
            "CharSelect" -> showFragment(
                act,
                CharacterSelection(),
                "CharSelect",
                backStack,
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
            "CharStats" -> showFragment(
                act,
                CharacterStats(),
                "CharStats",
                backStack,
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
        }
    }

    fun showFragment(act:AppCompatActivity, frag: Fragment, tag: String, bs: Boolean, animIn: Int, animOut: Int) {
        val fragmentTransaction = act.supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(animIn, animOut, animIn, animOut)
            .replace(R.id.cl_main, frag, tag)

        if (bs)
            fragmentTransaction.addToBackStack(tag)

        fragmentTransaction.commit()
    }
}