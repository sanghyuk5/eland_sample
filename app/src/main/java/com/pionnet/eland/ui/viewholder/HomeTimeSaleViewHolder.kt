package com.pionnet.eland.ui.viewholder

import android.os.CountDownTimer
import android.view.View
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.databinding.ViewHomeTimesaleModuleBinding
import com.pionnet.eland.model.Goods
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.utils.FlagUtil
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.priceFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class HomeTimeSaleViewHolder(
    private val binding: ViewHomeTimesaleModuleBinding
) : BaseViewHolder(binding.root) {

    private var currentCountDownTimer: CountDownTimer? = null

    private var linkUrl: String? = null

    init {
        itemView.setOnClickListener {
            linkUrl?.let {
                EventBus.fire(LinkEvent(it))
            }
        }
    }

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.HomeTimeData)?.let {
            initView(it.homeTimeData)
        }
    }

    private fun initView(data: Goods) = with(binding) {
        title.text = data.title
        linkUrl = data.linkUrl

        GlideApp.with(itemView.context).load("https:" + data.imageUrl).into(image)

        timerDash.visibility = View.VISIBLE
        val remainTime = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA).parse(data.time ?: "0").time - Date().time
        currentCountDownTimer = object : CountDownTimer(remainTime, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                var mills = millisUntilFinished
                var hours = TimeUnit.MILLISECONDS.toHours(mills)
                mills -= TimeUnit.HOURS.toMillis(hours)
                val minutes = TimeUnit.MILLISECONDS.toMinutes(mills)
                mills -= TimeUnit.MINUTES.toMillis(minutes)
                val seconds = TimeUnit.MILLISECONDS.toSeconds(mills)

                if (hours.toInt() > 24 * 3) {
                    hours %= 24
                }
                timer.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            }

            override fun onFinish() {
                timerDash.visibility = View.GONE
                timer.text = "Finish ."
            }
        }
        currentCountDownTimer?.start()

        brandName.text = data.brandName
        goodsName.text = data.goodsName
        saleRate.visibility = if (data.saleRate != null && data.saleRate != 0) View.VISIBLE else View.INVISIBLE
        per.visibility = if (data.saleRate != null && data.saleRate != 0) View.VISIBLE else View.INVISIBLE
        saleRate.text = data.saleRate.toString()
        priceBefore.visibility = if (data.marketPrice != null && data.marketPrice != 0) View.VISIBLE else View.GONE
        priceBefore.text = data.marketPrice.toString()
        priceAfter.text = priceFormat(data.salePrice ?: 0)
        flag.flags = FlagUtil.from(data.iconView)
    }

    override fun onDisappeared() {
        super.onDisappeared()
        currentCountDownTimer?.cancel()
    }
}