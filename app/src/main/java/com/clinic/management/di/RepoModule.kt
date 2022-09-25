package com.clinic.management.di

import com.clinic.management.repo.MainRepository
import org.koin.dsl.module

val repoModule = module {
    single {
        MainRepository(get())
    }
}