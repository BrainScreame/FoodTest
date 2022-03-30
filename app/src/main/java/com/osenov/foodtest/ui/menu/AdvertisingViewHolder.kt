package com.osenov.foodtest.ui.menu

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.osenov.foodtest.R
import com.osenov.foodtest.databinding.ItemAdvertisingImageBinding
import com.osenov.foodtest.domain.entity.Advertising

class AdvertisingViewHolder(private val binding: ItemAdvertisingImageBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(advertising: Advertising) {
        Glide.with(binding.root).load(advertising.imageUrl).placeholder(R.drawable.menu_placeholder)
            .into(binding.imageAdvertising)
    }
}