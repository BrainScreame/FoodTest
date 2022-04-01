package com.osenov.foodtest.di

import com.google.gson.Gson
import com.osenov.foodtest.Config.PRODUCT_URL
import com.osenov.foodtest.data.remote.ProductService
import com.osenov.foodtest.domain.entity.Product
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson) : GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideWeatherService(gsonConverterFactory: GsonConverterFactory): ProductService {
        return Retrofit.Builder()
            .baseUrl(PRODUCT_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(ProductService::class.java)
    }
}