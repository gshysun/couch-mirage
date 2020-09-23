package com.huji.couchmirage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.layout_department_single_list_item.view.*
import kotlinx.android.synthetic.main.layout_department_single_list_item.view.department_name
import kotlinx.android.synthetic.main.layout_single_list_item.view.*

class ItemRecyclerAdapter (private var listener: OnItemClickListen
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
     var items: MutableList<SingleItem> = ArrayList()
    fun getItemList(): List<SingleItem> {
        return items
    }
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_single_list_item, parent, false)

        val viewHol = ItemViewHolder(textView)
        textView.setOnClickListener { v ->
            listener.onItemClick(
                v,
                viewHol.layoutPosition
            )
        }

        return viewHol
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> {
                holder.bind(items[position])
            }
        }
    }



    fun setItemList(itemList: MutableList<SingleItem>) {
        items = itemList
    }

    class ItemViewHolder
    constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val itemImg: ImageView = itemView.item_img
        private val itemName: TextView = itemView.item_name

        fun bind(item: SingleItem) {
            itemName.text = item.model
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context).applyDefaultRequestOptions(requestOptions)
                .load(item.images[0])
                .into(itemImg)

        }


    }

    class DepartmentListGridRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private var items: List<SingleItem> = ArrayList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return ItemViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_single_list_item, parent, false)
            )
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val itemViewHolder = holder as ItemViewHolder
            itemViewHolder.bind(items[position])
        }

        override fun getItemCount(): Int {
            return items.size
        }
    }


}

interface OnItemClickListen {
    fun onItemClick(view: View, position: Int)
}

