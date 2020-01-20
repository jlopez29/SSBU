package com.jlapps.ssbu.view.fragment

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jlapps.ssbu.view.adapter.DefaultRecyclerAdapter
import com.jlapps.ssbu.model.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_character.view.*
import kotlinx.android.synthetic.main.fragment_character_selection.*

import com.jlapps.ssbu.R
import com.jlapps.ssbu.model.CharacterList.characters
import com.jlapps.ssbu.util.AnimUtil.fadeView
import com.jlapps.ssbu.util.FragUtil
import com.jlapps.ssbu.util.SwipeView.SwipeListener
import com.jlapps.ssbu.util.SwipeView.SwipeView
import com.jlapps.ssbu.util.ViewUtils.formatName
import com.jlapps.ssbu.view.widget.CharacterWidgetProvider
import com.jlapps.ssbu.viewmodel.SmashViewModel
import kotlin.random.Random


class CharacterSelection : Fragment(), DefaultRecyclerAdapter.DefaultRecyclerAdapterListener<Character>{

    val TAG = "CharSelect"
    val animationDuration:Long = 435
    lateinit var viewModel: SmashViewModel
    lateinit var adapter:DefaultRecyclerAdapter<Character>
    lateinit var recycler:RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_character_selection,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel= ViewModelProviders.of(activity!!).get(SmashViewModel::class.java)
        (activity as AppCompatActivity).setSupportActionBar(tb_char_selection)

        initLiveData()

        srl_characters.setOnRefreshListener {
            randomizeSkins()
            srl_characters.isRefreshing = false
        }

        recycler = activity!!.findViewById(R.id.rv_characters)

        recycler.layoutManager = LinearLayoutManager(context)
        adapter = DefaultRecyclerAdapter(characters,
                R.layout.item_character,this)
        recycler.adapter = adapter

    }

    fun randomizeSkins(){
        for(char in characters){
            char.skinDex = Random.nextInt(0,8)
        }

        adapter = DefaultRecyclerAdapter(characters,
                R.layout.item_character,this)
        recycler.adapter = adapter
    }

    override fun bindItemToView(item: Character, position: Int, viewHolder: RecyclerView.ViewHolder) {

        viewHolder.itemView.tv_character_name.text = item.name
        viewHolder.itemView.tv_character_tier.text = item.tier + " Tier"
        viewHolder.itemView.sv_layout.activationDistanceRatio = .1f
        if (position > DefaultRecyclerAdapter.lastItemPos) {
            val animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right)
            viewHolder.itemView.startAnimation(animation)
            DefaultRecyclerAdapter.lastItemPos = position
        }

        if(item.isSelected) {
            viewHolder.itemView.cv_character.strokeColor = resources.getColor(R.color.blue_text,null)
            viewHolder.itemView.iv_character_select.visibility = View.INVISIBLE
            viewHolder.itemView.iv_character_deselect.visibility = View.VISIBLE
        }
        else {
            viewHolder.itemView.cv_character.strokeColor = resources.getColor(android.R.color.transparent,null)
            viewHolder.itemView.iv_character_select.visibility = View.VISIBLE
            viewHolder.itemView.iv_character_deselect.visibility = View.INVISIBLE
        }

        if(item.isFavorite())
            viewHolder.itemView.iv_character_fav_ic.visibility = View.VISIBLE
        else
            viewHolder.itemView.iv_character_fav_ic.visibility = View.INVISIBLE

        viewHolder.itemView.sv_layout.swipeGestureListener = object : SwipeListener {
            override fun onSwipedLeft(swipeActionView: SwipeView): Boolean {
//                Log.e(TAG,"swiped $position")

                if(viewHolder.itemView.iv_character_fav_ic.visibility == View.VISIBLE) {

                    fadeView(swipeActionView.context,false,viewHolder.itemView.iv_character_fav_ic)

                    StorageManger.removeFromFavorites(item)
                }
                else {
                    fadeView(swipeActionView.context,true,viewHolder.itemView.iv_character_fav_ic)

                    StorageManger.addToFavorites(item)
                }

                var manager = AppWidgetManager.getInstance(context)
                var ids  =manager.getAppWidgetIds(ComponentName(viewHolder.itemView.context,CharacterWidgetProvider::class.java))
                manager.notifyAppWidgetViewDataChanged(ids,R.id.avf_widget_characters)

                return true
            }

            override fun onSwipedRight(swipeActionView: SwipeView): Boolean {
//                Log.e(TAG,"swiped $position")


                if(CharacterComparison.selectingChar1) {

                    if(!CharacterComparison.char2.id.equals("-1") && CharacterComparison.char2.id == item.id) {
                        CharacterComparison.selectingChar1 = false
                        setSelection(viewHolder, item, position, false)
                    }
                    else if(CharacterComparison.char1.id.equals("-1") && CharacterComparison.char1.id == item.id)
                        setSelection(viewHolder,item,position,false)
                    else
                        setSelection(viewHolder,item,position,true)
                }
                else{
                    if(!CharacterComparison.char1.id.equals("-1") && CharacterComparison.char1.id == item.id) {
                        CharacterComparison.selectingChar1 = true
                        setSelection(viewHolder, item, position, false)
                    }
                    else if(CharacterComparison.char2.id.equals("-1") && CharacterComparison.char2.id == item.id)
                        setSelection(viewHolder,item,position,false)
                    else
                        setSelection(viewHolder,item,position,true)
                }

                viewHolder.itemView.sv_layout.animateToOriginalPosition(0)


                return false
            }
        }

        viewHolder.itemView.sv_layout.setOnClickListener {
            Log.e(TAG,"clicked $position")
            var args = Bundle()
            args.putSerializable("char",item)
            FragUtil.swapFragment(activity as AppCompatActivity,FragUtil.fragmentCharacterStats,true,args)
        }

        var formatted_name = formatName(item.name)

        var url = "https://storage.googleapis.com/ssbu-3d1bf.appspot.com/skins/${formatted_name}/${item.skinDex}"
        Picasso.get()
                .load(url)
                .resize(800, 0)
                .centerInside()
                .into(viewHolder.itemView.iv_character)

//        viewHolder.itemView.iv_character_mark.setImageResource(item.mark)

    }
    override fun recyclerItemClicked(item: Character?, position: Int, viewHolder: RecyclerView.ViewHolder) {
        Log.e(TAG,"clicked $position")
        var args = Bundle()
        args.putSerializable("char",item)
        FragUtil.swapFragment(activity as AppCompatActivity,FragUtil.fragmentCharacterStats,true,args)
    }

    fun setSelection(viewHolder: RecyclerView.ViewHolder, item: Character, position: Int, selected:Boolean){
        if(selected){
//            Log.e(TAG,"Select")
            if(CharacterComparison.selectingChar1) {
//                Log.e(TAG,"Select 1")
                CharacterComparison.char1.isSelected = false
                if (!CharacterComparison.char1.id.equals("-1"))
                    removeBorder(viewHolder.adapterPosition)

                CharacterComparison.char1 = characters.get(position)
                item.isSelected = true

                viewHolder.itemView.cv_character.strokeColor =
                    resources.getColor(R.color.blue_text,null)

                CharacterComparison.selectingChar1 = false
                changeSelectionBackground(viewHolder, true)
                if(!CharacterComparison.char2.id.equals("-1"))
                    triggerCompareView(true)
                
            }
            else
            {
//                Log.e(TAG,"Select 2")
                CharacterComparison.char2.isSelected = false
                if(!CharacterComparison.char2.id.equals("-1"))
                    removeBorder(viewHolder.adapterPosition)
                CharacterComparison.char2 = characters.get(position)
                CharacterComparison.selectingChar1 = true
                item.isSelected = true
                viewHolder.itemView.cv_character.strokeColor =
                    resources.getColor(R.color.blue_text,null)
                changeSelectionBackground(viewHolder,true)
                if(!CharacterComparison.char1.id.equals("-1"))
                    triggerCompareView(false)

            }

        }else {
//            Log.e(TAG,"Deselect")
            if (CharacterComparison.selectingChar1) {
//                Log.e(TAG,"Select 1")
                activity?.actionBar?.subtitle = ""
                removeBorder(viewHolder.adapterPosition)
                CharacterComparison.char1 = Character()
                item.isSelected = false
                changeSelectionBackground(viewHolder, false)
                CharacterComparison.selectingChar1 = true
            }
            else {
//                Log.e(TAG,"Select 2")
                activity?.actionBar?.subtitle = ""
                removeBorder(viewHolder.adapterPosition)
                CharacterComparison.char2 = Character()
                item.isSelected = false
                changeSelectionBackground(viewHolder, false)
                CharacterComparison.selectingChar1 = false
            }
        }
    }

    fun triggerCompareView(isCharOne:Boolean){
        if(activity?.supportFragmentManager?.findFragmentById(R.id.cl_main) is CharacterCompare)
                viewModel.setChar(isCharOne)
        else
            FragUtil.swapFragment(activity as AppCompatActivity,FragUtil.fragmentCharacterComp,false,Bundle())
    }

    fun removeBorder(pos:Int){

        var view = rv_characters.layoutManager?.findViewByPosition(pos)
        if(view == null)
            Log.e(TAG,"view null")
        view?.sv_layout?.cv_character?.strokeColor = resources.getColor(android.R.color.transparent,null)

        view?.iv_character_select?.visibility = View.VISIBLE
        view?.iv_character_deselect?.visibility = View.INVISIBLE
    }

    fun changeSelectionBackground(viewHolder: RecyclerView.ViewHolder,selected:Boolean){
        val r = Runnable {
            if(selected) {
                viewHolder.itemView.iv_character_deselect.visibility = View.VISIBLE
                viewHolder.itemView.iv_character_select.visibility = View.INVISIBLE
            }
            else {
                viewHolder.itemView.iv_character_select.visibility = View.VISIBLE
                viewHolder.itemView.iv_character_deselect.visibility = View.INVISIBLE
            }
        }
        Handler().postDelayed(r, animationDuration)
    }

    fun resetSelections(){
        for(chararacter in characters)
            chararacter.isSelected = false

        CharacterComparison.char1 = Character()
        CharacterComparison.char2 = Character()

        recycler.adapter?.notifyDataSetChanged()
    }

    fun initLiveData(){
        viewModel.charactersUpdated.observe(activity as FragmentActivity, Observer { isUpdated ->
            if(isUpdated != null) {
                if(isUpdated) {
                    recycler.adapter = DefaultRecyclerAdapter(characters,
                            R.layout.item_character,this)
                }
                viewModel.loading.value = false
                viewModel.charactersUpdated.value = null
            }
        })
        viewModel.poppedCompareView.observe(activity as FragmentActivity, Observer { popped ->
            if(popped != null) {

                if(popped)
                    resetSelections()

                viewModel.poppedCompareView.value = null
            }
        })

    }

}

