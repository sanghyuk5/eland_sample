package com.pionnet.eland.views

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.NO_POSITION

/**
 * firstMargin, LastMargin : 각각 처음과 마지막 의 Margin
 * itemMargin : 처음과 끝을 제외하고 적용되는 Margin ( value / 2 값이 양쪽 Margin 으로 적용됨 )
 *
 * UpDate 과정에서 간혹 ItemPosition 을 갖고 오지 못하는 경우 발생 되어
 * NO_POSITION (-1) 일 경우 LayoutPosition 으로 대체
 */
class HorizontalMarginDecoration(
    var itemMargin: Int = 0,
    var firstItemMargin: Int = 0,
    var lastItemMargin: Int = 0
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val itemPosition = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount
        if (itemPosition == NO_POSITION) {
            val itemLayoutPosition = parent.getChildLayoutPosition(view)
            setOffset(itemLayoutPosition, itemCount, outRect)
        } else {
            setOffset(itemPosition, itemCount, outRect)
        }
    }

    private fun setOffset(itemIndex: Int, itemCount: Int, outRect: Rect) {
        if (itemIndex == 0) {    // 첫 번째 아이템
            outRect.set(firstItemMargin, 0, (itemMargin / 2), 0)
        } else if (itemCount > 0 && itemIndex == itemCount - 1) {  // 마지막 아이템
            outRect.set((itemMargin / 2), 0, lastItemMargin, 0)
        } else {  // 나머지
            outRect.set((itemMargin / 2), 0, (itemMargin / 2), 0)
        }
    }
}

class VerticalMarginDecoration(
    var itemMargin: Int = 0,
    var firstItemMargin: Int = 0,
    var lastItemMargin: Int = 0
): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val itemCount = state.itemCount
        var itemPosition = parent.getChildAdapterPosition(view)
        if (itemPosition == NO_POSITION) { itemPosition = parent.getChildLayoutPosition(view) }

        if (itemPosition == 0) {    // 첫 번째 아이템
            outRect.set(0, firstItemMargin, 0, (itemMargin / 2))
        } else if (itemCount > 0 && itemPosition == itemCount - 1) {  // 마지막 아이템
            outRect.set(0, (itemMargin / 2), 0, lastItemMargin)
        } else {  // 나머지
            outRect.set(0, (itemMargin / 2), 0, (itemMargin / 2))
        }
    }
}

class PagerEndOnlyMarginItemDecoration(private val horizontalMargin: Int = 0) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val childCount = state.itemCount

        val firstItem = position == 0
        val lastItem = position == childCount - 1

        with(outRect) {
            left = if (firstItem) 0 else 0
            right = if (lastItem) 0 else horizontalMargin
        }
    }
}

class GridMarginItemDecoration(private val spanCount: Int, private val spacing: Int)
    : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column

        outRect.left = spacing - column * spacing / spanCount
        outRect.right = (column + 1) * spacing / spanCount

        outRect.top = 0
        outRect.bottom = 0
    }
}