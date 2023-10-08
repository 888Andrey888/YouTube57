package com.example.youtube57.core.di

import com.example.youtube57.core.network.provideApiService
import com.example.youtube57.core.network.provideInterceptor
import com.example.youtube57.core.network.provideOkHttpClient
import com.example.youtube57.core.network.provideRetrofitClient
import org.koin.dsl.module

val networkMode = module {
    single { provideInterceptor() }
    single { provideOkHttpClient(get()) }
    factory { provideRetrofitClient(get()) }
    single { provideApiService(get()) }
}