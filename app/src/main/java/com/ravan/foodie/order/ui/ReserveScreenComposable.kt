package com.ravan.foodie.order.ui

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ravan.foodie.R
import com.ravan.foodie.domain.model.LoadableData
import com.ravan.foodie.domain.ui.component.FoodieButton
import com.ravan.foodie.domain.ui.component.FoodieFailCard
import com.ravan.foodie.domain.ui.component.FoodieInformationBox
import com.ravan.foodie.domain.ui.component.FoodieProgressIndicator
import com.ravan.foodie.domain.ui.component.FoodieTitleBar
import com.ravan.foodie.domain.ui.model.FoodieButtonUIModel
import com.ravan.foodie.domain.ui.model.FoodieFailCardUIModel
import com.ravan.foodie.domain.ui.model.FoodieTitleBarUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.order.ui.component.OrderScreen
import com.ravan.foodie.order.ui.component.SelectSelfRow
import com.ravan.foodie.order.ui.viewmodel.OrderScreenViewModel

@Composable
fun OrderScreenComposable(
    viewModel: OrderScreenViewModel,
    navController: NavController,
) {
    val orderScreenUIModel = remember(
        viewModel.orderScreenUIModel.value
    ) { viewModel.orderScreenUIModel.value }
    val context = LocalContext.current

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
            ) {
                FoodieButton(
                    data = FoodieButtonUIModel.General(
                        iconRes = R.drawable.ic_payment,
                        title = stringResource(id = R.string.increase_credit_button_label)
                    ),
                    onClick = {
                        viewModel.onIncreaseCreditClick() { url ->
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                        }
                    },

                    )
            }
            SelectSelfRow(
                selectSelfRowUIModel = viewModel.selectSelfRowUIModel.value,
                onExpandClick = { viewModel.onSelectSelfClick() },
                onSelectSelfClick = { viewModel.onSelfClick(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            )
            when (orderScreenUIModel) {
                is LoadableData.Failed -> {
                    FoodieFailCard(
                        data = FoodieFailCardUIModel(
                            title = orderScreenUIModel.message,
                        ), onReloadClick = { viewModel.onRefresh() },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                is LoadableData.Loaded -> {
                    OrderScreen(
                        data = orderScreenUIModel.data,
                        onReserveFoodClick = { detail, onFinish ->
                            viewModel.onOrderFoodClick(
                                detail,
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

    LaunchedEffect(orderScreenUIModel) {
        viewModel.navBack.setNavigateAction {
            navController.popBackStack()
        }
    }
}