package com.osenov.foodtest.domain.use_case

import com.osenov.foodtest.data.repository.ProductRepository
import com.osenov.foodtest.domain.entity.Product
import com.osenov.foodtest.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend fun execute(): Flow<Result<List<Product>>> =  repository.getProducts()

}