package com.pionnet.eland.ui.search.searchRecently

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.pionnet.eland.R
import com.pionnet.eland.databinding.FragmentSearchRecentlyBinding

class SearchRecentlyFragment : Fragment(R.layout.fragment_search_recently) {

    private val binding by viewBinding(FragmentSearchRecentlyBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}