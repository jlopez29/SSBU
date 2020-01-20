package com.jlapps.ssbu.view

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jlapps.ssbu.R
import com.jlapps.ssbu.model.StorageManger
import com.jlapps.ssbu.util.AnimUtil.fadeView
import com.jlapps.ssbu.util.FragUtil
import com.jlapps.ssbu.view.custom.QuickWheel
import com.jlapps.ssbu.view.custom.SelectWheel
import com.jlapps.ssbu.view.widget.CharacterWidgetProvider
import com.jlapps.ssbu.viewmodel.SmashViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.loading.*


class MainActivity : AppCompatActivity(){

    val TAG = "MAIN"
    lateinit var viewModel:SmashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        QuickWheel.initializeSelectWheel(context = this,parentContainer = cl_main)

        StorageManger.init(this)

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
