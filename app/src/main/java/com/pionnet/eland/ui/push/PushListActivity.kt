package com.pionnet.eland.ui.push

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import com.pionnet.eland.BaseActivity
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.data.SearchPlanShopData
import com.pionnet.eland.data.room.Push
import com.pionnet.eland.databinding.ActivityPushListBinding
import com.pionnet.eland.databinding.ViewItemPushBinding
import com.pionnet.eland.databinding.ViewItemSearchPlanShopBinding
import com.pionnet.eland.ui.leftmenu.LeftMenuAdapter
import com.pionnet.eland.utils.GlideApp

class PushListActivity : BaseActivity() {

    private lateinit var binding: ActivityPushListBinding
    private lateinit var viewModel: PushListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isSavedInstanceState(savedInstanceState)) {
            Logger.d("isSavedInstanceState is true, return")
            return
        }

        viewModel = ViewModelProvider(this).get(PushListViewModel::class.java)
        binding = ActivityPushListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initObserve()
    }

    private fun initView() = with(binding) {
        list.adapter = PushListAdapter()
    }

    private fun initObserve() = with(viewModel) {
        result.observe(this@PushListActivity) {
            (binding.list.adapter as? PushListAdapter)?.submitList(it.reversed())
        }
    }

    private class PushListAdapter
        : ListAdapter<Push, PushListAdapter.ViewHolder>(DiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemPushBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
        }

        private inner class ViewHolder(val binding: ViewItemPushBinding)
            : RecyclerView.ViewHolder(binding.root) {

            private var linkUrl: String? = null

            init {
                itemView.setOnClickListener {
                    linkUrl?.let {
                        EventBus.fire(LinkEvent(it))
                    }
                }
            }

            fun bind(data: Push) = with(binding) {
                linkUrl = data.link
                title.text = data.title
                content.text = data.content
                date.text = data.date
            }
        }

        class DiffCallback : DiffUtil.ItemCallback<Push>() {
            override fun areItemsTheSame(oldItem: Push, newItem: Push): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Push, newItem: Push): Boolean =
                oldItem == newItem
        }
    }
}