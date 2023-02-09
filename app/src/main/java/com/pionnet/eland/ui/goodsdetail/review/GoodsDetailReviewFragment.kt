package com.pionnet.eland.ui.goodsdetail.review

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.pionnet.eland.R
import com.pionnet.eland.databinding.ViewListBinding
import com.pionnet.eland.localData.DataManager.ARG_LIST
import com.pionnet.eland.model.BaseParcelable
import com.pionnet.eland.model.GoodsDetailData

class GoodsDetailReviewFragment: Fragment(R.layout.view_list) {

    private val binding by viewBinding(ViewListBinding::bind)
    private val viewModel: GoodsDetailReviewViewModel by viewModels()

    private var data: GoodsDetailData.Data.GoodsReviewInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getParcelable<BaseParcelable>(ARG_LIST)?.value as? GoodsDetailData.Data.GoodsReviewInfo
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        data?.let { viewModel.createModules(it) }
        observeData()
    }

    private fun initView() = with(binding) {
        list.apply {
            layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.VERTICAL, false)
            adapter = GoodsDetailReviewAdapter()
        }
    }

    private fun observeData() = with(viewModel) {
        result.observe(viewLifecycleOwner) {
            (binding.list.adapter as? GoodsDetailReviewAdapter)?.submitList(it)
        }
    }

    companion object {
        fun create(data: GoodsDetailData.Data.GoodsReviewInfo) =
            GoodsDetailReviewFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_LIST, BaseParcelable(data))
                }
            }
    }
}