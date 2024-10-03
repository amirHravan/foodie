package com.ravan.foodie.domain.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ravan.foodie.domain.api.DomainApi
import com.ravan.foodie.domain.api.TokenApi
import com.ravan.foodie.domain.model.PreferencesManager
import com.ravan.foodie.domain.network.AuthInterceptor
import com.ravan.foodie.domain.repository.DomainRepository
import com.ravan.foodie.domain.repository.DomainRepositoryImplementation
import com.ravan.foodie.domain.repository.TokenProvider
import com.ravan.foodie.domain.usecase.CacheAccessTokenUseCase
import com.ravan.foodie.domain.usecase.CheckTokenValidationUseCase
import com.ravan.foodie.domain.usecase.LoginUseCase
import com.ravan.foodie.domain.usecase.RefreshAccessTokenUseCase
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    val networkJson = Json { ignoreUnknownKeys = true }

    single { networkJson }

    single {
        Retrofit.Builder()
            .baseUrl("https://setad.dining.sharif.edu/")
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(TokenApi::class.java)
    }

    single { TokenProvider(get<TokenApi>(), get()) }

    single {
        PreferencesManager(get())
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

    factory { CacheAccessTokenUseCase(get()) }

    factory { CheckTokenValidationUseCase(get()) }

    factory { RefreshAccessTokenUseCase(get()) }

    factory { LoginUseCase(get()) }
}