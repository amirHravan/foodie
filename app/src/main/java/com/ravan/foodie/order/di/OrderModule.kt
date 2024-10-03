package com.ravan.foodie.order.di

import com.ravan.foodie.order.api.OrderApi
import com.ravan.foodie.order.domain.repository.OrderFoodRepository
import com.ravan.foodie.order.domain.repository.OrderFoodRepositoryImplementation
import com.ravan.foodie.order.domain.usecase.GetAvailableSelfs
import com.ravan.foodie.order.domain.usecase.GetReservableProgramUseCase
import com.ravan.foodie.order.domain.usecase.ReserveFoodUseCase
import com.ravan.foodie.order.ui.viewmodel.OrderScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val orderModule = module {
    single {
        get<Retrofit.Builder>().build().create(OrderApi::class.java)
    }

    single<OrderFoodRepository> {
        OrderFoodRepositoryImplementation(get(), get())
    }

    factory {
        GetReservableProgramUseCase(get())
    }

    factory {
        ReserveFoodUseCase(get())
    }

    factory {
        GetAvailableSelfs(get())
    }

    viewModel {
        OrderScreenViewModel(get(), get(), get(), get())
    }
}