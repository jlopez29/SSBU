package com.jlapps.ssbu.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jlapps.ssbu.R
import com.jlapps.ssbu.util.AnimUtil.fadeView
import com.jlapps.ssbu.util.FragUtil
import com.jlapps.ssbu.viewmodel.SmashViewModel
import kotlinx.android.synthetic.main.loading.*

class MainActivity : AppCompatActivity(){

    val TAG = "MAIN"
    lateinit var viewModel:SmashViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this)[SmashViewModel::class.java]

        viewModel.getCharacters(this)

        loading.visibility = View.VISIBLE

        FragUtil.swapFragment(this,FragUtil.fragmentCharacterSelect,false,Bundle())

        viewModel.loading.observe(this, Observer{isLoading ->
            if(isLoading != null){
                if(isLoading)
                    fadeView(this,true,loading)
                else
                    fadeView(this,false,loading)

            }
        })
    }

}

