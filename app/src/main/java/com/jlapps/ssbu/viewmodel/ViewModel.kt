package com.jlapps.ssbu.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jlapps.ssbu.api.Characters

class SmashViewModel : ViewModel(){

    val charactersUpdated = MutableLiveData<Boolean>()
    val isCharacter1 = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun setChar(one:Boolean){
        isCharacter1.value = one
    }

    fun getCharacters(ctx:Context){
        Characters.getChars(ctx,charactersUpdated)
    }
}