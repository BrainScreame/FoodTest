package com.osenov.foodtest.data.remote

import com.osenov.foodtest.domain.entity.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {
    @GET("food/products.json")
    suspend fun getProducts(): Response<List<Product>>
}