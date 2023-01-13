package com.pionnet.eland.ui.main

interface ScrollMoveListener {
    fun onScrollUp(top: Int)
    fun onScrollDown(top: Int)
    fun onBottomReached()
}