package com.jlapps.ssbu.model

import java.io.Serializable

class Character constructor(id:Int,name:String,series:String,tier:Char,image:Int,mark:Int,attributes: CharacterAttributes) : Serializable{
    var id = id
    var name = name
    var series = series
    var tier = tier
    var image = image
    var mark = mark
    var isFavorite = false
    var isSelected = false
    var attributes = attributes

    constructor():this(-1,"Unselected","",'Z',-1,-1, CharacterAttributes(0,0,0,0,0,0,0,0))
}