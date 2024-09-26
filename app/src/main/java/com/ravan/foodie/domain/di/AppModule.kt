package com.ravan.foodie.domain.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ravan.foodie.domain.api.DomainApi
import com.ravan.foodie.domain.api.TokenApi
import com.ravan.foodie.domain.model.PreferencesManager
import com.ravan.foodie.domain.network.AuthInterceptor
import com.ravan.foodie.domain.repository.DomainRepository
import com.ravan.foodie.domain.repository.DomainRepositoryImplementation
import com.ravan.foodie.domain.repository.TokenProvider
import com.ravan.foodie.domain.usecase.SaveSamadTokenUseCase
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    val networkJson = Json { ignoreUnknownKeys = true }

    single { TokenProvider() }

    single {
        PreferencesManager(get())
    }

    single {
        Retrofit.Builder()
        .baseUrl("https://setad.dining.sharif.edu/")
        .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(TokenApi::class.java)
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(get()))
            .addInterceptor(HttpLoggingInterceptor())
            .build()
    }

    single<Retrofit.Builder> {
        Retrofit
            .Builder()
            .baseUrl("https://setad.dining.sharif.edu/")
            .client(get())
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
    }

    single { get<Retrofit.Builder>().build().create(DomainApi::class.java) }


    single<DomainRepository> { DomainRepositoryImplementation(get()) }

    factory { SaveSamadTokenUseCase(get()) }
}