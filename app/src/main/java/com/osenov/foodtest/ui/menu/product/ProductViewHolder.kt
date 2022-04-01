package com.osenov.foodtest.ui.menu.product

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.osenov.foodtest.R
import com.osenov.foodtest.databinding.ItemProductBinding
import com.osenov.foodtest.domain.entity.Product

class ProductViewHolder(private val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Product) {
        with(binding) {
            textProductName.text = product.name
            textProductDescription.text = product.description
            if(product.category == "Пицца") {
                buttonProductPrice.text = "от ${product.price} р"
            } else {
                buttonProductPrice.text = "${product.price} р"
            }

            Glide.with(binding.root).load(product.imageUrl).placeholder(R.drawable.menu_placeholder).centerCrop()
                .into(binding.imageProduct)
        }
    }

}