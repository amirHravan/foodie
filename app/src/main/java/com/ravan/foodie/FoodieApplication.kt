package com.ravan.foodie

import android.app.Application
import com.ravan.foodie.domain.di.appModule
import com.ravan.foodie.forget.di.forgetCodeModule
import com.ravan.foodie.home.di.homeModule
import com.ravan.foodie.login.di.loginModule
import com.ravan.foodie.profile.di.profileModule
import com.ravan.foodie.order.di.orderModule
import com.ravan.foodie.reserveinfo.di.reserveInfoModule
import com.ravan.foodie.splash.di.splashModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FoodieApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@FoodieApplication)
            modules(
                appModule,
                splashModule,
                loginModule,
                homeModule,
                reserveInfoModule,
                profileModule,
                forgetCodeModule,
                orderModule
            )
        }
    }
}