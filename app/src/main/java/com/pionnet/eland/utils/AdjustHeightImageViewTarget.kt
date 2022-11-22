package com.pionnet.eland.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.transition.Transition
import kotlin.math.roundToInt

open class AdjustHeightImageViewTarget @JvmOverloads constructor(
    view: ImageView
): ImageViewTarget<Drawable>(view) {

    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
        super.onResourceReady(resource, transition)
        val width = resource.intrinsicWidth
        val height = resource.intrinsicHeight
        val ratio = height.toFloat() / width.toFloat()
        val newHeight = view.width * ratio
        view.layoutParams.height = newHeight.roundToInt()
        view.requestLayout()
    }

    override fun setResource(resource: Drawable?) {
        setDrawable(resource)
    }
}