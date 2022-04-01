package com.osenov.foodtest.ui.menu.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.osenov.foodtest.databinding.ItemProductBinding
import com.osenov.foodtest.domain.entity.Product

class ProductAdapter(private val onItemClicked: (Product) -> Unit) : RecyclerView.Adapter<ProductViewHolder>() {

    private var products = ArrayList<Product>()

    fun setData(data: List<Product>) {
        products.clear()
        this.products.addAll(data)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
        holder.itemView.setOnClickListener {
            onItemClicked(products[position])
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }
}