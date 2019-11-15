package com.jlapps.ssbu.Model

class CharacterAttributes constructor(damage:Int,weight:Int,defense:Int,killPower:Int,speed:Int,recovery:Int,neutral:Int,taunt:Int){
    var damage = damage
    var neutral = neutral
    var recovery = recovery
    var killPower = killPower
    var defense = defense
    var speed = speed
    var weight = weight
    var taunt = taunt

    fun getAttributes(): ArrayList<Int> = ArrayList<Int>(arrayListOf(damage,weight,defense,killPower,speed,recovery,neutral,taunt))
}