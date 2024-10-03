package com.ravan.foodie.dailysell.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ravan.foodie.R
import com.ravan.foodie.dailysell.ui.component.DailySaleScreen
import com.ravan.foodie.dailysell.ui.viewmodel.DailySellViewModel
import com.ravan.foodie.domain.model.LoadableData
import com.ravan.foodie.domain.ui.component.FoodieFailCard
import com.ravan.foodie.domain.ui.component.FoodieInformationBox
import com.ravan.foodie.domain.ui.component.FoodieProgressIndicator
import com.ravan.foodie.domain.ui.component.FoodieTitleBar
import com.ravan.foodie.domain.ui.model.FoodieFailCardUIModel
import com.ravan.foodie.domain.ui.model.FoodieTitleBarUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme

@Composable
fun DailySellComposable(
    viewModel: DailySellViewModel,
    navController: NavController,
) {

    val dailySellScreenUIModel = remember(
        viewModel.dailySaleScreenUIModel.value
    ) { viewModel.dailySaleScreenUIModel.value }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(RavanTheme.colors.background.primary)
        ) {
            FoodieTitleBar(
                data = FoodieTitleBarUIModel(
                    title = stringResource(id = R.string.daily_sell_screen_title),
                ), onBackClick = { viewModel.onBackClick() }
            )
            when (dailySellScreenUIModel) {
                is LoadableData.Failed -> {
                    FoodieFailCard(
                        data = FoodieFailCardUIModel(
                            title = dailySellScreenUIModel.message,
                        ), onReloadClick = { viewModel.onRefresh() },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                is LoadableData.Loaded -> {
                    DailySaleScreen(
                        uiModel = dailySellScreenUIModel.data,
                        onGetForgetCodeClick = { id, onFinish ->
                            viewModel.onGetForgetCode(
                                id,
                                onFinish
                            )
                        },
                        onOrderDailySaleClick = { id, onFinish ->
                            viewModel.onOrderDailySale(
                                id,
                                onFinish
                            )
                        }
                    )
                }

                is LoadableData.Loading -> {
                    FoodieProgressIndicator(
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                is LoadableData.NotLoaded -> Unit
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
    }

    LaunchedEffect(true) {
        viewModel.onLaunch()
    }

    LaunchedEffect(dailySellScreenUIModel) {
        viewModel.navBack.setNavigateAction {
            navController.popBackStack()
        }
    }

}