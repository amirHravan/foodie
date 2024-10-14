package com.ravan.foodie.autoreserve.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ravan.foodie.autoreserve.ui.component.selection.FoodPriorityScreen
import com.ravan.foodie.autoreserve.ui.viewmodel.PrioritySelectionViewModel
import com.ravan.foodie.domain.model.LoadableData
import com.ravan.foodie.domain.ui.component.FoodieFailCard
import com.ravan.foodie.domain.ui.component.FoodieProgressIndicator
import com.ravan.foodie.domain.ui.model.FoodieFailCardUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme

@Composable
fun PrioritySelectionComposable(
    viewModel: PrioritySelectionViewModel,
    navController: NavController,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(RavanTheme.colors.background.primary)
    ) {
        when (val priorityScreenUIModel = viewModel.priorityScreenUIModel.value) {
            is LoadableData.Loaded -> {
                FoodPriorityScreen(
                    data = priorityScreenUIModel.data,
                    onPriorityChange = { foodId, newPriority ->
                        viewModel.onPriorityChange(
                            foodId,
                            newPriority
                        )
                    },
                    onBackClick = { viewModel.onBackClick() },
                    onClearPriorities = { viewModel.onClearPriorities() }
                )
            }

            is LoadableData.Loading -> {
                FoodieProgressIndicator(
                    modifier = Modifier.fillMaxSize()
                )
            }

            is LoadableData.Failed -> {
                FoodieFailCard(
                    data = FoodieFailCardUIModel(priorityScreenUIModel.message),
                    onReloadClick = { viewModel.onReload() })
            }

            is LoadableData.NotLoaded -> Unit
        }
    }

    LaunchedEffect(viewModel.priorityScreenUIModel) {
        viewModel.navBack.setNavigateAction {
            navController.popBackStack()
        }
    }

    LaunchedEffect(true) {
        viewModel.onLaunch()
    }

}