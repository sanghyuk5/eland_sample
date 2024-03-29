package com.pionnet.eland.ui.leftmenu

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.orhanobut.logger.Logger
import com.pionnet.eland.BaseActivity
import com.pionnet.eland.databinding.ActivityLeftMenuBinding

class LeftMenuActivity : BaseActivity() {

    private lateinit var binding: ActivityLeftMenuBinding
    private lateinit var viewModel: LeftMenuViewModel

    private val logoutClickCallback = {
        //doLogout()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isSavedInstanceState(savedInstanceState)) {
            Logger.d("isSavedInstanceState is true, return")
            return
        }

        viewModel = ViewModelProvider(this).get(LeftMenuViewModel::class.java)
        binding = ActivityLeftMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        initView()
    }

    private fun initViewModel() = with(viewModel) {
        requestData()

        result.observe(this@LeftMenuActivity) {
            initTopView()
            (binding.list.adapter as? LeftMenuAdapter)?.submitList(it)
        }
    }
    private fun initView() = with(binding) {
        close.setOnClickListener { finish() }
        list.apply {
            adapter = LeftMenuAdapter(logoutClickCallback)
        }
    }

    private fun initTopView() = with(binding) {
        viewModel.topMenuList?.let {
            if (it.isNotEmpty()) {
                basketCount.text = it[2].cartCnt.toString()
                couponCount.text = it[4].cupnCnt.toString()
            }
        }
    }
}