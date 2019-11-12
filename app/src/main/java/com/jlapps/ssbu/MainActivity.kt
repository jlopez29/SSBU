package com.jlapps.ssbu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jlapps.ssbu.Util.FragUtil

class MainActivity : AppCompatActivity(){

    val TAG = "MAIN"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FragUtil.swapFragment(this,"CharSelect",false)
    }

}

