package com.pionnet.eland.ui.goodsdetail.order

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.pionnet.eland.R
import com.pionnet.eland.databinding.ViewListBinding
import com.pionnet.eland.data.DataManager.ARG_LIST
import com.pionnet.eland.data.BaseParcelable
import com.pionnet.eland.data.GoodsDetailData

class GoodsDetailOrderFragment: Fragment(R.layout.view_list) {

    private val binding by viewBinding(ViewListBinding::bind)
    private val viewModel: GoodsDetailOrderViewModel by viewModels()

    private var data: GoodsDetailData.Data.OrderInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getParcelable<BaseParcelable>(ARG_LIST)?.value as? GoodsDetailData.Data.OrderInfo
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
            adapter = GoodsDetailOrderAdapter()
        }
    }

    private fun observeData() = with(viewModel) {
        result.observe(viewLifecycleOwner) {
            (binding.list.adapter as? GoodsDetailOrderAdapter)?.submitList(it)
        }
    }

    companion object {
        fun create(data: GoodsDetailData.Data.OrderInfo) =
            GoodsDetailOrderFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_LIST, BaseParcelable(data))
                }
            }
    }
}