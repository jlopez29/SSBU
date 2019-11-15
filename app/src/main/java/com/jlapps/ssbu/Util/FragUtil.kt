package com.jlapps.ssbu.Util

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jlapps.ssbu.R
import com.jlapps.ssbu.View.Fragment.CharacterSelection
import com.jlapps.ssbu.View.Fragment.CharacterStats

object FragUtil {

    const val fragmentCharacterSelect = "CharSelect"
    const val fragmentCharacterStats = "CharStats"


    @JvmOverloads
    fun swapFragment(act: AppCompatActivity,frag: String, backStack: Boolean,args: Bundle) {
        when (frag) {
            fragmentCharacterSelect -> showFragment(
                act,
                CharacterSelection(),
                fragmentCharacterSelect,
                backStack,
                R.anim.slide_in_right,
                R.anim.slide_out_right,
                args
            )
            fragmentCharacterStats -> showFragment(
                act,
                CharacterStats(),
                fragmentCharacterStats,
                backStack,
                R.anim.slide_in_right,
                R.anim.slide_out_right,
                args
            )
        }
    }

    @JvmOverloads
    private fun showFragment(act:AppCompatActivity, frag: Fragment, tag: String, bs: Boolean, animIn: Int, animOut: Int,args:Bundle) {
        frag.arguments = args

        val fragmentTransaction = act.supportFragmentManager.beginTransaction()

        fragmentTransaction.setCustomAnimations(animIn, animOut, animIn, animOut)
            .replace(R.id.cl_main, frag, tag)

        if (bs)
            fragmentTransaction.addToBackStack(tag)

        fragmentTransaction.commit()
    }
}