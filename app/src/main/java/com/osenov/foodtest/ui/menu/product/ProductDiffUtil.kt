package com.osenov.foodtest.ui.menu.product

import androidx.recyclerview.widget.DiffUtil
import com.osenov.foodtest.domain.entity.Product

class ProductDiffUtil(private val oldList: List<Product>, private val newList: List<Product>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].description == newList[newItemPosition].description &&
                oldList[oldItemPosition].price == newList[newItemPosition].price &&
                oldList[oldItemPosition].imageUrl == newList[newItemPosition].imageUrl
    }
}