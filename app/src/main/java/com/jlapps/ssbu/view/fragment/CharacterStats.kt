package com.jlapps.ssbu.view.fragment

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.transition.TransitionManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jlapps.ssbu.model.Character

import com.jlapps.ssbu.R
import com.jlapps.ssbu.model.CharacterList
import com.jlapps.ssbu.util.AnimUtil.animateView
import com.jlapps.ssbu.view.adapter.DefaultRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_character_stats.*
import kotlinx.android.synthetic.main.item_character_counters.view.*
import kotlinx.android.synthetic.main.item_character_stat.view.*


class CharacterStats : Fragment(), DefaultRecyclerAdapter.DefaultRecyclerAdapterListener<Any>{

    private var characters : ArrayList<Character> = ArrayList()
    private var skins: ArrayList<Int> = ArrayList(arrayListOf(R.drawable.img_villager, R.drawable.img_villager2,R.drawable.img_villager3,R.drawable.img_villager4,R.drawable.img_villager5,R.drawable.img_villager6,R.drawable.img_villager7,R.drawable.img_villager8))
    private var skinDex = 0

    private var overviewShowing = true

    val TAG = "CharStats"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_character_stats,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        var char = arguments?.get("char") as Character

        ctbl_character_stats.title = char.name
        iv_character_image.setImageResource(char.image)

        rv_character_stats.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL,false)
        rv_character_stats.adapter = DefaultRecyclerAdapter(char.attributes.getAttributes() as ArrayList<Any>,
            R.layout.item_character_stat,this)

        characters = arrayListOf(CharacterList.getChar("Wario")!!,CharacterList.getChar("Wario")!!,CharacterList.getChar("Wario")!!,CharacterList.getChar("Wario")!!)

        rv_character_counters.layoutManager = LinearLayoutManager(context)
        rv_character_counters.adapter = DefaultRecyclerAdapter(characters as ArrayList<Any>,
            R.layout.item_character_counters,this)

        iv_character_image.setOnClickListener { transitionSkinView() }

        iv_overview_pill.setImageDrawable(activity?.getDrawable(R.drawable.ic_minus_to_plus))
        iv_counters_pill.setImageDrawable(activity?.getDrawable(R.drawable.ic_minus_to_plus))

//        cl_character_stat_container.layoutTransition.setAnimateParentHierarchy(false)


        //TODO Fix horizontal scrolling after collapse

        iv_overview_pill.setOnClickListener {

            if(cl_character_stat_overview.visibility == View.GONE) {
                cl_character_stat_overview.visibility = View.VISIBLE
//                TransitionManager.beginDelayedTransition(cl_character_stat_container)
                animateView(iv_overview_pill.context, R.anim.slide_down_from_top, cl_character_stat_overview){rv_character_stats.isNestedScrollingEnabled = false}
                iv_overview_pill.setImageDrawable(activity?.getDrawable(R.drawable.ic_plus_to_minus))
                (iv_overview_pill.drawable as AnimatedVectorDrawable).start()
            }else {
//                TransitionManager.beginDelayedTransition(cl_character_stat_container)
                animateView(iv_overview_pill.context, R.anim.slide_up_to_top, cl_character_stat_overview){cl_character_stat_overview.visibility = View.GONE }
                iv_overview_pill.setImageDrawable(activity?.getDrawable(R.drawable.ic_minus_to_plus))
                (iv_overview_pill.drawable as AnimatedVectorDrawable).start()
            }

        }

        iv_counters_pill.setOnClickListener {

            if(cl_character_counters.visibility == View.GONE) {
                cl_character_counters.visibility = View.VISIBLE
//                TransitionManager.beginDelayedTransition(cl_character_stat_container)
                animateView(iv_counters_pill.context, R.anim.slide_down_from_top, cl_character_counters){rv_character_stats.isNestedScrollingEnabled = false}
                iv_counters_pill.setImageDrawable(activity?.getDrawable(R.drawable.ic_plus_to_minus))
                (iv_counters_pill.drawable as AnimatedVectorDrawable).start()
            }else {
//                TransitionManager.beginDelayedTransition(cl_character_stat_container)
                animateView(iv_counters_pill.context, R.anim.slide_up_to_top, cl_character_counters){cl_character_counters.visibility = View.GONE }
                iv_counters_pill.setImageDrawable(activity?.getDrawable(R.drawable.ic_minus_to_plus))
                (iv_counters_pill.drawable as AnimatedVectorDrawable).start()
            }

        }
    }

    override fun bindItemToView(item: Any, position: Int, viewHolder: RecyclerView.ViewHolder) {
        if(item is Int){
            when(position){
                0 -> {

                    viewHolder.itemView.iv_character_stat_icon.setImageResource(R.drawable.ic_damage)
                    viewHolder.itemView.tv_character_stat_name.text = "Damage"
                    viewHolder.itemView.pb_character_stat_progress.progress = item

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

                    viewHolder.itemView.iv_character_stat_icon.setImageResource(R.drawable.ic_recover)
                    viewHolder.itemView.tv_character_stat_name.text = "Recovery"
                    viewHolder.itemView.pb_character_stat_progress.progress = 60

                }
                6 -> {

                    viewHolder.itemView.iv_character_stat_icon.setImageResource(R.drawable.ic_neutral)
                    viewHolder.itemView.tv_character_stat_name.text = "Neutral"
                    viewHolder.itemView.pb_character_stat_progress.progress = 40

                }
                7 -> {

                    viewHolder.itemView.iv_character_stat_icon.setImageResource(R.drawable.ic_taunt)
                    viewHolder.itemView.tv_character_stat_name.text = "Taunt"
                    viewHolder.itemView.pb_character_stat_progress.progress = 20

                }
            }
        }

        else if(item is Character){

            viewHolder.itemView.pb_character_counter_damage_progress.progress = 60
            viewHolder.itemView.pb_character_counter_weight_progress.progress = 40
            viewHolder.itemView.pb_character_counter_speed_progress.progress = 20
            viewHolder.itemView.pb_character_counter_defense_progress.progress = 100

        }

    }
    override fun recyclerItemClicked(item: Any?, position: Int, viewHolder: RecyclerView.ViewHolder) {
        Log.e(TAG,"clicked $position")
    }

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

