package com.ravan.foodie.forget.di

import com.ravan.foodie.forget.api.ForgetCodeApi
import com.ravan.foodie.forget.domain.repository.ForgetCodeRepository
import com.ravan.foodie.forget.domain.repository.ForgetCodeRepositoryImplementation
import com.ravan.foodie.forget.domain.usecase.GetForgetCodeUseCase
import com.ravan.foodie.forget.domain.usecase.GetTodayMealUseCase
import com.ravan.foodie.forget.ui.viewmodel.ForgetCodeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val forgetCodeModule = module {
    single {
        get<Retrofit.Builder>().build().create(ForgetCodeApi::class.java)
    }

    single<ForgetCodeRepository> {
        ForgetCodeRepositoryImplementation(get())
    }

    factory {
        GetForgetCodeUseCase(get())
    }

    factory {
        GetTodayMealUseCase(get())
    }

    viewModel {
        ForgetCodeViewModel(get(), get())
    }
}