package com.jlapps.ssbu.view.adapter

import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class DefaultRecyclerAdapter<Any>(
    private val items: ArrayList<Any>,
    private val itemLayout: Int,
    private val listener: DefaultRecyclerAdapterListener<Any>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        var lastItemPos = -1
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class SimpleRecyclerViewHolder(var container: View) :
        RecyclerView.ViewHolder(container) {

        private var curPosition: Int = 0
        private var item: Any? = null

        init {
            val holder = this
            container.setOnClickListener {
                listener.recyclerItemClicked(
                    item,
                    adapterPosition,
                    holder
                )
            }
        }

        fun setItem(item: Any, position: Int) {
            this.item = item
            this.curPosition = position
        }

        fun clearAnim() {
            container.clearAnimation()
        }
    }

    interface DefaultRecyclerAdapterListener<T> {
        fun bindItemToView(item: T, position: Int, viewHolder: RecyclerView.ViewHolder)
        fun recyclerItemClicked(item: T?, position: Int, viewHolder: RecyclerView.ViewHolder)
    }

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val itemView = inflater.inflate(itemLayout, parent, false)
        return SimpleRecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        var item = items[position]
        (viewHolder as DefaultRecyclerAdapter<Any>.SimpleRecyclerViewHolder).setItem(item, position)
        listener.bindItemToView(item, position, viewHolder)
    }


    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size)
    }

    fun restoreItem(model:Any, position: Int) {
        items.add(position, model)
        // notify item added by position
        notifyItemInserted(position)
    }

}
