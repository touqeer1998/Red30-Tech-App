package com.example.red30.viewbased

import android.content.Context
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat.getColor
import com.example.red30.R

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
