package com.ravan.foodie.account.di

import com.ravan.foodie.account.domain.repository.AccountManager
import com.ravan.foodie.account.domain.repository.AccountStore
import com.ravan.foodie.account.domain.repository.ReserveInFlightTracker
import com.ravan.foodie.account.ui.viewmodel.AccountsViewModel
import com.ravan.foodie.autoreserve.domain.repository.AutoReserveRepository
import com.ravan.foodie.domain.model.PreferencesManager
import com.ravan.foodie.profile.domain.usecase.GetNurtureProfile
import com.ravan.foodie.reserveinfo.domain.repository.ReservationInfoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val accountModule = module {

    single { AccountStore(get<PreferencesManager>(), get()) }

    single { ReserveInFlightTracker() }

    single {
        val getNurtureProfile = get<GetNurtureProfile>()
        AccountManager(
            store = get(),
            tokenProvider = get(),
            inFlight = get(),
            accountData = listOf(get<AutoReserveRepository>(), get<ReservationInfoRepository>()),
            loadDisplayName = {
                getNurtureProfile().map { "${it.firstName} ${it.lastName}".trim() }
            },
            appScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate),
        )
    }

    viewModel { AccountsViewModel(get(), get(), get()) }
}
