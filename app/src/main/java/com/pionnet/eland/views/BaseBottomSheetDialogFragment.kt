package com.pionnet.eland.views

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.orhanobut.logger.Logger
import com.pionnet.eland.R
import com.pionnet.eland.utils.getScreenHeightToPx

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    @SuppressLint("RestrictedApi")
    final override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        dialog.setOnShowListener { setupBottomSheet(it) }
    }

    /**
     * 상단 offset 높이 (StatusBar 영역까지 포함됨)
     */
    protected open fun offsetHeight(): Int = 0

    /**
     * 디바이스 높이값을 가져와서 offset 높이 만큼 뺀 높이가 BottomSheet 의 높이가 된다.
     *
     * StatusBar 영역까지 포함된 영역으로 부터 [offsetHeight]가 빠짐
     */
    private fun bottomSheetMaxHeight() =
        (getScreenHeightToPx() * maxRatio/**/ - offsetHeight()/**/).toInt()

    /**
     * 최대 높이 고정 여부, 기본값 : 크기가 늘어날 경우 화면 최대로(false)
     *
     */
    protected open val shouldFixedMaxHeight: Boolean = false

    /**
     * 드래그 여부, 기본값 : 드래그 불가능(false)
     */
    protected open val isDraggable: Boolean = false

    /**
     * 외부 클릭시 닫기 여부 설정, 기본값 : 닫지 않음(false)
     */
    protected open val shouldCancellableOnTouchOutside = false

    /**
     * 사용될 Dialog Resource ID
     */
    protected abstract val dialogResourceId: Int

    /**
     * 공통 BottomSheet Theme 적용
     */
    override fun getTheme(): Int {
        return R.style.CustomThemeBottomSheet
    }

    private fun setupBottomSheet(dialogInterface: DialogInterface) {
        val bottomSheetDialog = dialogInterface as BottomSheetDialog
        val bottomSheet = bottomSheetDialog.findViewById<View>(
            com.google.android.material.R.id.design_bottom_sheet
        )
        bottomSheetDialog.setCanceledOnTouchOutside(shouldCancellableOnTouchOutside)
        bottomSheet?.let {
            it.setBackgroundColor(Color.TRANSPARENT)

            val behavior = BottomSheetBehavior.from(it)
            behavior.isDraggable = isDraggable
            if (shouldFixedMaxHeight) {
                val maxHeight = bottomSheetMaxHeight()

                Logger.t("Height").d("measuredHeight=${it.measuredHeight}, maxHeight=$maxHeight")
                if (it.measuredHeight > 0 && it.measuredHeight > maxHeight) {
                    it.layoutParams.height = maxHeight
                    behavior.peekHeight = maxHeight
                }
            }
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                        dismiss()
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        } ?: run {
            dismiss()
        }
    }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(dialogResourceId, container, false)

    companion object {

        private const val maxRatio = 1.0f
    }
}