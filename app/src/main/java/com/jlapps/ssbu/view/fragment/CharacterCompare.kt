package com.jlapps.ssbu.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jlapps.ssbu.model.Character

import com.jlapps.ssbu.R
import com.jlapps.ssbu.model.CharacterComparison
import com.jlapps.ssbu.util.FragUtil
import com.jlapps.ssbu.util.ViewUtils.formatName
import com.jlapps.ssbu.util.ViewUtils.transitionSkinView
import com.jlapps.ssbu.view.adapter.DefaultRecyclerAdapter
import com.jlapps.ssbu.viewmodel.SmashViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_character_compare.*
import kotlinx.android.synthetic.main.fragment_character_compare.view.*
import kotlinx.android.synthetic.main.fragment_character_stats.*
import java.lang.Exception


class CharacterCompare : Fragment(), DefaultRecyclerAdapter.DefaultRecyclerAdapterListener<Any>{
    val TAG = "CharComp"

    lateinit var viewModel:SmashViewModel
    lateinit var rootView:View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_character_compare,container,false)
        return rootView
    }

    override fun onDestroyView() {
        viewModel.poppedCompareView.value = true
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(SmashViewModel::class.java)

        initCharacters()

        initBottomSheet()

        initLiveData()
    }

    override fun bindItemToView(item: Any, position: Int, viewHolder: RecyclerView.ViewHolder) {

    }
    override fun recyclerItemClicked(item: Any?, position: Int, viewHolder: RecyclerView.ViewHolder) {
        Log.e(TAG,"clicked $position")
    }

    fun initCharacters(){
        initCharacterOne()
        initCharacterTwo()
    }

    fun initCharacterOne(){
        TransitionManager.beginDelayedTransition(rootView.cl_char_compare_bottom_sheet)
        rootView.pb_char1_comp_damage_progress.progress = CharacterComparison.char1.attributes.damage
        rootView.pb_char1_comp_defense_progress.progress = CharacterComparison.char1.attributes.defense
        rootView.pb_char1_comp_kill_power_progress.progress = CharacterComparison.char1.attributes.killPower
        rootView.pb_char1_comp_speed_progress.progress = CharacterComparison.char1.attributes.speed
        rootView.pb_char1_comp_weight_progress.progress = CharacterComparison.char1.attributes.weight

        var formatted_name = formatName(CharacterComparison.char1.name)

        var url = "https://storage.googleapis.com/ssbu-3d1bf.appspot.com/skins/${formatted_name}/${CharacterComparison.char1.skinDex}"

        if(!CharacterComparison.char1.facesRight() && rootView.iv_char1.scaleX != -1f)
            rootView.iv_char1.scaleX = -1f
        else if(CharacterComparison.char1.facesRight() && rootView.iv_char1.scaleX == -1f)
            rootView.iv_char1.scaleX = 1f

        Picasso.get()
                .load(url).resize(800, 0).centerInside()
                .into(rootView.iv_char1)
        rootView.iv_char1.setOnClickListener {transitionSkinView(rootView.context,CharacterComparison.char1,rootView.iv_char1)}
        rootView.tv_char1_name.text = CharacterComparison.char1.name
        rootView.tv_char1_series.text = CharacterComparison.char1.series
    }
    fun initCharacterTwo(){
        TransitionManager.beginDelayedTransition(rootView.cl_char_compare_bottom_sheet)
        rootView.pb_char2_comp_damage_progress.progress = CharacterComparison.char2.attributes.damage
        rootView.pb_char2_comp_defense_progress.progress = CharacterComparison.char2.attributes.defense
        rootView.pb_char2_comp_kill_power_progress.progress = CharacterComparison.char2.attributes.killPower
        rootView.pb_char2_comp_speed_progress.progress = CharacterComparison.char2.attributes.speed
        rootView.pb_char2_comp_weight_progress.progress = CharacterComparison.char2.attributes.weight


        var formatted_name = formatName(CharacterComparison.char2.name)

        if(CharacterComparison.char2.facesRight() && rootView.iv_char2.scaleX != -1f)
            rootView.iv_char2.scaleX = -1f

        else if(!CharacterComparison.char2.facesRight() && rootView.iv_char2.scaleX == -1f)
            rootView.iv_char2.scaleX = 1f

        var url = "https://storage.googleapis.com/ssbu-3d1bf.appspot.com/skins/${formatted_name}/${CharacterComparison.char2.skinDex}"
        Picasso.get()
                .load(url).resize(800, 0).centerInside()
                .into(rootView.iv_char2)
        rootView.iv_char2.setOnClickListener {transitionSkinView(rootView.context,CharacterComparison.char2,rootView.iv_char2)}
        rootView.tv_char2_name.text = CharacterComparison.char2.name
        rootView.tv_char2_series.text = CharacterComparison.char2.series
    }

    fun initBottomSheet(){

        var bottomSheet = BottomSheetBehavior.from(cl_char_compare_bottom_sheet)
        bottomSheet.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(p0: View, p1: Float) {

            }

            override fun onStateChanged(p0: View, p1: Int) {
                if(p1 == BottomSheetBehavior.STATE_HIDDEN) {
                    activity?.supportFragmentManager?.popBackStack()
                }
            }
        })
    }

    fun initLiveData(){
        viewModel.isCharacter1.observe(activity as FragmentActivity, Observer { isChar1 ->
            if(isChar1 != null) {

                if(isChar1)
                    initCharacterOne()
                else
                    initCharacterTwo()

                viewModel.isCharacter1.value = null
            }
        })

    }

}

