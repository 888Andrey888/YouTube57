package com.example.youtube57.core.di

import com.example.youtube57.core.network.RemoteDataSource
import org.koin.dsl.module

val remoteDataSource = module {
    single { RemoteDataSource(get()) }
}