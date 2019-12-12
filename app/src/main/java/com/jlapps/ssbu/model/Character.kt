package com.jlapps.ssbu.model

import android.content.Context
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.google.gson.annotations.SerializedName
import com.jlapps.ssbu.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_character_stats.*
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class Character constructor(@SerializedName("_id") var id: String, var name: String, var series: String, var tier: Char, image:Int, mark:Int, skins:ArrayList<String>, var attributes: Attributes, @SerializedName("move_set") var moves: ArrayList<Move>, var traits: Traits) : Serializable{
    constructor():this("-1","Unselected","Default",'Z',-1,-1,ArrayList<String>(), Attributes(0,0,0,0,0,0,0),ArrayList<Move>(),Traits())

    var mark = mark

    var skinDex = 0

    var isFavorite = false
    var isSelected = false

    companion object{
        fun formatName(name:String):String{
            var formatted_name = ""

            if(name == "Pokémon Trainer (Charizard)" || name == "Pokémon Trainer (Ivysaur)" || name == "Pokémon Trainer (Squirtle)")
                formatted_name = "pokemon_trainer"
            else if(name == "Mii Gunner" || name == "Mii Brawler" || name == "Mii Swordfighter")
                formatted_name = "mii_fighter"

            if(formatted_name == "")
                formatted_name = name.formatString()

            return formatted_name
        }
        fun fadeViewSlow(ctx:Context,fadeIn:Boolean, view: View, onEnd: () -> Unit){
            lateinit var animation: Animation

            if(fadeIn) {
                animation = AnimationUtils.loadAnimation(ctx, R.anim.fade_in)

//            animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right)
                animation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(animation: Animation?) {
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        view.visibility = View.VISIBLE
                        onEnd()
                    }

                    override fun onAnimationStart(animation: Animation?) {
                    }
                })
            }else
            {
                animation = AnimationUtils.loadAnimation(ctx, R.anim.fade_out)

//            animation = AnimationUtils.loadAnimation(context, R.anim.slide_out_left)
                animation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(animation: Animation?) {
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        view.visibility = View.GONE
                        onEnd()
                    }

                    override fun onAnimationStart(animation: Animation?) {
                    }
                })
            }
            view.startAnimation(animation)
        }
        fun transitionSkinView(ctx:Context,char:Character,view: View) {

            fadeViewSlow(ctx, false, view) {

                if (char.skinDex < 7) char.skinDex++ else char.skinDex = 0

                var formatted_name = formatName(char.name)

                var url = "https://storage.googleapis.com/ssbu-3d1bf.appspot.com/skins/${formatted_name}/${char.skinDex}"
                Log.e("Char", "index ${char.skinDex}")
                Picasso.get()
                        .load(url).resize(800, 0).centerInside()
                        .into(view as ImageView)

                fadeViewSlow(ctx, true, view) {}
            }
        }
    }
}

fun String.formatString():String = this.toLowerCase(Locale.getDefault()).replace("-","_").replace(" ","_").replace(".","").replace("&","and").replace("!!","")