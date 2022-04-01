package com.osenov.foodtest.data.remote

import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(private val productService: ProductService) {

    suspend fun fetchProducts() = productService.getProducts()

}