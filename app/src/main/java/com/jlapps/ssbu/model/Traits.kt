package com.jlapps.ssbu.model

class Traits constructor(advantages:ArrayList<String>, disadvantages:ArrayList<String>) {
    val advantages = advantages
    val disadvantages = disadvantages
    constructor():this(ArrayList<String>(),ArrayList<String>())
}