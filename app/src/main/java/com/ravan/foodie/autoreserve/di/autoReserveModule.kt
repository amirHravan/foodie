package com.ravan.foodie.autoreserve.di

import androidx.room.Room
import com.ravan.foodie.autoreserve.api.AutoReserveApi
import com.ravan.foodie.account.domain.repository.AccountStore
import com.ravan.foodie.autoreserve.db.AutoReserveDataBase
import com.ravan.foodie.autoreserve.db.AutoReserveMigration1To2
import com.ravan.foodie.autoreserve.db.PrepopulateFoodsDatabase
import com.ravan.foodie.autoreserve.domain.repository.AutoReserveRepository
import com.ravan.foodie.autoreserve.domain.repository.AutoReserveRepositoryImplementation
import com.ravan.foodie.autoreserve.domain.usecase.GetAllFoodsUseCase
import com.ravan.foodie.autoreserve.domain.usecase.GetAllSelectedDaysUseCase
import com.ravan.foodie.autoreserve.domain.usecase.InsertFoodUseCase
import com.ravan.foodie.autoreserve.domain.usecase.UpdateAutoReserveDaysUseCase
import com.ravan.foodie.autoreserve.domain.usecase.UpdateFoodPriorityUseCase
import com.ravan.foodie.autoreserve.ui.viewmodel.AutoReserveViewModel
import com.ravan.foodie.autoreserve.ui.viewmodel.PrioritySelectionViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val autoReserveModule = module {

    single {
        val accountStore = get<AccountStore>()
        Room
            .databaseBuilder(
                get(),
                AutoReserveDataBase::class.java,
                AutoReserveDataBase.DATABASE_NAME
            )
            .addCallback(PrepopulateFoodsDatabase(androidContext()))
            .addMigrations(AutoReserveMigration1To2 { accountStore.legacyUsername })
            .build()
    }

    single { get<Retrofit.Builder>().build().create(AutoReserveApi::class.java) }

    single<AutoReserveRepository> {
        val accountStore = get<AccountStore>()
        AutoReserveRepositoryImplementation(get()) { accountStore.active?.username }
    }

    factory { GetAllSelectedDaysUseCase(get()) }

    factory { GetAllFoodsUseCase(get()) }

    factory { InsertFoodUseCase(get()) }

    factory { UpdateAutoReserveDaysUseCase(get()) }

    factory { UpdateFoodPriorityUseCase(get()) }

    viewModel { AutoReserveViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }

    viewModel { PrioritySelectionViewModel(get(), get()) }

}