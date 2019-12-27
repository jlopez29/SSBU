package com.jlapps.ssbu.view.fragment

import android.graphics.drawable.AnimatedVectorDrawable
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.jlapps.ssbu.R
import com.jlapps.ssbu.model.Attributes
import com.jlapps.ssbu.model.Character
import com.jlapps.ssbu.model.Character.Companion.transitionSkinView
import com.jlapps.ssbu.model.Move
import com.jlapps.ssbu.model.formatString
import com.jlapps.ssbu.util.AnimUtil.animateView
import com.jlapps.ssbu.view.adapter.DefaultRecyclerAdapter
import com.jlapps.ssbu.view.custom.SelectWheel
import com.jlapps.ssbu.view.custom.SelectWheel.Companion.lastKnownX
import com.jlapps.ssbu.view.custom.SelectWheel.Companion.lastKnownY
import com.jlapps.ssbu.view.custom.SelectWheel.Companion.lastSelected
import com.jlapps.ssbu.view.custom.circularHideView
import com.jlapps.ssbu.view.custom.circularRevealView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_character_stats.*
import kotlinx.android.synthetic.main.item_character_stat.view.*
import kotlinx.android.synthetic.main.item_move.view.*
import kotlinx.android.synthetic.main.layout_character_moves.*
import kotlinx.android.synthetic.main.layout_character_stats.*
import kotlinx.android.synthetic.main.layout_character_traits.*


class CharacterStats : Fragment(), DefaultRecyclerAdapter.DefaultRecyclerAdapterListener<Any>{

    private var moves : ArrayList<Move> = ArrayList()
    lateinit var character:Character
    lateinit var selectWheelContainer:View

    val TAG = "CharacterStats"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_character_stats,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).actionBar?.setHomeButtonEnabled(true)
        (activity as AppCompatActivity).actionBar?.setDisplayShowHomeEnabled(true)
        character = arguments?.get("char") as Character
        ctbl_character_stats.title = character.name

        initCharacterImages()

        initLists()

        initDropdowns()

        initselectWheel()
    }

    fun initselectWheel(){

        var view = LayoutInflater.from(context).inflate(R.layout.custom_select_wheel,null)
        var selectWheelContainer = view.findViewById<View>(R.id.fl_select_wheel)
        var selectWheel = selectWheelContainer.findViewById<SelectWheel>(R.id.select_wheel)

        selectWheel.visibility = View.INVISIBLE
        cl_stat_container.addView(selectWheelContainer)
//        selectWheel.amount = 8

        iv_character_image.setOnTouchListener{_,e ->
            if (e.action == MotionEvent.ACTION_DOWN) {
                lastKnownX = e.x
                lastKnownY = e.y
            } else if (e.action == MotionEvent.ACTION_UP && selectWheel.visibility == View.VISIBLE) {
                circularHideView(selectWheel)
                enableScroll()
                selectWheel.isSelecting = false
                selectWheel.resetSelection()
                character.skinDex = lastSelected-1
                transitionSkinView(requireContext(),character,iv_character_image)
            }
            else if(e.action == MotionEvent.ACTION_MOVE) {
                if(!selectWheel.isSelecting) {
                    selectWheel.isSelecting = true
                }else{
                    var newX = e.x
                    var newY = e.y
                    lastSelected = selectWheel.findAreaTouched(newX,newY)
                }
            }

            false
        }

        iv_character_image.setOnLongClickListener {
            Log.e(TAG,"Long click")

            if(selectWheel.visibility == View.INVISIBLE){
                selectWheelContainer.x = lastKnownX
                selectWheelContainer.y = lastKnownY
                disableScroll()
                circularRevealView(selectWheel)
            }
            false
        }
    }
    private fun enableScroll() {
        val params = ctbl_character_stats.getLayoutParams() as AppBarLayout.LayoutParams
        params.scrollFlags = (
                AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                        or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                )
        ctbl_character_stats.setLayoutParams(params)
        ctbl_character_stats.requestDisallowInterceptTouchEvent(false)
    }

    private fun disableScroll() {
        val params = ctbl_character_stats.getLayoutParams() as AppBarLayout.LayoutParams
        params.scrollFlags = 0
        ctbl_character_stats.setLayoutParams(params)
        ctbl_character_stats.requestDisallowInterceptTouchEvent(true)
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
            if(item.videoUri != "") {

                viewHolder.itemView.vv_move.visibility = View.VISIBLE
                viewHolder.itemView.vv_move.setVideoURI(Uri.parse(item.videoUri))
                viewHolder.itemView.vv_move.setOnPreparedListener {
                    it.isLooping = true
                    it.start()
                }
            }
        }

    }

    override fun recyclerItemClicked(item: Any?, position: Int, viewHolder: RecyclerView.ViewHolder) {
        Log.e(TAG,"clicked $position")
    }

    fun initCharacterImages(){
        var formattedName = Character.formatName(character.name)

        Picasso.get()
                .load("https://storage.googleapis.com/ssbu-3d1bf.appspot.com/skins/${formattedName}/${character.skinDex}")
                .resize(800, 0)
                .centerInside()
                .into(iv_character_image)

        Picasso.get()
                .load("https://storage.googleapis.com/ssbu-3d1bf.appspot.com/emblems/${character.series.formatString()}.png")
                .resize(0, 75)
                .into(iv_character_series_icon)

        tv_character_tier.text = character.tier.toString()
    }

    fun initLists(){
        rv_character_stats.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL,false)
        rv_character_stats.adapter = DefaultRecyclerAdapter(character.attributes.getAttributes() as ArrayList<Any>,
                R.layout.item_character_stat,this)

        var stringBuilder = StringBuilder()

        for (trait in character.traits.advantages)
            stringBuilder.append("- ${trait}\n")

        tv_character_trait_advantages.text = stringBuilder.toString()

        stringBuilder = stringBuilder.clear()

        for (trait in character.traits.disadvantages)
            stringBuilder.append("- ${trait}\n")

        tv_character_trait_disadvantages.text = stringBuilder.toString()

        moves = character.moves.filter { m -> m.videoUri != "" } as ArrayList<Move>
        moves.addAll(character.moves.filter { m -> m.videoUri == "" } as Collection<Move>)

        rv_character_moves.layoutManager = LinearLayoutManager(context)
        rv_character_moves.adapter = DefaultRecyclerAdapter(moves as ArrayList<Any>,
                R.layout.item_move,this)
    }

    fun initDropdowns(){
//        iv_character_image.setOnClickListener { transitionSkinView(requireContext(),character,iv_character_image) }

        iv_overview_pill.setImageDrawable(activity?.getDrawable(R.drawable.ic_minus_to_plus))
        iv_traits_pill.setImageDrawable(activity?.getDrawable(R.drawable.ic_minus_to_plus))
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

        iv_traits_pill.setOnClickListener {

            if(ll_character_traits.visibility == View.GONE) {
                ll_character_traits.visibility = View.VISIBLE
//                TransitionManager.beginDelayedTransition(cl_character_stat_container)
                animateView(iv_traits_pill.context, R.anim.slide_down_from_top, ll_character_traits){}
                iv_traits_pill.setImageDrawable(activity?.getDrawable(R.drawable.ic_plus_to_minus))
                (iv_traits_pill.drawable as AnimatedVectorDrawable).start()
            }else {
//                TransitionManager.beginDelayedTransition(cl_character_stat_container)
                animateView(iv_traits_pill.context, R.anim.slide_up_to_top, ll_character_traits){ll_character_traits.visibility = View.GONE }
                iv_traits_pill.setImageDrawable(activity?.getDrawable(R.drawable.ic_minus_to_plus))
                (iv_traits_pill.drawable as AnimatedVectorDrawable).start()
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


}

