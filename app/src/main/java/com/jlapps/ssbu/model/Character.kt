package com.jlapps.ssbu.model

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.google.gson.annotations.SerializedName
import com.jlapps.ssbu.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.fragment_character_stats.*
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class Character constructor(@SerializedName("_id") var id: String, var name: String, var series: String, var tier: Char,var faces: Char,var attributes: Attributes, @SerializedName("move_set") var moves: ArrayList<Move>, var traits: Traits) : Serializable{
    constructor():this("-1","Unselected","Default",'Z','r', Attributes(0,0,0,0,0,0,0),ArrayList<Move>(),Traits())

    var skinDex = 0

    var isSelected = false

    fun facesRight():Boolean{
        return (faces == 'r')
    }

    fun isFavorite() = StorageManger.getFavorites().containsKey(this.id)
}

fun String.formatString():String = this.toLowerCase(Locale.getDefault()).replace("-","_").replace(" ","_").replace(".","").replace("&","and").replace("!!","")