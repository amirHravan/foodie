package com.ravan.foodie.profile.di

import com.ravan.foodie.profile.api.ProfileApi
import com.ravan.foodie.profile.domain.repository.ProfileRepository
import com.ravan.foodie.profile.domain.repository.ProfileRepositoryImplementation
import com.ravan.foodie.profile.domain.usecase.CheckTokenValidationUseCase
import com.ravan.foodie.profile.domain.usecase.GetNurtureProfile
import com.ravan.foodie.profile.ui.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val profileModule = module {

    single { get<Retrofit.Builder>().build().create(ProfileApi::class.java) }

    single<ProfileRepository> { ProfileRepositoryImplementation(get()) }

    factory { CheckTokenValidationUseCase(get()) }

    factory { GetNurtureProfile(get()) }

    viewModel {
        ProfileViewModel(get(), get())
    }
}