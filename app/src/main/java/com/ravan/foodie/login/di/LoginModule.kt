package com.ravan.foodie.login.di

import com.ravan.foodie.login.api.LoginApi
import com.ravan.foodie.login.domain.repository.LoginRepository
import com.ravan.foodie.login.domain.repository.LoginRepositoryImplementation
import com.ravan.foodie.login.domain.usecase.SamadLoginUseCase
import com.ravan.foodie.login.ui.viewmodel.LoginScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val loginModule = module {

    single<LoginApi> {
        get<Retrofit.Builder>().build().create(LoginApi::class.java)
    }

    single<LoginRepository> {
        LoginRepositoryImplementation(get())
    }

    factory { SamadLoginUseCase(get(), get()) }

    viewModel<LoginScreenViewModel> { LoginScreenViewModel(get(), get()) }
}