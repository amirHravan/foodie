package com.ravan.foodie.autoreserve.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ravan.foodie.autoreserve.ui.component.reserve.AutoReserveScreen
import com.ravan.foodie.autoreserve.ui.viewmodel.AutoReserveViewModel
import com.ravan.foodie.domain.model.LoadableData
import com.ravan.foodie.domain.ui.component.FoodieFailCard
import com.ravan.foodie.domain.ui.component.FoodieInformationBox
import com.ravan.foodie.domain.ui.component.FoodieProgressIndicator
import com.ravan.foodie.domain.ui.model.FoodieFailCardUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.domain.util.FoodieRoutes


@Composable
fun AutoReserveComposable(
    viewModel: AutoReserveViewModel,
    navController: NavController,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(RavanTheme.colors.background.primary)
    ) {

        when (val data = viewModel.autoReserveScreenUIModel.value) {
            is LoadableData.NotLoaded -> Unit
            is LoadableData.Loading -> {
                FoodieProgressIndicator(
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is LoadableData.Loaded -> {
                AutoReserveScreen(
                    data = data.data,
                    reserveResultInfoRowUIModelList = viewModel.reserveResponseLog.value,
                    selectSelfRowUIModel = viewModel.selectSelfRowUIModel.value,
                    onExpandSelfDialogClick = { viewModel.onSelectSelfClick() },
                    onSelectSelfClick = { selfDialogRowUIModel ->
                        viewModel.onSelfClick(
                            selfDialogRowUIModel
                        )
                    },
                    onPrioritySelectionClick = { viewModel.onPrioritySelectionClick() },
                    onSelectDayClick = { daysOfWeek, selected ->
                        viewModel.onSelectDayClick(
                            day = daysOfWeek,
                            isSelected = selected
                        )
                    },
                    onAutoReserveClick = { viewModel.onReserveClick() },
                    onIncreaseCreditClick = { viewModel.onIncreaseCreditClick(it) },
                    onBackClick = { viewModel.onBackClick() },
                )
            }

            is LoadableData.Failed -> {
                FoodieFailCard(
                    data = FoodieFailCardUIModel(
                        title = data.message,
                    ), onReloadClick = { viewModel.onRefresh() },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        AnimatedVisibility(visible = viewModel.showMessage.value) {
            viewModel.informationBoxUIModel.value?.let {
                FoodieInformationBox(
                    data = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }

        }


    }

    LaunchedEffect(viewModel.autoReserveScreenUIModel) {
        viewModel.navBack.setNavigateAction {
            navController.popBackStack()
        }

        viewModel.navReserve.setNavigateAction {
            navController.navigate(FoodieRoutes.FoodPriorityScreen.route)
        }
    }

    LaunchedEffect(true) {
        viewModel.onLaunch()
    }
}
