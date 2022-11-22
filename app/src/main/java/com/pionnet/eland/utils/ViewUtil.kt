package com.pionnet.eland.utils

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.res.Resources
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.view.Window
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.LinearLayout
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

inline val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

inline val Float.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * dp 값을 글꼴크기 설정에 상관없이 dp와 동일한 사이즈의 sp 로 변환
 */
inline val Float.dpToScaleAwareSp: Float
    get() = (this * (Resources.getSystem().displayMetrics.density / Resources.getSystem().displayMetrics.scaledDensity))

inline val Float.toDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

fun View.setWidth(value: Int) {
    val lp = layoutParams
    lp?.let {
        lp.width = value
        layoutParams = lp
    }
}

fun View.setHeight(value: Int) {
    val lp = layoutParams
    lp?.let {
        lp.height = value
        layoutParams = lp
    }
}

fun View.resize(width: Int, height: Int) {
    val lp = layoutParams
    lp?.let {
        lp.width = width
        lp.height = height
        layoutParams = lp
    }
}

fun View.setShow(isShow: Boolean) {
    visibility = if (isShow) View.VISIBLE else View.GONE
}

const val STANDARD_WIDTH_DP = 360f

fun getScreenWidthToPx(): Int = Resources.getSystem().displayMetrics.widthPixels
fun getScreenHeightToPx(): Int = Resources.getSystem().displayMetrics.heightPixels

fun getSizeRelativeScreenWidth(dp: Float): Float
        = getScreenWidthToPx().toFloat() * dp.toPx / STANDARD_WIDTH_DP.toPx

fun View.setResponsiveHeight(value: Int) {
    setHeight(getSizeRelativeScreenWidth(value.toFloat()).toInt())
}

fun View.setResponsiveWidth(value: Int) {
    setWidth(getSizeRelativeScreenWidth(value.toFloat()).toInt())
}

fun View.setResponsiveSize(width: Int, height: Int) {
    resize(getSizeRelativeScreenWidth(width.toFloat()).toInt(),
        getSizeRelativeScreenWidth(height.toFloat()).toInt())
}

fun getSpannedColorText(origin: String, changed: String, color: Int, bold: Boolean = false): Spannable {
    val sb = SpannableStringBuilder(origin)
    val pair = getChangedIndex(origin, changed)

    sb.setSpan(
        ForegroundColorSpan(color),
        pair.first,
        pair.second,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    if (bold) {
        sb.setSpan(
            StyleSpan(Typeface.BOLD),
            pair.first,
            pair.second,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return sb
}

fun getSpannedColorSizeText(origin: String, changed: String, color: Int, sizeDp: Int, bold: Boolean = false): Spannable {
    val sb = SpannableStringBuilder(origin)
    val pair = getChangedIndex(origin, changed)

    sb.setSpan(
        ForegroundColorSpan(color),
        pair.first,
        pair.second,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    sb.setSpan(
        AbsoluteSizeSpan(sizeDp, true),
        pair.first,
        pair.second,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    if (bold) {
        sb.setSpan(
            StyleSpan(Typeface.BOLD),
            pair.first,
            pair.second,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return sb
}

fun getSpannedBoldText(origin: String, changed: String) :Spannable {
    val sb = SpannableStringBuilder(origin)
    val pair = getChangedIndex(origin, changed)

    sb.setSpan(
        StyleSpan(Typeface.BOLD),
        pair.first,
        pair.second,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return sb
}

fun getSpannedSizeText(
    origin: String,
    changed: String,
    sizeDp: Int,
    bold: Boolean = false
): Spannable {

    val sb = SpannableStringBuilder(origin)
    val pair = getChangedIndex(origin, changed)

    sb.setSpan(
        AbsoluteSizeSpan(sizeDp, true),
        pair.first,
        pair.second,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    if (bold) {
        sb.setSpan(
            StyleSpan(Typeface.BOLD),
            pair.first,
            pair.second,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return sb
}

fun getChangedIndex(origin: String, changed: String): Pair<Int, Int> {
    if (origin.isEmpty()) return Pair(0, 0)

    var start = origin.indexOf(changed, 0)
    var end = start + changed.length
    if (start == -1) {
        start = 0
        end = origin.length
    }
    return Pair(start, end)
}

fun getSpannedUnderLineBoldText(origin: String, changed: String, isBold: Boolean): Spannable {
    val sb = SpannableStringBuilder(origin)
    val pair = getChangedIndex(origin, changed)
    sb.setSpan(UnderlineSpan(), pair.first, pair.second, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    sb.setSpan(
        StyleSpan(if (isBold) Typeface.BOLD else Typeface.NORMAL),
        pair.first,
        pair.second,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return sb
}

fun convertPattern(inputPattern: String, outputPattern: String, input: String): String {
    var output = ""

    try {
        val date = SimpleDateFormat(inputPattern, Locale.getDefault()).parse(input)
        output = SimpleDateFormat(outputPattern, Locale.getDefault()).format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    } finally {
        return output
    }
}

fun View.expand(endAnimCallback: () -> Unit) {
    measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    val targetHeight: Int = measuredHeight

    layoutParams.height = 1
    visibility = View.VISIBLE

    val a: Animation = object : Animation(){
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            layoutParams.height = if (interpolatedTime == 1f)
                LinearLayout.LayoutParams.WRAP_CONTENT
            else
                (targetHeight * interpolatedTime).toInt()
            requestLayout()

        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    a.duration = (targetHeight / context.resources.displayMetrics.density).toInt().toLong()
    a.setAnimationListener(object: Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) {}
        override fun onAnimationEnd(animation: Animation?) {
            endAnimCallback.invoke()
        }
        override fun onAnimationRepeat(animation: Animation?) {}
    })
    startAnimation(a)
}

fun View.collapse() {
    val initialHeight : Int = this.measuredHeight
    val a : Animation = object : Animation(){
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            if (interpolatedTime == 1f){
                visibility = View.GONE
            }else{
                layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                requestLayout()
            }
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    a.duration = (initialHeight / context.resources.displayMetrics.density).toInt().toLong()
    startAnimation(a)
}

/**
 * Call listener when the current page is changed.
 * Use this with LinearLayoutManager and PagerSnapHelper.
 *
 * Whether the listener is called at first might depend on the version of RecyclerView.
 */
fun RecyclerView.addOnPageChangedListener(listener: (pos: Int) -> Unit) {

    val layoutManager = this.layoutManager as LinearLayoutManager
    var lastPos = -1

    this.addOnScrollListener(object: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val pos = layoutManager.findFirstCompletelyVisibleItemPosition()
            if (pos != -1 && pos != lastPos) {
                lastPos = pos
                listener.invoke(pos)
            }
        }

    })
}

fun hideSystemUI(window: Window, view: View) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    WindowInsetsControllerCompat(window, view).let { controller ->
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

fun showSystemUI(window: Window, view: View) {
    WindowCompat.setDecorFitsSystemWindows(window, true)
    WindowInsetsControllerCompat(window, view).show(WindowInsetsCompat.Type.systemBars())
}

fun ViewPager2.setCurrentItemWithDuration(
    item: Int,
    duration: Long,
    interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
    pagePxWidth: Int = width // Default value taken from getWidth() from ViewPager2 view
) {
    val pxToDrag: Int = pagePxWidth * (item - currentItem)
    val animator = ValueAnimator.ofInt(0, pxToDrag)
    var previousValue = 0
    animator.addUpdateListener { valueAnimator ->
        val currentValue = valueAnimator.animatedValue as Int
        val currentPxToDrag = (currentValue - previousValue).toFloat()
        fakeDragBy(-currentPxToDrag)
        previousValue = currentValue
    }
    animator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) { beginFakeDrag() }
        override fun onAnimationEnd(animation: Animator?) { endFakeDrag() }
        override fun onAnimationCancel(animation: Animator?) { /* Ignored */ }
        override fun onAnimationRepeat(animation: Animator?) { /* Ignored */ }
    })
    animator.interpolator = interpolator
    animator.duration = duration
    animator.start()
}