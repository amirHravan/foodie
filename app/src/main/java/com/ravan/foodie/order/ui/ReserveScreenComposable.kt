package com.ravan.foodie.order.ui

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
import com.ravan.foodie.domain.model.LoadableData
import com.ravan.foodie.domain.ui.component.FoodieFailCard
import com.ravan.foodie.domain.ui.component.FoodieInformationBox
import com.ravan.foodie.domain.ui.component.FoodieProgressIndicator
import com.ravan.foodie.domain.ui.component.FoodieTitleBar
import com.ravan.foodie.domain.ui.model.FoodieFailCardUIModel
import com.ravan.foodie.domain.ui.model.FoodieTitleBarUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.order.ui.component.OrderScreen
import com.ravan.foodie.order.ui.component.SelectSelfRow
import com.ravan.foodie.order.ui.component.SelfDialog
import com.ravan.foodie.order.ui.model.OrderScreenUIModel
import com.ravan.foodie.order.ui.model.SelfDialogUIModel
import com.ravan.foodie.order.ui.viewmodel.OrderScreenViewModel

@Composable
fun OrderScreenComposable(
    viewModel: OrderScreenViewModel,
    navController: NavController,
) {
    val data = remember(
        viewModel.orderScreenUIModel.value
    ) { viewModel.orderScreenUIModel.value }

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
                    title = stringResource(R.string.order_screen_title_bar),
                ), onBackClick = { viewModel.onBackClick() }
            )
            SelectSelfRow(
                name = viewModel.selectedSelf.value,
                isExpanded = viewModel.shouldShowSelfDialog.value,
                onClick = { viewModel.onSelectSelfClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            ) {
                    when (val selfDialogData = viewModel.selfDialogUIModel.value) {
                        is LoadableData.Loaded<*> -> {
                            SelfDialog(
                                data = selfDialogData.data as SelfDialogUIModel,
                                onSelectSelf = { viewModel.onSelfClick(it) },
                            )
                        }

                        is LoadableData.Failed,
                        LoadableData.Loading,
                        LoadableData.NotLoaded -> Unit
                    }

            }
            when (data) {
                is LoadableData.Failed -> {
                    FoodieFailCard(
                        data = FoodieFailCardUIModel(
                            title = data.message,
                        ), onReloadClick = { viewModel.onRefresh() },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                is LoadableData.Loaded<*> -> {
                    OrderScreen(
                        data = data.data as OrderScreenUIModel,
                        onReserveFoodClick = { detail, onFinish -> viewModel.onOrderFoodClick(detail, onFinish) }
                    )
                }

                is LoadableData.Loading -> {
                    FoodieProgressIndicator(
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                is LoadableData.NotLoaded -> Unit
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
    LaunchedEffect(true) {
        viewModel.onLaunch()
    }

    LaunchedEffect(data) {
        viewModel.navBack.setNavigateAction {
            navController.popBackStack()
        }
    }
}