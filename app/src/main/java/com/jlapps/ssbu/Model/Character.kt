package com.jlapps.ssbu.Model

class Character constructor(id:Int,name:String,series:String,tier:Char,image:Int,mark:Int){
    var id = id
    var name = name
    var series = series
    var tier = tier
    var image = image
    var mark = mark
    var isFavorite = false
    var isSelected = false
    lateinit var attributes:CharacterAttributes

    constructor():this(-1,"Unselected","",'Z',-1,-1)
}