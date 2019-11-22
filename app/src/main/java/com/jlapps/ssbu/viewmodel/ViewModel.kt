package com.jlapps.ssbu.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SmashViewModel : ViewModel(){

    val isCharacter1 = MutableLiveData<Boolean>()

    fun setChar(one:Boolean){
        isCharacter1.value = one
    }
}