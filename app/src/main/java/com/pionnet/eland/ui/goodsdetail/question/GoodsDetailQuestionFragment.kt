package com.pionnet.eland.ui.goodsdetail.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.orhanobut.logger.Logger
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.R
import com.pionnet.eland.databinding.FragmentGoodsDetailQuestionBinding
import com.pionnet.eland.databinding.ViewItemGoodsDetailQuestionBinding
import com.pionnet.eland.databinding.ViewItemGoodsDetailReviewImageTextBinding
import com.pionnet.eland.localData.DataManager.ARG_LIST
import com.pionnet.eland.model.BaseParcelable
import com.pionnet.eland.model.GoodsDetailData
import com.pionnet.eland.model.ViewType
import com.pionnet.eland.model.ViewTypeDataSet
import com.pionnet.eland.ui.goodsdetail.info.GoodsDetailInfoAdapter
import com.pionnet.eland.utils.GlideApp

class GoodsDetailQuestionFragment: Fragment(R.layout.fragment_goods_detail_question) {

    private val binding by viewBinding(FragmentGoodsDetailQuestionBinding::bind)
    private val viewModel: GoodsDetailQuestionViewModel by viewModels()

    private var data: GoodsDetailData.Data.GoodsQuestionInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getParcelable<BaseParcelable>(ARG_LIST)?.value as? GoodsDetailData.Data.GoodsQuestionInfo
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        data?.let { viewModel.createModules(it) }
        observeData()
    }

    private fun initView() = with(binding) {
        list.adapter = QuestionAdapter()
    }

    private fun observeData() = with(viewModel) {
        result.observe(viewLifecycleOwner) {
            (binding.list.adapter as? QuestionAdapter)?.submitList(it)
        }
    }

    private inner class QuestionAdapter
        : ListAdapter<GoodsDetailData.Data.GoodsQuestionInfo.Question, QuestionAdapter.ViewHolder>(DiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemGoodsDetailQuestionBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
        }

        private inner class ViewHolder(val binding: ViewItemGoodsDetailQuestionBinding) :
            RecyclerView.ViewHolder(binding.root) {

            private var linkUrl: String? = null

            init {
                itemView.setOnClickListener {
                    linkUrl?.let {
                        EventBus.fire(LinkEvent(it))
                    }
                }
            }

            fun bind(data: GoodsDetailData.Data.GoodsQuestionInfo.Question) = with(binding) {
                answer.visibility = if (data.userID == "판매자") View.VISIBLE else View.GONE
                id.text = data.userID
                date.text = data.date
                data.secret?.let { secret ->
                    if (secret) {
                        comment.visibility = View.GONE
                    } else {
                        comment.visibility = View.VISIBLE
                        comment.text = data.content
                    }
                }
            }
        }
    }

    private inner class DiffCallback : DiffUtil.ItemCallback<GoodsDetailData.Data.GoodsQuestionInfo.Question>() {
        override fun areItemsTheSame(oldItem: GoodsDetailData.Data.GoodsQuestionInfo.Question, newItem: GoodsDetailData.Data.GoodsQuestionInfo.Question): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: GoodsDetailData.Data.GoodsQuestionInfo.Question, newItem: GoodsDetailData.Data.GoodsQuestionInfo.Question): Boolean =
            oldItem == newItem
    }

    companion object {
        fun create(data: GoodsDetailData.Data.GoodsQuestionInfo) =
            GoodsDetailQuestionFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_LIST, BaseParcelable(data))
                }
            }
    }
}