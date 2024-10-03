package com.ravan.foodie.dailysell.di

import com.ravan.foodie.dailysell.api.DailySellApi
import com.ravan.foodie.dailysell.domain.repository.DailySellRepository
import com.ravan.foodie.dailysell.domain.repository.DailySellRepositoryImplementation
import com.ravan.foodie.dailysell.domain.usecase.GetDailySellProgramUseCase
import com.ravan.foodie.dailysell.domain.usecase.GetUserDailySalesUseCase
import com.ravan.foodie.dailysell.domain.usecase.OrderDailySellFoodUseCase
import com.ravan.foodie.dailysell.ui.viewmodel.DailySellViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val dailySellModule = module {
    single { get<Retrofit.Builder>().build().create(DailySellApi::class.java) }

    single<DailySellRepository> { DailySellRepositoryImplementation(get(), get()) }

    factory { OrderDailySellFoodUseCase(get()) }

    factory { GetDailySellProgramUseCase(get()) }

    factory { GetUserDailySalesUseCase(get()) }

    viewModel { DailySellViewModel(get(), get(), get(), get(), get()) }
}