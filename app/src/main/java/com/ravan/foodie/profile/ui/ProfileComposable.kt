package com.ravan.foodie.profile.ui

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
import com.ravan.foodie.profile.ui.component.ProfileScreen
import com.ravan.foodie.profile.ui.model.ProfileScreenUIModel
import com.ravan.foodie.profile.ui.viewmodel.ProfileViewModel

@Composable
fun ProfileComposable(
    viewModel: ProfileViewModel,
    navController: NavController,
) {

    val profileUIModel = remember(viewModel.profileUIModel.value) {
        viewModel.profileUIModel.value
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(RavanTheme.colors.background.primary)
    ) {
        FoodieTitleBar(
            data = FoodieTitleBarUIModel(title = stringResource(R.string.nurture_profile_titlebar_name)),
            onBackClick = { viewModel.onBackClick() }
        )

        when (profileUIModel) {
            is LoadableData.Loading -> {
                FoodieProgressIndicator(
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is LoadableData.Loaded<*> -> {
                ProfileScreen(data = profileUIModel.data as ProfileScreenUIModel)
            }

            is LoadableData.Failed -> {
                FoodieFailCard(
                    data = FoodieFailCardUIModel(
                        title = profileUIModel.message,
                    ), onReloadClick = { viewModel.onRefresh() },
                    modifier = Modifier.fillMaxSize()
                )
            }

            is LoadableData.NotLoaded -> Unit
        }
    }
    LaunchedEffect(profileUIModel) {
        viewModel.navBack.setNavigateAction {
            navController.popBackStack()
        }
    }

    LaunchedEffect(true) {
        viewModel.onLaunch()
    }

}
