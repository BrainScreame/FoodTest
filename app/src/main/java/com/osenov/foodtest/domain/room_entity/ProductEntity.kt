package com.osenov.foodtest.domain.room_entity

import androidx.annotation.IdRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.osenov.foodtest.domain.entity.Product

@Entity(tableName = "products")
data class ProductEntity (
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val description: String,
    val price : Int,
    val category: String,
    val imageUrl: String
) {
    fun toProduct() = Product(name, description, price, category, imageUrl)
}