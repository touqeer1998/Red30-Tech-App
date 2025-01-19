package com.example.red30.viewbased

import android.content.Context
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import androidx.window.layout.WindowMetricsCalculator
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

fun View.gone() {
    this.visibility = GONE
}

fun getAppLayoutManager(context: Context): RecyclerView.LayoutManager {
    val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(context)
    val width = metrics.bounds.width()
    val height = metrics.bounds.height()
    val density = context.resources.displayMetrics.density
    val windowSizeClass = WindowSizeClass.compute(width / density, height / density)
    val widthWindowSizeClass = windowSizeClass.windowWidthSizeClass

    return when(widthWindowSizeClass) {
        WindowWidthSizeClass.COMPACT -> LinearLayoutManager(context)
        WindowWidthSizeClass.MEDIUM -> GridLayoutManager(context, 2)
        else -> GridAutofitLayoutManager(context, 800)
    }
}

fun makeAppSnackbar(view: View, snackbarMessage: Int): Snackbar {
    val snackbar = Snackbar.make(
        view,
        snackbarMessage,
        Snackbar.LENGTH_SHORT
    )

    (snackbar.view.layoutParams as (CoordinatorLayout.LayoutParams)).apply {
        width = FrameLayout.LayoutParams.WRAP_CONTENT
    }.also {
        snackbar.view.layoutParams = it
    }

    return snackbar
}
