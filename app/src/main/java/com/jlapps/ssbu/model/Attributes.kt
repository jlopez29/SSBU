package com.jlapps.ssbu.model

import com.google.gson.annotations.SerializedName

class Attributes constructor(val damage: Int, @SerializedName("kill_power") val killPower: Int, val defense: Int, val speed: Int, val neutral: Int, val weight: Int, val recovery: Int) {

    fun getAttributes(): ArrayList<Int> = ArrayList(arrayListOf(damage, weight, defense, killPower, speed, recovery, neutral))

    companion object{
        val DAMAGE = 0
        val WEIGHT = 1
        val DEFENSE = 2
        val KILL_POWER = 3
        val SPEED = 4
        val RECOVERY = 5
        val NEUTRAL = 6
    }

}