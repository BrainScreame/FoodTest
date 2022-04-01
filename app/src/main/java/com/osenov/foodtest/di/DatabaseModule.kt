package com.osenov.foodtest.di

import android.content.Context
import androidx.room.Room
import com.osenov.foodtest.data.local.FoodDao
import com.osenov.foodtest.data.local.FoodDatabase
import com.osenov.foodtest.data.local.FoodDatabase.Companion.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): FoodDatabase {
        return Room.databaseBuilder(
            appContext,
            FoodDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideCharacterDao(foodDatabase: FoodDatabase): FoodDao {
        return foodDatabase.foodDao()
    }
}