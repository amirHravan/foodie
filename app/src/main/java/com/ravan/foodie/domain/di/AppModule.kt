package com.ravan.foodie.domain.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ravan.foodie.domain.model.PreferencesManager
import com.ravan.foodie.domain.repository.DomainRepository
import com.ravan.foodie.domain.usecase.GetSavedSamadTokenUseCase
import com.ravan.foodie.domain.usecase.SaveSamadTokenUseCase
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    val networkJson = Json { ignoreUnknownKeys = true }
    single<Retrofit.Builder> {
        Retrofit
            .Builder()
            .baseUrl("https://setad.dining.sharif.edu/")
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
    }

    single {
        PreferencesManager(get())
    }

    single { DomainRepository() }

    factory { SaveSamadTokenUseCase(get()) }

    factory { GetSavedSamadTokenUseCase(get()) }
}