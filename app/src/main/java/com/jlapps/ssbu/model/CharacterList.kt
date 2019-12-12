package com.jlapps.ssbu.model

import com.jlapps.ssbu.R

object CharacterList {
    var characters = ArrayList<Character>()

    fun getChar(name:String) = characters.find{it.name == name}
}