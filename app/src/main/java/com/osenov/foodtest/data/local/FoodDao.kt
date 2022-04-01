package com.osenov.foodtest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.osenov.foodtest.domain.room_entity.ProductEntity

@Dao
interface  FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(characters: List<ProductEntity>)

    @Query("DELETE FROM products")
    suspend fun deleteProducts()

    @Query("SELECT * FROM products")
    suspend fun getProducts() : List<ProductEntity>

}