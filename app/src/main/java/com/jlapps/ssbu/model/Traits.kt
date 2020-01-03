package com.jlapps.ssbu.model

import java.io.Serializable

class Traits constructor(advantages:ArrayList<String>, disadvantages:ArrayList<String>) : Serializable {
    val advantages = advantages
    val disadvantages = disadvantages
    constructor():this(ArrayList<String>(),ArrayList<String>())
}