package com.ravan.foodie.splash.di

import com.ravan.foodie.splash.ui.viewmodel.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val splashModule = module {

    viewModel<SplashScreenViewModel> {
        SplashScreenViewModel(get(), get(), get())
    }

}