package com.pionnet.eland.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.pionnet.eland.BaseActivity
import com.pionnet.eland.databinding.ActivitySplashBinding
import com.pionnet.eland.MainActivity
import com.pionnet.eland.localData.DataManager
import com.pionnet.eland.utils.dialogAlert

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[SplashViewModel::class.java]

        viewModel.requestTabsData()
        viewModel.requestHomeData()

        initObserve()
    }

    private fun initObserve() {
        viewModel.isReadyToMain.observe(this) {
            if (viewModel.isReadyToMain.value == true) {
                goNextActivity()
            }
        }

        viewModel.isTabApiCalled.observe(this) {
            if (!it) onFailure()
        }

        viewModel.isHomeApiCalled.observe(this) {
            if (!it) onFailure()
        }
    }

    private fun goNextActivity() {
        val nextIntent = Intent(this, MainActivity::class.java)
        nextIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(nextIntent)
        DataManager.isIntroToMain = true
        finish()
    }

    private fun onFailure() {
        dialogAlert(
            this,
            "오류가 발생하였습니다.",
            okListener = { finish() }
        )
    }
}
