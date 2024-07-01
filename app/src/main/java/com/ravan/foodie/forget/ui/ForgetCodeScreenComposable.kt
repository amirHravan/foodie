package com.ravan.foodie.forget.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ravan.foodie.domain.model.LoadableData
import com.ravan.foodie.domain.ui.component.FoodieFailCard
import com.ravan.foodie.domain.ui.component.FoodieProgressIndicator
import com.ravan.foodie.domain.ui.component.FoodieTitleBar
import com.ravan.foodie.domain.ui.model.FoodieFailCardUIModel
import com.ravan.foodie.domain.ui.model.FoodieTitleBarUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.forget.ui.component.ForgetCodeScreen
import com.ravan.foodie.forget.ui.model.ForgetCodeScreenUIModel
import com.ravan.foodie.forget.ui.viewmodel.ForgetCodeViewModel

@Composable
fun ForgetCodeScreenComposable(
    viewModel: ForgetCodeViewModel,
    navController: NavController,
) {

    val forgetCodeInfo = remember(
        viewModel.forgetCodeScreenUIModel.value
    ) { viewModel.forgetCodeScreenUIModel.value }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RavanTheme.colors.background.primary)
    ) {
        FoodieTitleBar(
            data = FoodieTitleBarUIModel(
                title = "کد فراموشی",
            ), onBackClick = { viewModel.onBackClick() }
        )
        when (forgetCodeInfo) {
            is LoadableData.Failed -> {
                FoodieFailCard(
                    data = FoodieFailCardUIModel(
                        title = forgetCodeInfo.message,
                    ), onReloadClick = { viewModel.onRefresh() },
                    modifier = Modifier.fillMaxSize()
                )
            }

            is LoadableData.Loaded<*> -> {
                ForgetCodeScreen(
                    data = forgetCodeInfo.data as ForgetCodeScreenUIModel,
                    onGetForgetCode = { viewModel.onGetForgetCode(it) }
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

    LaunchedEffect(forgetCodeInfo) {
        viewModel.navBack.setNavigateAction {
            navController.popBackStack()
        }
    }

}
