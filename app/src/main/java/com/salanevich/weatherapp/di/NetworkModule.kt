package com.salanevich.weatherapp.di

import com.salanevich.weatherapp.BuildConfig
import com.salanevich.weatherapp.data.NetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @ApiKey
    fun getApiKey(): String = BuildConfig.API_KEY

    @Singleton
    @Provides
    fun provideRetrofit(): NetworkService {
        return Retrofit.Builder()
            .baseUrl("http://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NetworkService::class.java)
    }

}

@Qualifier
@Retention
annotation class ApiKey