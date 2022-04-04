package com.osenov.foodtest.ui.menu.product

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.osenov.foodtest.R

class ProductItemDecoration(private val context : Context) :
    DividerItemDecoration(context, VERTICAL) {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view);
        if (position != state.itemCount - 1) {
            super.getItemOffsets(outRect, view, parent, state)
        }
    }
}