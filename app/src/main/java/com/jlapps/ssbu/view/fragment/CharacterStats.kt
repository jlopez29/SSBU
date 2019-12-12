package com.jlapps.ssbu.view.fragment

import android.graphics.drawable.AnimatedVectorDrawable
import android.net.Uri
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
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.jlapps.ssbu.R
import com.jlapps.ssbu.model.*
import com.jlapps.ssbu.model.Character.Companion.transitionSkinView
import com.jlapps.ssbu.util.AnimUtil.animateView
import com.jlapps.ssbu.view.adapter.DefaultRecyclerAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_character_stats.*
import kotlinx.android.synthetic.main.item_character_stat.view.*
import kotlinx.android.synthetic.main.item_move.view.*


class CharacterStats : Fragment(), DefaultRecyclerAdapter.DefaultRecyclerAdapterListener<Any>{

    private var moves : ArrayList<Move> = ArrayList()
    lateinit var char:Character

    val TAG = "CharStats"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_character_stats,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        char = arguments?.get("char") as Character

        ctbl_character_stats.title = char.name
        var formatted_name = Character.formatName(char.name)

        Picasso.get()
                .load("https://storage.googleapis.com/ssbu-3d1bf.appspot.com/skins/${formatted_name}/${char.skinDex}")
                .resize(800, 0)
                .centerInside()
                .into(iv_character_image)

        Log.e(TAG,"height ${iv_character_series_icon.height}")

        Picasso.get()
                .load("https://storage.googleapis.com/ssbu-3d1bf.appspot.com/emblems/${char.series.formatString()}.png")
                .resize(0, 75)
                .into(iv_character_series_icon)

        rv_character_stats.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL,false)
        rv_character_stats.adapter = DefaultRecyclerAdapter(char.attributes.getAttributes() as ArrayList<Any>,
            R.layout.item_character_stat,this)

        moves = char.moves

        rv_character_moves.layoutManager = LinearLayoutManager(context)
        rv_character_moves.adapter = DefaultRecyclerAdapter(moves as ArrayList<Any>,
            R.layout.item_move,this)

        iv_character_image.setOnClickListener { transitionSkinView(requireContext(),char,iv_character_image) }

        iv_overview_pill.setImageDrawable(activity?.getDrawable(R.drawable.ic_minus_to_plus))
        iv_moves_pill.setImageDrawable(activity?.getDrawable(R.drawable.ic_minus_to_plus))

        iv_overview_pill.setOnClickListener {

            if(cl_character_stat_overview.visibility == View.GONE) {
                cl_character_stat_overview.visibility = View.VISIBLE
                TransitionManager.beginDelayedTransition(cl_character_stat_container)
                animateView(iv_overview_pill.context, R.anim.slide_down_from_top, cl_character_stat_overview){}
                iv_overview_pill.setImageDrawable(activity?.getDrawable(R.drawable.ic_plus_to_minus))
                (iv_overview_pill.drawable as AnimatedVectorDrawable).start()
            }else {
                TransitionManager.beginDelayedTransition(cl_character_stat_container)
                animateView(iv_overview_pill.context, R.anim.slide_up_to_top, cl_character_stat_overview){cl_character_stat_overview.visibility = View.GONE }
                iv_overview_pill.setImageDrawable(activity?.getDrawable(R.drawable.ic_minus_to_plus))
                (iv_overview_pill.drawable as AnimatedVectorDrawable).start()
            }

        }

        iv_moves_pill.setOnClickListener {

            if(cl_character_moves.visibility == View.GONE) {
                cl_character_moves.visibility = View.VISIBLE
//                TransitionManager.beginDelayedTransition(cl_character_stat_container)
                animateView(iv_moves_pill.context, R.anim.slide_down_from_top, cl_character_moves){rv_character_stats.isNestedScrollingEnabled = false}
                iv_moves_pill.setImageDrawable(activity?.getDrawable(R.drawable.ic_plus_to_minus))
                (iv_moves_pill.drawable as AnimatedVectorDrawable).start()
            }else {
//                TransitionManager.beginDelayedTransition(cl_character_stat_container)
                animateView(iv_moves_pill.context, R.anim.slide_up_to_top, cl_character_moves){cl_character_moves.visibility = View.GONE }
                iv_moves_pill.setImageDrawable(activity?.getDrawable(R.drawable.ic_minus_to_plus))
                (iv_moves_pill.drawable as AnimatedVectorDrawable).start()
            }

        }
    }

    override fun bindItemToView(item: Any, position: Int, viewHolder: RecyclerView.ViewHolder) {
        if(item is Int){
            when(position){
                Attributes.DAMAGE -> {

                    viewHolder.itemView.iv_character_stat_icon.setImageResource(R.drawable.ic_damage)
                    viewHolder.itemView.tv_character_stat_name.text = "Damage"
                    viewHolder.itemView.pb_character_stat_progress.progress = item

                }
                Attributes.WEIGHT -> {

                    viewHolder.itemView.iv_character_stat_icon.setImageResource(R.drawable.ic_weight)
                    viewHolder.itemView.tv_character_stat_name.text = "Weight"
                    viewHolder.itemView.pb_character_stat_progress.progress = item

                }
                Attributes.DEFENSE -> {

                    viewHolder.itemView.iv_character_stat_icon.setImageResource(R.drawable.ic_defense)
                    viewHolder.itemView.tv_character_stat_name.text = "Defense"
                    viewHolder.itemView.pb_character_stat_progress.progress = item

                }
                Attributes.KILL_POWER -> {

                    viewHolder.itemView.iv_character_stat_icon.setImageResource(R.drawable.ic_kill_power)
                    viewHolder.itemView.tv_character_stat_name.text = "Kill Power"
                    viewHolder.itemView.pb_character_stat_progress.progress = item

                }
                Attributes.SPEED -> {
                    viewHolder.itemView.iv_character_stat_icon.setImageResource(R.drawable.ic_speed)
                    viewHolder.itemView.tv_character_stat_name.text = "Speed"
                    viewHolder.itemView.pb_character_stat_progress.progress = item
                }
                Attributes.RECOVERY -> {

                    viewHolder.itemView.iv_character_stat_icon.setImageResource(R.drawable.ic_recover)
                    viewHolder.itemView.tv_character_stat_name.text = "Recovery"
                    viewHolder.itemView.pb_character_stat_progress.progress = item

                }
                Attributes.NEUTRAL -> {

                    viewHolder.itemView.iv_character_stat_icon.setImageResource(R.drawable.ic_neutral)
                    viewHolder.itemView.tv_character_stat_name.text = "Neutral"
                    viewHolder.itemView.pb_character_stat_progress.progress = item

                }
            }
        }else if(item is Move){
            viewHolder.itemView.tv_move_name.text = item.move
            viewHolder.itemView.tv_move_description.text = item.description
            if(item.videoUri != null && item.videoUri != "null" && item.videoUri != "") {

                viewHolder.itemView.vv_move.visibility = View.VISIBLE
                viewHolder.itemView.vv_move.setVideoURI(Uri.parse(item.videoUri))
                viewHolder.itemView.vv_move.setOnPreparedListener {
                    Log.e(TAG,"prepped")
                    viewHolder.itemView.vv_move.setMediaController(MediaController(requireContext()))
                    it.isLooping = true
                    it.start()
                    viewHolder.itemView.vv_move.start()
                }
            }
        }

    }
    override fun recyclerItemClicked(item: Any?, position: Int, viewHolder: RecyclerView.ViewHolder) {
        Log.e(TAG,"clicked $position")
    }

}

