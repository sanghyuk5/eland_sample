package com.pionnet.eland.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import com.pionnet.eland.ui.viewholder.BaseViewHolder
import kotlin.math.abs

/**
 * 배너등 viewPager 가 있는 탭에서 사용(수평 페이징/스크롤 사용성 개선)
 */
open class BetterRecyclerView @JvmOverloads constructor (
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): RecyclerView(context, attrs, defStyleAttr) {

    private var mScrollPointerId = INVALID_POINTER
    private var mInitialTouchX = 0
    private var mInitialTouchY = 0
    private var mTouchSlop: Int

    private val itemHolders: MutableList<ViewHolder> = mutableListOf()

    companion object {
        private const val INVALID_POINTER = -1
    }

    init {
        val vc = ViewConfiguration.get(getContext())
        mTouchSlop = vc.scaledTouchSlop
    }

    override fun setScrollingTouchSlop(slopConstant: Int) {
        super.setScrollingTouchSlop(slopConstant)
        val vc = ViewConfiguration.get(context)
        when (slopConstant) {
            TOUCH_SLOP_DEFAULT -> mTouchSlop = vc.scaledTouchSlop
            TOUCH_SLOP_PAGING -> mTouchSlop = vc.scaledPagingTouchSlop
        }
    }

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        val action: Int = e.actionMasked
        val actionIndex: Int = e.actionIndex
        return when (action) {
            MotionEvent.ACTION_DOWN -> {
                mScrollPointerId = e.getPointerId(0)
                mInitialTouchX = (e.x + 0.5f).toInt()
                mInitialTouchY = (e.y + 0.5f).toInt()
                super.onInterceptTouchEvent(e)
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                mScrollPointerId = e.getPointerId(actionIndex)
                mInitialTouchX = ((e.getX(actionIndex) + 0.5f).toInt())
                mInitialTouchY = ((e.getY(actionIndex) + 0.5f).toInt())
                super.onInterceptTouchEvent(e)
            }
            MotionEvent.ACTION_MOVE -> {
                val index: Int = e.findPointerIndex(mScrollPointerId)
                if (index < 0) { return false }
                val x = (e.getX(index) + 0.5f).toInt()
                val y = (e.getY(index) + 0.5f).toInt()
                if (scrollState != SCROLL_STATE_DRAGGING) {
                    val dx = x - mInitialTouchX
                    val dy = y - mInitialTouchY
                    val canScrollHorizontally = layoutManager?.canScrollHorizontally() ?: false
                    val canScrollVertically = layoutManager?.canScrollVertically() ?: false
                    var startScroll = false
                    if (canScrollHorizontally && abs(dx) > mTouchSlop && (abs(dx) >= abs(dy) || canScrollVertically)) {
                        startScroll = true
                    }
                    if (canScrollVertically && abs(dy) > mTouchSlop && (abs(dy) >= abs(dx) || canScrollHorizontally)) {
                        startScroll = true
                    }
                    return startScroll && super.onInterceptTouchEvent(e)
                }
                super.onInterceptTouchEvent(e)
            }
            else -> super.onInterceptTouchEvent(e)
        }
    }

    fun onAddViewHolder(viewHolder: BaseViewHolder) {
        itemHolders.add(viewHolder)
    }

    fun onRemoveViewHolder(viewHolder: BaseViewHolder) {
        itemHolders.remove(viewHolder)
    }
}