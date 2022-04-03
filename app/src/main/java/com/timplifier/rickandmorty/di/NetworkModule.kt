package com.timplifier.rickandmorty.di

import com.timplifier.rickandmorty.data.remote.RetrofitClient
import org.koin.dsl.module


val networkModule = module {
    val retrofitClient = RetrofitClient()

    single { retrofitClient.provideCharactersApiService() }
    single { retrofitClient.provideLocationApiService() }
    single { retrofitClient.provideEpisodesApiService() }
}