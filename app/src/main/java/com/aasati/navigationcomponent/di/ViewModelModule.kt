package com.aasati.navigationcomponent.di

import com.aasati.navigationcomponent.viewmodel.LoginViewModel
import com.aasati.navigationcomponent.viewmodel.MainViewModel
import com.aasati.navigationcomponent.viewmodel.SignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        LoginViewModel( dao = get())
    }

    viewModel {
        SignupViewModel(get())
    }

    viewModel {
        MainViewModel(get())
    }
}