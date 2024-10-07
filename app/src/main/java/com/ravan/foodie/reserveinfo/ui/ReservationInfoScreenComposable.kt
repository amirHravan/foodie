package com.ravan.foodie.reserveinfo.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
import com.ravan.foodie.domain.util.FoodieRoutes
import com.ravan.foodie.reserveinfo.ui.component.ReservationInfoScreen
import com.ravan.foodie.reserveinfo.ui.viewmodel.ReservationInfoViewModel

@Composable
fun ReservationInfoScreenComposable(
    viewModel: ReservationInfoViewModel,
    navController: NavController,
    finish: () -> Unit,
) {

    val reservationInfoUIModel = remember(
        viewModel.reservationInfoUIModel.value
    ) { viewModel.reservationInfoUIModel.value }

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
                    title = stringResource(id = R.string.reservation_info_screen_title),
                ), onBackClick = null
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_tune),
                    contentDescription = "SettingsIcon",
                    tint = RavanTheme.colors.icon.onPrimary,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { viewModel.onSettingsClick() }
                )
            }
            when (reservationInfoUIModel) {
                is LoadableData.Failed -> {
                    FoodieFailCard(
                        data = FoodieFailCardUIModel(
                            title = reservationInfoUIModel.message,
                        ), onReloadClick = { viewModel.onRefresh() },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                is LoadableData.Loaded -> {
                    reservationInfoUIModel.data?.let {
                        ReservationInfoScreen(
                            data = it,
                            onGetForgetCodeClick = { id, onFinish ->
                                viewModel.onGetForgetCode(
                                    id,
                                    onFinish
                                )
                            }
                        )
                    }
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

    BackHandler {
        viewModel.onBackClick()
    }

    LaunchedEffect(true) {
        viewModel.onLaunch()
    }

    LaunchedEffect(reservationInfoUIModel) {
        viewModel.navBack.setNavigateAction {
            finish()
        }

        viewModel.navSettings.setNavigateAction {
            navController.navigate(FoodieRoutes.SettingsScreen.route)
        }
    }

}