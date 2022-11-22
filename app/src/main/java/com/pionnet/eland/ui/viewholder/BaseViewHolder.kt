package com.pionnet.eland.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger

typealias ItemClickIntCallback = (Int) -> Unit
abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    /**
     * whether is prepared animation on scroll
     */
    var preparedAnimOnScroll = false

    abstract fun onBind(data: Any, position: Int)

    open fun onAppeared() {
        Logger.d("onAppeared $this")
    }

    open fun onDisappeared() {
        Logger.d("onDisappeared $this")
    }

    open fun onRecycled() {
        Logger.d("onRecycled $this")
    }

    open fun onResetPlayView() {
        Logger.d("onResetPlayView $this")
    }

    open fun onStyleNowSetToolTop() {
        Logger.d("onStyleNowSetToolTop $this")
    }

    open fun onLivePlay() {
        Logger.d("onLivePlay >> $this")
    }

    open fun onLiveStop() {
        Logger.d("onLivePlay >> $this")
    }

    protected fun finalize() {
        Logger.d("finalize $this")
    }
}