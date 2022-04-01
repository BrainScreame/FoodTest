package com.osenov.foodtest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.osenov.foodtest.data.local.FoodDatabase.Companion.DB_VERSION
import com.osenov.foodtest.domain.room_entity.ProductEntity

@Database(entities = [ProductEntity::class], version = DB_VERSION)
abstract class FoodDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "food.db"
        const val DB_VERSION = 1
    }

    abstract fun foodDao() : FoodDao

}