package com.ravan.foodie.login.di

import com.ravan.foodie.login.ui.viewmodel.LoginScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {

    viewModel<LoginScreenViewModel> { LoginScreenViewModel(get(), get()) }
}