package com.ravan.foodie.reserveinfo.di

import androidx.room.Room
import com.ravan.foodie.reserveinfo.api.ReserveInfoApi
import com.ravan.foodie.reserveinfo.db.ForgetCodeDatabase
import com.ravan.foodie.reserveinfo.domain.repository.ReservationInfoRepository
import com.ravan.foodie.reserveinfo.domain.repository.ReservationInfoRepositoryImplementation
import com.ravan.foodie.reserveinfo.domain.usecase.GetForgetCodeMapCacheUseCase
import com.ravan.foodie.reserveinfo.domain.usecase.GetForgetCodeUseCase
import com.ravan.foodie.reserveinfo.domain.usecase.GetReservationInformationUseCase
import com.ravan.foodie.reserveinfo.ui.viewmodel.ReservationInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val reserveInfoModule = module {
    single<ReserveInfoApi> {
        get<Retrofit.Builder>().build().create(ReserveInfoApi::class.java)
    }

    single {
        Room
            .databaseBuilder(
                get(),
                ForgetCodeDatabase::class.java,
                ForgetCodeDatabase.DATABASE_NAME
            )
            .build()
    }

    single { get<ForgetCodeDatabase>().forgetCodeDao() }

    single<ReservationInfoRepository> {
        ReservationInfoRepositoryImplementation(get(), get())
    }

    factory {
        GetReservationInformationUseCase(get())
    }

    viewModel {
        ReservationInfoViewModel(get(), get(), get())
    }

    factory {
        GetForgetCodeUseCase(get())
    }

    factory {
        GetForgetCodeMapCacheUseCase(get())
    }

}