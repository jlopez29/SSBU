package com.jlapps.ssbu.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Attributes constructor(val damage: Int, @SerializedName("kill_power") val killPower: Int, val defense: Int, val speed: Int, val neutral: Int, val weight: Int, val recovery: Int) : Serializable {

    fun getAttributes(): ArrayList<Int> = ArrayList(arrayListOf(damage, weight, defense, killPower, speed, recovery, neutral))

    enum class AttributeType{

        DAMAGE,
        WEIGHT,
        DEFENSE,
        KILL_POWER,
        SPEED,
        RECOVERY,
        NEUTRAL

    }

}