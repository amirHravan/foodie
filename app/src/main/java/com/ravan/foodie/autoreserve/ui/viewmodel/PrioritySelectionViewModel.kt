package com.ravan.foodie.autoreserve.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.ravan.foodie.autoreserve.domain.model.AutoReserveFoodPriority
import com.ravan.foodie.autoreserve.domain.usecase.GetAllFoodsUseCase
import com.ravan.foodie.autoreserve.domain.usecase.UpdateFoodPriorityUseCase
import com.ravan.foodie.autoreserve.ui.model.AutoReservePriorityScreenUIModel
import com.ravan.foodie.autoreserve.ui.model.toFoodPriorityUIModel
import com.ravan.foodie.domain.model.LoadableData
import com.ravan.foodie.domain.model.NavigationEvent
import com.ravan.foodie.domain.ui.viewmodel.FoodieViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PrioritySelectionViewModel(
    private val getAllFoodsUseCase: GetAllFoodsUseCase,
    private val updateFoodPriorityUseCase: UpdateFoodPriorityUseCase,
) : FoodieViewModel() {

    val priorityScreenUIModel =
        mutableStateOf<LoadableData<AutoReservePriorityScreenUIModel>>(LoadableData.NotLoaded)
    private var foodPriorityList: List<AutoReserveFoodPriority>? = null
    val navBack: NavigationEvent = NavigationEvent()

    fun onLaunch(
        offlineFirst: Boolean = false,
    ) {
        if (!offlineFirst) {
            priorityScreenUIModel.value = LoadableData.Loading
        }

        CoroutineScope(Dispatchers.IO).launch {
            getAllFoodsUseCase().fold(
                onSuccess = { foods ->
                    foodPriorityList = foods
                    priorityScreenUIModel.value = LoadableData.Loaded(
                        AutoReservePriorityScreenUIModel(
                            foodPriorityUIModelList = foods.map { it.toFoodPriorityUIModel() }
                                .sortedBy { it.priority }.reversed().toImmutableList()
                        )
                    )
                    foodPriorityList?.forEach {
                        Log.d("temp", "onLaunch: $it")
                    }
                    Log.d("temp size", "onLaunch: ${foodPriorityList?.size}")
                },
                onFailure = { error ->
                    priorityScreenUIModel.value =
                        LoadableData.Failed(error.message ?: "Unknown error")
                }
            )
        }
    }

    fun onPriorityChange(foodId: Int, newPriority: Int) {
        val food = foodPriorityList?.find { it.id == foodId } ?: return

        viewModelScope.launch {
            updateFoodPriorityUseCase(food, newPriority)
            onLaunch(true)
        }
    }

    fun onReload() {
        onLaunch()
    }

    fun onBackClick() {
        navBack.navigate()
    }

    fun onClearPriorities() {

        viewModelScope.launch {
            foodPriorityList?.forEach { foodPriority ->
                updateFoodPriorityUseCase(foodPriority, 0)
            }
            onLaunch(false)
        }
    }
}