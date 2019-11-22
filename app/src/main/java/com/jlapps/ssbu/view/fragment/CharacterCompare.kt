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
import com.jlapps.ssbu.view.adapter.DefaultRecyclerAdapter
import com.jlapps.ssbu.viewmodel.SmashViewModel
import kotlinx.android.synthetic.main.fragment_character_compare.*


class CharacterCompare : Fragment(), DefaultRecyclerAdapter.DefaultRecyclerAdapterListener<Any>{

    private var characters : ArrayList<Character> = ArrayList()

    val TAG = "CharComp"
    lateinit var viewModel:SmashViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e(TAG,"oCV")
        return inflater.inflate(R.layout.fragment_character_compare,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e(TAG,"oVC")

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
        TransitionManager.beginDelayedTransition(cl_char_compare_bottom_sheet)
        pb_char1_comp_damage_progress.progress = CharacterComparison.char1.attributes.damage
        pb_char1_comp_defense_progress.progress = CharacterComparison.char1.attributes.defense
        pb_char1_comp_kill_power_progress.progress = CharacterComparison.char1.attributes.killPower
        pb_char1_comp_speed_progress.progress = CharacterComparison.char1.attributes.speed
        pb_char1_comp_weight_progress.progress = CharacterComparison.char1.attributes.weight

        iv_char1.setImageResource(CharacterComparison.char1.image)
        tv_char1_name.text = CharacterComparison.char1.name
        tv_char1_series.text = CharacterComparison.char1.series
    }
    fun initCharacterTwo(){
        TransitionManager.beginDelayedTransition(cl_char_compare_bottom_sheet)
        pb_char2_comp_damage_progress.progress = CharacterComparison.char2.attributes.damage
        pb_char2_comp_defense_progress.progress = CharacterComparison.char2.attributes.defense
        pb_char2_comp_kill_power_progress.progress = CharacterComparison.char2.attributes.killPower
        pb_char2_comp_speed_progress.progress = CharacterComparison.char2.attributes.speed
        pb_char2_comp_weight_progress.progress = CharacterComparison.char2.attributes.weight


        iv_char2.setImageResource(CharacterComparison.char2.image)
        tv_char2_name.text = CharacterComparison.char2.name
        tv_char2_series.text = CharacterComparison.char2.series
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
            }
        })
    }

}

