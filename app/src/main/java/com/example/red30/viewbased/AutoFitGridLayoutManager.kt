package com.example.red30.viewbased

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max

class GridAutofitLayoutManager @JvmOverloads constructor(
    context: Context,
    spanCount: Int = 1,
): GridLayoutManager(context, spanCount) {

    var columnWidth: Int = 0
        set(value) {
            if (value > 0 && value != field) {
                field = value
                columnWidthChanged = true
            }
        }

    var columnWidthChanged: Boolean = true

    init {
        columnWidth = spanCount
    }

    override fun onLayoutChildren(
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ) {
        if (columnWidthChanged && columnWidth > 0) {
            val totalSpace: Int = if (orientation == VERTICAL) {
                width - paddingRight - paddingLeft
            } else {
                height - paddingTop - paddingBottom
            }

            setSpanCount(max(1, totalSpace / columnWidth))
            columnWidthChanged = false
        }
        super.onLayoutChildren(recycler, state)
    }
}
