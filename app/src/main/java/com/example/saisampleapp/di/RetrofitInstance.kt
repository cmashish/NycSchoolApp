package com.example.saisampleapp.di

import com.example.saisampleapp.data.model.api.SchoolServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitInstance {

    private const val BASE_URL = "https://data.cityofnewyork.us/resource/"

    private val okHttpClient = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build()

    @Singleton
    private val retrofit: Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).addConverterFactory(
            GsonConverterFactory.create()
        ).build()

    @get:Provides
    @Singleton
    val api: SchoolServices by lazy {
        retrofit.create(SchoolServices::class.java)
    }
}