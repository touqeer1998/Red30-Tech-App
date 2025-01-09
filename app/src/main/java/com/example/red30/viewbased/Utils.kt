package com.example.red30.viewbased

import android.content.Context
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.red30.R
import com.google.android.material.snackbar.Snackbar

fun pickBackgroundColorForName(context: Context, name: String?): Int {
    val avatarColors = listOf(
        getColor(context, R.color.md_theme_primaryContainer),
        getColor(context, R.color.md_theme_secondaryContainer),
        getColor(context, R.color.md_theme_tertiaryContainer),
        getColor(context, R.color.md_theme_errorContainer),
    )
    return avatarColors[name.hashCode().mod(avatarColors.size - 1)]
}

fun View.visible() {
    this.visibility = VISIBLE
}

fun View.invisible() {
    this.visibility = INVISIBLE
}

fun getAppLayoutManager(columnCount: Int, context: Context): RecyclerView.LayoutManager {
    return when {
        columnCount <= 1 -> LinearLayoutManager(context)
        else -> GridLayoutManager(context, columnCount)
    }
}

fun makeAppSnackbar(view: View, snackbarMessage: Int): Snackbar {
    val snackbar = Snackbar.make(
        view,
        snackbarMessage,
        Snackbar.LENGTH_SHORT
    )

    (snackbar.view.layoutParams as (CoordinatorLayout.LayoutParams)).apply {
        setMargins(16, 0, 16, 32)
        width = FrameLayout.LayoutParams.WRAP_CONTENT
    }.also {
        snackbar.view.layoutParams = it
    }

    return snackbar
}
