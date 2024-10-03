package com.ravan.foodie.credit.di

import com.ravan.foodie.credit.api.CreditApi
import com.ravan.foodie.credit.domain.repository.CreditRepository
import com.ravan.foodie.credit.domain.repository.CreditRepositoryImplementation
import com.ravan.foodie.credit.domain.usecase.GetRedirectLoginAsTokenUseCase
import org.koin.dsl.module
import retrofit2.Retrofit

val creditModule = module {

    single { get<Retrofit.Builder>().build().create(CreditApi::class.java) }

    single<CreditRepository> { CreditRepositoryImplementation(get()) }

    factory { GetRedirectLoginAsTokenUseCase(get()) }
}