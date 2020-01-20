package com.jlapps.ssbu.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.jlapps.ssbu.R
import com.jlapps.ssbu.model.Character

class CharacterAdapter(context: Context,characters:ArrayList<Character>) : BaseAdapter() {
    var characterList = characters
    private val mInflator: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return characterList.size
    }

    override fun getItem(position: Int): Any {
        return characterList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View?
        val vh: ListRowHolder
        if (convertView == null) {
            view = this.mInflator.inflate(R.layout.widget_layout, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }


        return view
    }
}
private class ListRowHolder(row: View?) {
    val image: ImageView

    init {
        this.image = row?.findViewById(R.id.iv_widget_character) as ImageView
    }
}