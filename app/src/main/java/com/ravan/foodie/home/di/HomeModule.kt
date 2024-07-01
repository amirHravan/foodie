package com.ravan.foodie.home.di

import com.ravan.foodie.home.ui.viewmodel.HomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel {
        HomeScreenViewModel()
    }
}