package com.example.youtube57.core.di

import com.example.youtube57.domain.repository.Repository
import org.koin.dsl.module

val repositoryModule = module {
    single { Repository(get()) }
}