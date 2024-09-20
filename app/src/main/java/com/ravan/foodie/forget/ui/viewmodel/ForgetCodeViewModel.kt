package com.ravan.foodie.forget.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import com.ravan.foodie.domain.model.LoadableData
import com.ravan.foodie.domain.model.NavigationEvent
import com.ravan.foodie.domain.ui.viewmodel.RavanViewModel
import com.ravan.foodie.domain.usecase.GetSavedSamadTokenUseCase
import com.ravan.foodie.forget.domain.usecase.GetForgetCodeUseCase
import com.ravan.foodie.forget.domain.usecase.GetTodayMealUseCase
import com.ravan.foodie.forget.ui.fixture.forgetCodeScreenUIModelFixture

class ForgetCodeViewModel(
    private val getForgetCodeUseCase: GetForgetCodeUseCase,
    private val getTodayMealUseCase: GetTodayMealUseCase,
    private val getSavedSamadTokenUseCase: GetSavedSamadTokenUseCase,
): RavanViewModel() {
    val forgetCodeScreenUIModel = mutableStateOf<LoadableData>(LoadableData.NotLoaded)
    val navBack = NavigationEvent()

    fun onLaunch() {
        loadForgetCodePage()
    }

    fun onRefresh() {
        onLaunch()
    }

    fun onBackClick() {
        navBack.navigate()
    }

    fun onGetForgetCode(
        reserveId: Int,
    ) {

    }

    private fun loadForgetCodePage() {
        forgetCodeScreenUIModel.value = LoadableData.Loaded(forgetCodeScreenUIModelFixture)
    }
}