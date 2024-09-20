package com.ravan.foodie.reserveinfo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ravan.foodie.R
import com.ravan.foodie.domain.model.LoadableData
import com.ravan.foodie.domain.ui.component.FoodieFailCard
import com.ravan.foodie.domain.ui.component.FoodieProgressIndicator
import com.ravan.foodie.domain.ui.component.FoodieTitleBar
import com.ravan.foodie.domain.ui.model.FoodieFailCardUIModel
import com.ravan.foodie.domain.ui.model.FoodieTitleBarUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.reserveinfo.ui.component.ReservationInfoScreen
import com.ravan.foodie.reserveinfo.ui.model.ReservationInfoScreenUIModel
import com.ravan.foodie.reserveinfo.ui.viewmodel.ReservationInfoViewModel

@Composable
fun ReservationInfoScreenComposable(
    viewModel: ReservationInfoViewModel,
    navController: NavController,
) {
    val reservationInfo = remember(
        viewModel.reservationInfo.value
    ) { viewModel.reservationInfo.value }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RavanTheme.colors.background.primary)
    ) {
        FoodieTitleBar(
            data = FoodieTitleBarUIModel(
                title = stringResource(id = R.string.reservation_info_screen_title),
            ), onBackClick = { viewModel.onBackClick() }
        )
        when (reservationInfo) {
            is LoadableData.Failed -> {
                FoodieFailCard(
                    data = FoodieFailCardUIModel(
                        title = reservationInfo.message,
                    ), onReloadClick = { viewModel.onRefresh() },
                    modifier = Modifier.fillMaxSize()
                )
            }

            is LoadableData.Loaded<*> -> {
                ReservationInfoScreen(
                    data = reservationInfo.data as ReservationInfoScreenUIModel,
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

    LaunchedEffect(true) {
        viewModel.onLaunch()
    }

    LaunchedEffect(reservationInfo) {
        viewModel.navBack.setNavigateAction {
            navController.popBackStack()
        }
    }

}