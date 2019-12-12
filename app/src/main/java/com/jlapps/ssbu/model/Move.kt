package com.jlapps.ssbu.model

import com.google.gson.annotations.SerializedName

class Move constructor(move:String,description:String,videoUri:String) {
    val move = move
    val description = description
    @SerializedName("video_uri")
    val videoUri = videoUri
}