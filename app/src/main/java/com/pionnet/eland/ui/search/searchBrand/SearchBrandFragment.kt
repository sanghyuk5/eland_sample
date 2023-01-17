package com.pionnet.eland.ui.search.searchBrand

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.pionnet.eland.R
import com.pionnet.eland.databinding.FragmentSearchBrandBinding

class SearchBrandFragment : Fragment(R.layout.fragment_search_brand) {

    private val binding by viewBinding(FragmentSearchBrandBinding::bind)
    private val viewModel: BrandViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        initView()
    }

    private fun observeData() = with(viewModel) {
        requestData()

        result.observe(viewLifecycleOwner) {
            (binding.rvBrand.adapter as? SearchBrandAdapter)?.submitList(it)
        }
    }

    private fun initView() = with(binding) {
        rvBrand.adapter = SearchBrandAdapter(changeLetterCallback)
    }

    private val changeLetterCallback: (String) -> Unit = {
        Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        viewModel.requestKeywordList(it)
    }
}