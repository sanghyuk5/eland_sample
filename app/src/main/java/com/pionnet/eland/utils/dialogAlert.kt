package com.pionnet.eland.utils

import android.app.Activity
import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.pionnet.eland.R
import com.pionnet.eland.databinding.DialogCommonBinding

fun dialogAlert(
    context: Context,
    msg: String,
    title: String = "",
    okText: String = context.getString(R.string.dialog_ok),
    okListener: (() -> Unit)? = null,
    useSystemTheme: Boolean = false
) {
    dialogBasic(
        isConfirmType = false,
        context = context,
        msg = msg,
        title = title,
        okText = okText,
        okListener = okListener,
        useSystemTheme = useSystemTheme)
}

fun dialogConfirm(
    context: Context,
    msg: String,
    title: String = "",
    okText: String = context.getString(R.string.dialog_ok),
    cancelText: String = context.getString(R.string.dialog_cancel),
    okListener: (() -> Unit)? = null,
    cancelListener: (() -> Unit)? = null,
    useSystemTheme: Boolean = false
) {
    dialogBasic(
        isConfirmType = true,
        context = context,
        msg = msg,
        title = title,
        okText = okText,
        cancelText = cancelText,
        okListener = okListener,
        cancelListener = cancelListener,
        useSystemTheme = useSystemTheme)
}

private fun dialogBasic(
    isConfirmType: Boolean,
    context: Context,
    msg: String,
    title: String = "",
    okText: String = context.getString(R.string.dialog_ok),
    cancelText: String = context.getString(R.string.dialog_cancel),
    okListener: (() -> Unit)? = null,
    cancelListener: (() -> Unit)? = null,
    useSystemTheme: Boolean = false
) {
    if (context !is Activity || context.isFinishing) {
        return
    }

    val withTitle = title.isNotEmpty()

    if (useSystemTheme) {
        val builder = AlertDialog.Builder(context)

        if (withTitle) {
            builder.setTitle(title)
        }

        builder.setMessage(msg)

        builder.setPositiveButton(okText) { _, _ ->
            if (okListener != null) {
                okListener()
            }
        }

        if (isConfirmType) {
            builder.setNegativeButton(cancelText) { _, _ ->
                if (cancelListener != null) {
                    cancelListener()
                }
            }
        }

        builder.setCancelable(false).create().show()
        return
    }

    val binding = DialogCommonBinding.inflate(context.layoutInflater)

    val dialog = AlertDialog.Builder(context, R.style.DialogBody)
        .setView(binding.root)
        .setCancelable(false)
        .create()

    if (withTitle) {
        binding.contentWithTitle.let {
            it.title.text = title
            it.desc.text = msg
            it.desc.movementMethod = ScrollingMovementMethod()
            it.root.visibility = View.VISIBLE
        }
    } else {
        binding.contentNoTitle.let {
            it.desc.text = msg
            it.desc.movementMethod = ScrollingMovementMethod()
            it.root.visibility = View.VISIBLE
        }
    }

    binding.ok.apply {
        text = okText
        setOnClickListener {
            if (okListener != null) {
                okListener()
            }
            dialog.dismiss()
        }
    }

    if (isConfirmType) {
        binding.cancel.apply {
            text = cancelText
            setOnClickListener {
                if (cancelListener != null) {
                    cancelListener()
                }
                dialog.dismiss()
            }
        }
    } else {
        binding.cancel.visibility = View.GONE
    }

    dialog.show()
}