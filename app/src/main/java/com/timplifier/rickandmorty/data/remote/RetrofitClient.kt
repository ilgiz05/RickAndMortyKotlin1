package com.timplifier.rickandmorty.data.remote

import com.timplifier.rickandmorty.common.constants.Constants.BASE_URL
import com.timplifier.rickandmorty.data.remote.apiservices.CharactersApiService
import com.timplifier.rickandmorty.data.remote.apiservices.EpisodesApiService
import com.timplifier.rickandmorty.data.remote.apiservices.LocationsApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(provideLoggingInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    fun provideCharactersApiService(): CharactersApiService =
        retrofit.create(CharactersApiService::class.java)


    fun provideLocationApiService(): LocationsApiService =
        retrofit.create(LocationsApiService::class.java)

    fun provideEpisodesApiService(): EpisodesApiService =
        retrofit.create(EpisodesApiService::class.java)


}