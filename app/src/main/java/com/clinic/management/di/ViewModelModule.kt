package com.clinic.management.di

import com.clinic.management.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        CommonViewModel(get(), get())
    }
    viewModel {
        LoginViewModel(get(), get())
    }
    viewModel {
        RegisterViewModel(get(), get())
    }
    viewModel {
        HomeViewModel(get(), get())
    }
    viewModel {
        DoctorListingViewModel(get(), get())
    }
    viewModel {
        DoctorDetailViewModel(get(), get())
    }
    viewModel {
        ActiveAppointmentViewModel(get(), get())
    }
    viewModel {
        CompleteAppointmentViewModel(get(), get())
    }
    viewModel {
        BookAppointmentViewModel(get(), get())
    }
    viewModel {
        CategoryListingViewModel(get(), get())
    }
    viewModel {
        FAQViewModel(get(), get())
    }
    viewModel {
        CancelAppointmentViewModel(get(), get())
    }
    viewModel {
        MedicineViewModel(get(), get())
    }
    viewModel {
        MedicineDetailViewModel(get(), get())
    }
    viewModel {
        DoctorAppointmentViewModel(get(), get())
    }
    viewModel {
        Lab_RadiologyViewModel(get(), get())
    }
    viewModel {
        FilterViewModel(get(), get())
    }
}