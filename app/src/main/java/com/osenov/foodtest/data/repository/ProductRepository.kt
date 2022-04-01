package com.osenov.foodtest.data.repository

import com.osenov.foodtest.data.local.FoodDao
import com.osenov.foodtest.data.remote.ProductRemoteDataSource
import com.osenov.foodtest.domain.entity.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import com.osenov.foodtest.util.Result
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productService: ProductRemoteDataSource,
    private val foodDao: FoodDao
) {

    suspend fun getProducts(): Flow<Result<List<Product>>> =
        flow {
            try {
                emit(Result.loading<ArrayList<Product>>())
                val response = productService.fetchProducts()
                if (response.isSuccessful) {
                    val products = response.body() ?: listOf()
                    emit(Result.success(products))
                    foodDao.deleteProducts()
                    foodDao.insertProducts(products.map { it.toProductEntity() })
                } else {
                    val products = foodDao.getProducts().map { it.toProduct() }
                    if(products.isEmpty()){
                        emit(Result.error<ArrayList<Product>>(response.message(), null))
                    } else {
                        emit(Result.success(products))
                    }
                }
            } catch (e: Exception) {
                val products = foodDao.getProducts().map { it.toProduct() }
                if(products.isEmpty()){
                    emit(Result.error<ArrayList<Product>>(e.message.toString(), null))
                } else {
                    emit(Result.success(products))
                }
            }
        }.flowOn(Dispatchers.IO)


}