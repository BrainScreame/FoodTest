package com.osenov.foodtest.ui.menu.advertising

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.osenov.foodtest.R

class AdvertisingItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view);
        if (position != state.itemCount - 1) {
            outRect.right = space
        }
    }
}