package com.osenov.foodtest.domain.entity

import com.osenov.foodtest.domain.room_entity.ProductEntity

data class Product(
    val name: String,
    val description: String,
    val price : Int,
    val category: String,
    val imageUrl: String
) {
    fun toProductEntity() = ProductEntity(0, name, description, price, category, imageUrl)
}