package com.osenov.foodtest.ui.menu.advertising

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.osenov.foodtest.databinding.ItemAdvertisingImageBinding
import com.osenov.foodtest.domain.entity.Advertising

class AdvertisingAdapter(private val onItemClicked: (Advertising) -> Unit) :
    RecyclerView.Adapter<AdvertisingViewHolder>() {

    private var advertisings = ArrayList<Advertising>()

    fun setData(data: List<Advertising>) {
        advertisings.clear()
        this.advertisings.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertisingViewHolder {
        return AdvertisingViewHolder(
            ItemAdvertisingImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AdvertisingViewHolder, position: Int) {
        holder.bind(advertisings[position])
        holder.itemView.setOnClickListener {
            onItemClicked(advertisings[position])
        }
    }

    override fun getItemCount(): Int {
        return advertisings.size
    }
}