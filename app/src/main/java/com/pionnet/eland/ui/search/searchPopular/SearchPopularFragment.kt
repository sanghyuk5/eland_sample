package com.pionnet.eland.ui.search.searchPopular

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.pionnet.eland.R
import com.pionnet.eland.databinding.FragmentSearchPopularBinding
import com.pionnet.eland.ui.search.SearchPlanShopAdapter
import com.pionnet.eland.ui.search.SearchPopularAdapter
import com.pionnet.eland.utils.toPx
import com.pionnet.eland.views.GridMarginItemDecoration

class SearchPopularFragment : Fragment(R.layout.fragment_search_popular) {

    private val binding by viewBinding(FragmentSearchPopularBinding::bind)
    private val viewModel: SearchPopularViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        initView()
    }

    private fun observeData() = with(viewModel) {
        requestData()

        popularResult.observe(viewLifecycleOwner) {
            (binding.listPopular.adapter as? SearchPopularAdapter)?.submitList(it)
        }

        planShopResult.observe(viewLifecycleOwner) {
            (binding.listPlanShop.adapter as? SearchPlanShopAdapter)?.submitList(it)
        }
    }

    private fun initView() = with(binding) {
        listPopular.adapter = SearchPopularAdapter()
        listPlanShop.apply {
            if (itemDecorationCount == 0) addItemDecoration(GridMarginItemDecoration(3, 10.toPx))
            adapter = SearchPlanShopAdapter()
        }
    }
}