package com.jlapps.ssbu.util

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.jlapps.ssbu.R
import com.jlapps.ssbu.model.Character
import com.jlapps.ssbu.model.formatString
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

object ViewUtils {
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
    fun fadeViewSlow(ctx: Context, fadeIn:Boolean, view: View, onEnd: () -> Unit){
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
                    view.visibility = View.INVISIBLE
                    onEnd()
                }

                override fun onAnimationStart(animation: Animation?) {
                }
            })
        }
        view.startAnimation(animation)
    }
    fun transitionSkinView(ctx: Context, char: Character, view: View) {

        fadeViewSlow(ctx, false, view) {

            if (char.skinDex < 7) char.skinDex++ else char.skinDex = 0

            var formatted_name = formatName(char.name)

            var url = "https://storage.googleapis.com/ssbu-3d1bf.appspot.com/skins/${formatted_name}/${char.skinDex}"
            Picasso.get()
                    .load(url).resize(800, 0).centerInside()
                    .into(view as ImageView)

            fadeViewSlow(ctx, true, view) {}
        }
    }

    fun getSkin(char: Character, index:Int, target: Target, width:Int){
        var formatted_name = formatName(char.name)
        var url = "https://storage.googleapis.com/ssbu-3d1bf.appspot.com/skins/${formatted_name}/${index}"
        Picasso.get()
                .load(url).resize(width, 0).centerInside()
                .into(target)
    }
    fun transitionToSkinView(ctx: Context, char: Character, view: View, index:Int, fadeout:Boolean, animate:Boolean) {

        if(animate){
            if(fadeout) {
                fadeViewSlow(ctx, false, view) {

                    char.skinDex = index

                    var formatted_name = formatName(char.name)

                    var url = "https://storage.googleapis.com/ssbu-3d1bf.appspot.com/skins/${formatted_name}/${char.skinDex}"
                    Picasso.get()
                            .load(url).resize(800, 0).centerInside()
                            .into(view as ImageView)

                    fadeViewSlow(ctx, true, view) {}
                }
            }else
            {
                char.skinDex = index

                var formatted_name = formatName(char.name)

                var url = "https://storage.googleapis.com/ssbu-3d1bf.appspot.com/skins/${formatted_name}/${char.skinDex}"
                Picasso.get()
                        .load(url).resize(800, 0).centerInside()
                        .into(view as ImageView)

                fadeViewSlow(ctx, true, view) {}
            }
        }else{

            char.skinDex = index

            var formatted_name = formatName(char.name)

            var url = "https://storage.googleapis.com/ssbu-3d1bf.appspot.com/skins/${formatted_name}/${char.skinDex}"
            Picasso.get()
                    .load(url).resize(800, 0).centerInside()
                    .into(view as ImageView)
        }

    }

}