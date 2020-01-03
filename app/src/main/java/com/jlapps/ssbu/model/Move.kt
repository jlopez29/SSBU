package com.jlapps.ssbu.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Move constructor(move:String,description:String,videoUri:String) : Serializable {
    val move = move
    val description = description
    @SerializedName("video_uri")
    val videoUri = videoUri
}