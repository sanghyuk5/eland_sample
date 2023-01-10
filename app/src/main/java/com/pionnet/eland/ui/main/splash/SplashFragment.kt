package com.pionnet.eland.ui.main.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.pionnet.eland.R
import com.pionnet.eland.databinding.FragmentSplashBinding
import com.pionnet.eland.ui.main.MainViewModel

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val mainViewModel: MainViewModel by activityViewModels()

    private val binding by viewBinding(FragmentSplashBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserve()
    }

    private fun initObserve() = with(mainViewModel) {
        requestData()
    }
}