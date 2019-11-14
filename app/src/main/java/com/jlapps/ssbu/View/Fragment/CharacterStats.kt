package com.jlapps.ssbu.View.Fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jlapps.ssbu.Model.Character
import com.jlapps.ssbu.Model.CharacterComparison

import com.jlapps.ssbu.R
import com.jlapps.ssbu.Util.SwipeView.SwipeListener
import com.jlapps.ssbu.Util.SwipeView.SwipeView
import com.jlapps.ssbu.View.Adapter.DefaultRecyclerAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_character_stats.*
import kotlinx.android.synthetic.main.item_character.view.*
import kotlinx.android.synthetic.main.item_character_stat.view.*


class CharacterStats : Fragment(), DefaultRecyclerAdapter.DefaultRecyclerAdapterListener<Character>{

    var characters : ArrayList<Character> = ArrayList<Character>()
    var skins: ArrayList<Int> = ArrayList(arrayListOf(R.drawable.img_villager, R.drawable.img_villager2,R.drawable.img_villager3,R.drawable.img_villager4,R.drawable.img_villager5,R.drawable.img_villager6,R.drawable.img_villager7,R.drawable.img_villager8))
    var skinDex = 0
    val animationDuration:Long = 435

    val TAG = "CharStats"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_character_stats,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)


        characters = initChars()


        rv_character_stats.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL,false)
        rv_character_stats.adapter = DefaultRecyclerAdapter(characters,
            R.layout.item_character_stat,this)

        iv_character_image.setOnClickListener { transitionSkinView() }
    }

    override fun bindItemToView(item: Character, position: Int, viewHolder: RecyclerView.ViewHolder) {
        when(position){
            0 -> {

                viewHolder.itemView.iv_character_stat_icon.setImageResource(R.drawable.ic_damage)
                viewHolder.itemView.tv_character_stat_name.text = "Damage"
                viewHolder.itemView.pb_character_stat_progress.progress = 60

            }
            1 -> {

                viewHolder.itemView.iv_character_stat_icon.setImageResource(R.drawable.ic_weight)
                viewHolder.itemView.tv_character_stat_name.text = "Weight"
                viewHolder.itemView.pb_character_stat_progress.progress = 20

            }
            2 -> {

                viewHolder.itemView.iv_character_stat_icon.setImageResource(R.drawable.ic_defense)
                viewHolder.itemView.tv_character_stat_name.text = "Defense"
                viewHolder.itemView.pb_character_stat_progress.progress = 40

            }
            3 -> {

                viewHolder.itemView.iv_character_stat_icon.setImageResource(R.drawable.ic_kill_power)
                viewHolder.itemView.tv_character_stat_name.text = "Kill Power"
                viewHolder.itemView.pb_character_stat_progress.progress = 40

            }
            4 -> {
                viewHolder.itemView.iv_character_stat_icon.setImageResource(R.drawable.ic_speed)
                viewHolder.itemView.tv_character_stat_name.text = "Speed"
                viewHolder.itemView.pb_character_stat_progress.progress = 60
            }
            5 -> {

                viewHolder.itemView.iv_character_stat_icon.setImageResource(R.drawable.ic_neutral)
                viewHolder.itemView.tv_character_stat_name.text = "Neutral"
                viewHolder.itemView.pb_character_stat_progress.progress = 40

            }
            6 -> {

                viewHolder.itemView.iv_character_stat_icon.setImageResource(R.drawable.ic_taunt)
                viewHolder.itemView.tv_character_stat_name.text = "Taunt"
                viewHolder.itemView.pb_character_stat_progress.progress = 20

            }
            7-> {

                viewHolder.itemView.iv_character_stat_icon.setImageResource(R.drawable.ic_recover)
                viewHolder.itemView.tv_character_stat_name.text = "Recovery"
                viewHolder.itemView.pb_character_stat_progress.progress = 60

            }
        }

//        viewHolder.itemView.tv_character_name.text = item.name
//        viewHolder.itemView.tv_character_tier.text = item.tier + " Tier"
//        viewHolder.itemView.sv_layout.activationDistanceRatio = .1f
//        if (position > DefaultRecyclerAdapter.lastItemPos) {
//            val animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right)
//            viewHolder.itemView.startAnimation(animation)
//            DefaultRecyclerAdapter.lastItemPos = position
//        }
//
//        if(item.isSelected) {
//            viewHolder.itemView.cv_character.strokeColor = resources.getColor(R.color.blue_text,null)
//            viewHolder.itemView.iv_character_select.visibility = View.INVISIBLE
//            viewHolder.itemView.iv_character_deselect.visibility = View.VISIBLE
//        }
//        else {
//            viewHolder.itemView.cv_character.strokeColor = resources.getColor(android.R.color.transparent,null)
//            viewHolder.itemView.iv_character_select.visibility = View.VISIBLE
//            viewHolder.itemView.iv_character_deselect.visibility = View.INVISIBLE
//        }
//
//        if(item.isFavorite)
//            viewHolder.itemView.iv_character_fav_ic.visibility = View.VISIBLE
//        else
//            viewHolder.itemView.iv_character_fav_ic.visibility = View.INVISIBLE
//
//        viewHolder.itemView.sv_layout.swipeGestureListener = object : SwipeListener {
//            override fun onSwipedLeft(swipeActionView: SwipeView): Boolean {
////                Log.e(TAG,"swiped $position")
//
//                if(viewHolder.itemView.iv_character_fav_ic.visibility == View.VISIBLE) {
//
//                    fadeView(false,viewHolder.itemView.iv_character_fav_ic)
//
//                    item.isFavorite = false
//                }
//                else {
//                    fadeView(true,viewHolder.itemView.iv_character_fav_ic)
//                    item.isFavorite = true
//                }
//
//                return true
//            }
//
//            override fun onSwipedRight(swipeActionView: SwipeView): Boolean {
////                Log.e(TAG,"swiped $position")
//
//
//                if(CharacterComparison.selectingChar1) {
//
//                    if(CharacterComparison.char2.id != -1 && CharacterComparison.char2.id == item.id) {
//                        CharacterComparison.selectingChar1 = false
//                        setSelection(viewHolder, item, position, false)
//                    }
//                    else if(CharacterComparison.char1.id != -1 && CharacterComparison.char1.id == item.id)
//                        setSelection(viewHolder,item,position,false)
//                    else
//                        setSelection(viewHolder,item,position,true)
//                }
//                else{
//                    if(CharacterComparison.char1.id != -1 && CharacterComparison.char1.id == item.id) {
//                        CharacterComparison.selectingChar1 = true
//                        setSelection(viewHolder, item, position, false)
//                    }
//                    else if(CharacterComparison.char2.id != -1 && CharacterComparison.char2.id == item.id)
//                        setSelection(viewHolder,item,position,false)
//                    else
//                        setSelection(viewHolder,item,position,true)
//                }
//
//                viewHolder.itemView.sv_layout.animateToOriginalPosition(0)
//
//
//                return false
//            }
//        }
//
//        viewHolder.itemView.sv_layout.setOnClickListener {
//            Log.e(TAG,"clicked $position")
//        }
//
//        Picasso
//            .get()
//            .load(item.image).resize(800,0).centerInside()
//            .into(viewHolder.itemView.iv_character)
//        viewHolder.itemView.iv_character_mark.setImageResource(item.mark)

    }
    override fun recyclerItemClicked(item: Character?, position: Int, viewHolder: RecyclerView.ViewHolder) {
        Log.e(TAG,"clicked $position")
    }
//
//    fun fadeView(fadeIn:Boolean,view:View){
//        lateinit var animation: Animation
//
//        if(fadeIn) {
//            animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
//            animation.setAnimationListener(object : Animation.AnimationListener {
//                override fun onAnimationRepeat(animation: Animation?) {
//                }
//
//                override fun onAnimationEnd(animation: Animation?) {
//                    view.visibility = View.VISIBLE
//                }
//
//                override fun onAnimationStart(animation: Animation?) {
//                }
//            })
//        }else
//        {
//            animation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
//            animation.setAnimationListener(object : Animation.AnimationListener {
//                override fun onAnimationRepeat(animation: Animation?) {
//                }
//
//                override fun onAnimationEnd(animation: Animation?) {
//                    view.visibility = View.GONE
//                }
//
//                override fun onAnimationStart(animation: Animation?) {
//                }
//            })
//        }
//        view.startAnimation(animation)
//    }
    fun initChars():ArrayList<Character>{
        val list = ArrayList<Character>()
        list.add(
            Character(
                0,
                "Villager",
                "animal_crossing",
                'E',
                R.drawable.img_villager,
                R.drawable.doubutsu
            )
        )
        list.add(
            Character(
                1,
                "Terry",
                "animal_crossing",
                'E',
                R.drawable.img_terry,
                R.drawable.garou
            )
        )
        list.add(
            Character(
                2,
                "Toon Link",
                "animal_crossing",
                'E',
                R.drawable.img_toon_link,
                R.drawable.zelda
            )
        )
        list.add(
            Character(
                3,
                "Wario",
                "animal_crossing",
                'E',
                R.drawable.img_wario,
                R.drawable.wario
            )
        )
        list.add(
            Character(
                4,
                "Wii Fit Trainer",
                "animal_crossing",
                'E',
                R.drawable.img_wii_fit_trainer,
                R.drawable.wii_fit
            )
        )
        list.add(
            Character(
                5,
                "Wolf",
                "animal_crossing",
                'E',
                R.drawable.img_wolf,
                R.drawable.starfox
            )
        )
        list.add(
            Character(
                6,
                "Yoshi",
                "animal_crossing",
                'E',
                R.drawable.img_yoshi,
                R.drawable.yoshi
            )
        )
        list.add(
            Character(
                7,
                "Young Link",
                "animal_crossing",
                'E',
                R.drawable.img_young_link,
                R.drawable.zelda
            )
        )
        list.add(
            Character(
                8,
                "Zelda",
                "animal_crossing",
                'E',
                R.drawable.img_zelda,
                R.drawable.zelda
            )
        )
        list.add(
            Character(
                9,
                "Zero Suit Samus",
                "animal_crossing",
                'E',
                R.drawable.img_zero_suit_samus,
                R.drawable.metroid
            )
        )
        return list
    }
//    fun setSelection(viewHolder: RecyclerView.ViewHolder, item: Character, position: Int, selected:Boolean){
//        if(selected){
//            if(CharacterComparison.selectingChar1) {
//                CharacterComparison.char1.isSelected = false
//                if (CharacterComparison.char1.id != -1)
//                    removeBorder(CharacterComparison.char1.id)
//
//                CharacterComparison.char1 = characters.get(position)
//                item.isSelected = true
//
//                viewHolder.itemView.cv_character.strokeColor =
//                    resources.getColor(R.color.blue_text,null)
//
//                CharacterComparison.selectingChar1 = false
//                changeSelectionBackground(viewHolder, true)
//                if(CharacterComparison.char2.id != -1)
//                    activity?.actionBar?.subtitle =  "${CharacterComparison.char1.name} v ${CharacterComparison.char2.name}"
//
//            }
//            else
//            {
//                CharacterComparison.char2.isSelected = false
//                if(CharacterComparison.char2.id != -1)
//                    removeBorder(CharacterComparison.char2.id)
//                CharacterComparison.char2 = characters.get(position)
//                CharacterComparison.selectingChar1 = true
//                item.isSelected = true
//                viewHolder.itemView.cv_character.strokeColor =
//                    resources.getColor(R.color.blue_text,null)
//                changeSelectionBackground(viewHolder,true)
//                if(CharacterComparison.char1.id != -1)
//                    activity?.actionBar?.subtitle =  "${CharacterComparison.char1.name} v ${CharacterComparison.char2.name}"
//
//            }
//
//        }else {
//            if (CharacterComparison.selectingChar1) {
//
//                activity?.actionBar?.subtitle = ""
//                removeBorder(CharacterComparison.char1.id)
//                CharacterComparison.char1 = Character()
//                item.isSelected = false
//                changeSelectionBackground(viewHolder, false)
//                CharacterComparison.selectingChar1 = true
//            }
//            else {
//
//                activity?.actionBar?.subtitle = ""
//                removeBorder(CharacterComparison.char2.id)
//                CharacterComparison.char2 = Character()
//                item.isSelected = false
//                changeSelectionBackground(viewHolder, false)
//                CharacterComparison.selectingChar1 = false
//            }
//        }
//    }
//    fun removeBorder(pos:Int){
//
//        var view = rv_characters.layoutManager?.findViewByPosition(pos)
//        view?.sv_layout?.cv_character?.strokeColor = resources.getColor(android.R.color.transparent,null)
//    }
//    fun changeSelectionBackground(viewHolder: RecyclerView.ViewHolder,selected:Boolean){
//        val r = Runnable {
//            if(selected) {
//                viewHolder.itemView.iv_character_deselect.visibility = View.VISIBLE
//                viewHolder.itemView.iv_character_select.visibility = View.INVISIBLE
//            }
//            else {
//                viewHolder.itemView.iv_character_select.visibility = View.VISIBLE
//                viewHolder.itemView.iv_character_deselect.visibility = View.INVISIBLE
//            }
//        }
//        Handler().postDelayed(r, animationDuration)
//    }
    fun fadeViewSlow(fadeIn:Boolean,view:View, onEnd: () -> Unit){
        lateinit var animation: Animation

        if(fadeIn) {
            animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)

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
            animation = AnimationUtils.loadAnimation(context, R.anim.fade_out)

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
    fun transitionSkinView(){

        fadeViewSlow(false,iv_character_image){

            if(skinDex < skins.size-1) skinDex++ else skinDex = 0

            iv_character_image.setImageResource(skins[skinDex])
            fadeViewSlow(true,iv_character_image){}
        }


    }

}

