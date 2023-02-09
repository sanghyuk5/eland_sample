package com.pionnet.eland.ui.search.searchBrand

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.pionnet.eland.R
import com.pionnet.eland.databinding.ViewListBinding

class SearchBrandFragment : Fragment(R.layout.view_list) {

    private val binding by viewBinding(ViewListBinding::bind)
    private val viewModel: BrandViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        initView()
    }

    private fun observeData() = with(viewModel) {
        requestData()

        result.observe(viewLifecycleOwner) {
            (binding.list.adapter as? SearchBrandAdapter)?.submitList(it)
        }
    }

    private fun initView() = with(binding) {
        list.apply {
            layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.VERTICAL, false)
            adapter = SearchBrandAdapter(changeLetterCallback)
        }
    }

    private val changeLetterCallback: (String) -> Unit = {
        Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        viewModel.requestKeywordList(it)
    }
}