package com.clinic.management.di

import com.clinic.management.viewmodel.CommonViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        CommonViewModel(get(), get())
    }
}