package com.pionnet.eland.ui.main

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.databinding.ViewItemMainTabmenuBinding
import com.pionnet.eland.model.TabData
import com.pionnet.eland.utils.toPx

class MainTabMenuAdapter : RecyclerView.Adapter<MainTabMenuAdapter.TabMenuViewHolder>() {

    private lateinit var itemClickListener : OnItemClickListener

    companion object {
        var selectedPosition: Int = 0
        var tabs: List<TabData.TabInfo.HeaderIcon> = mutableListOf()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TabMenuViewHolder {
        val itemBinding = ViewItemMainTabmenuBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TabMenuViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: TabMenuViewHolder, position: Int) {
        val tabInfo = tabs[position]
        holder.itemView.setOnClickListener{itemClickListener.onClick(position)}
        holder.bind(tabInfo, position)
    }

    override fun getItemCount(): Int {
        return tabs.size
    }

    class TabMenuViewHolder(private val itemBinding: ViewItemMainTabmenuBinding) : RecyclerView.ViewHolder(itemBinding.root){
        fun bind(item: TabData.TabInfo.HeaderIcon, position: Int) {
            if (position == 0) {
                itemBinding.clItemMainRoot.updateLayoutParams<RecyclerView.LayoutParams> {
                    marginStart = 5.toPx
                }
            }
            if (position == tabs.size - 1) {
                itemBinding.clItemMainRoot.updateLayoutParams<RecyclerView.LayoutParams> {
                    marginEnd = 5.toPx
                }
            }
            if (position == selectedPosition) {
                itemBinding.tvItemMainTabTitle.setTextColor(Color.parseColor("#222222"))
                itemBinding.viewItemMainTabUnderline.visibility = View.VISIBLE
            } else {
                itemBinding.tvItemMainTabTitle.setTextColor(Color.parseColor("#777777"))
                itemBinding.viewItemMainTabUnderline.visibility = View.GONE
            }

            itemBinding.tvItemMainTabTitle.text = item.menu_nm
        }
    }

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun updateData(headerIcon: List<TabData.TabInfo.HeaderIcon>) {
        tabs = headerIcon
        notifyDataSetChanged()
    }

    fun updatePosition(selectPosition: Int) {
        selectedPosition = selectPosition
        notifyDataSetChanged()
    }
}