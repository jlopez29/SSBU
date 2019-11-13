package com.jlapps.ssbu.View.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jlapps.ssbu.Model.Character
import kotlinx.android.synthetic.main.fragment_character_stats.*
import com.jlapps.ssbu.R


class CharacterStats : Fragment(){

    val TAG = "CharStats"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_character_stats,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}

