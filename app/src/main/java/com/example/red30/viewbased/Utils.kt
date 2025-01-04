package com.example.red30.viewbased

import android.content.Context
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat.getColor
import com.example.red30.R
import kotlin.random.Random.Default.nextInt

fun pickBackgroundColorForName(context: Context): Int {
    val avatarColors = listOf(
        getColor(context, R.color.md_theme_primaryContainer),
        getColor(context, R.color.md_theme_secondaryContainer),
        getColor(context, R.color.md_theme_tertiaryContainer),
        getColor(context, R.color.md_theme_errorContainer),
    )
    return avatarColors[nextInt(avatarColors.size - 1)]
}

fun View.visible() {
    this.visibility = VISIBLE
}

fun View.invisible() {
    this.visibility = INVISIBLE
}
