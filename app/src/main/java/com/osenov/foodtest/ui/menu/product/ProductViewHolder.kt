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

            //can’t do this, but due to the lack of a good api, I left it like this
            if (product.category ==  root.resources.getString(R.string.product_category_for_price)) {
                buttonProductPrice.text =
                    root.resources.getString(R.string.product_price_from, product.price)
            } else {
                buttonProductPrice.text =
                    root.resources.getString(R.string.product_price, product.price)
            }

            Glide.with(binding.root).load(product.imageUrl).placeholder(R.drawable.menu_placeholder)
                .centerCrop()
                .into(binding.imageProduct)
        }
    }

}