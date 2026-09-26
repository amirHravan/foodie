package com.ravan.foodie.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ravan.foodie.R
import com.ravan.foodie.account.ui.component.AccountList
import com.ravan.foodie.account.ui.component.AccountSwitchStatus
import com.ravan.foodie.account.ui.viewmodel.AccountsViewModel
import com.ravan.foodie.domain.model.LoadableData
import com.ravan.foodie.domain.ui.component.FoodieButton
import com.ravan.foodie.domain.ui.component.FoodieFailCard
import com.ravan.foodie.domain.ui.component.FoodieProgressIndicator
import com.ravan.foodie.domain.ui.component.FoodieTitleBar
import com.ravan.foodie.domain.ui.model.FoodieButtonUIModel
import com.ravan.foodie.domain.ui.model.FoodieFailCardUIModel
import com.ravan.foodie.domain.ui.model.FoodieTitleBarUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.profile.ui.component.ProfileScreen
import com.ravan.foodie.profile.ui.viewmodel.ProfileViewModel

@Composable
fun ProfileComposable(
    viewModel: ProfileViewModel,
    accountsViewModel: AccountsViewModel,
    navController: NavController,
) {
    val reservesInFlight by accountsViewModel.reservesInFlight.collectAsState()

    val profileUIModel = remember(viewModel.profileScreenUIModel.value) {
        viewModel.profileScreenUIModel.value
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(RavanTheme.colors.background.primary)
    ) {
        FoodieTitleBar(
            data = FoodieTitleBarUIModel(title = stringResource(R.string.nurture_profile_titlebar_name)),
            onBackClick = { viewModel.onBackClick() }
        ) {
            FoodieButton(
                data = FoodieButtonUIModel.General(
                    title = stringResource(id = R.string.settings_logout_button_label),
                    iconRes = R.drawable.ic_logout
                ), onClick = { accountsViewModel.onRemoveActiveClick() },
                modifier = Modifier
            )
        }

        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
        ) {
            Text(
                text = stringResource(R.string.accounts_title),
                style = RavanTheme.typography.h6,
                color = RavanTheme.colors.text.onPrimary,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            AccountSwitchStatus(
                switchingTo = accountsViewModel.switchingTo.value,
                waitingForReserve = reservesInFlight > 0,
                status = accountsViewModel.status.value,
                contentColor = RavanTheme.colors.text.onPrimary,
            )
            AccountList(
                accounts = accountsViewModel.accounts.value,
                canAddAccount = accountsViewModel.canAddAccount.value,
                contentColor = RavanTheme.colors.text.onSecondary,
                rowBackground = RavanTheme.colors.background.secondary,
                onAccountClick = { accountsViewModel.onAccountClick(it) },
                onLoginAgainClick = { accountsViewModel.onLoginAgainClick(it) },
                onAddAccountClick = { accountsViewModel.onAddAccountClick() },
                enabled = accountsViewModel.switchingTo.value == null,
                onRemoveClick = { accountsViewModel.onRemoveClick(it) },
                onMove = { from, to -> accountsViewModel.onMove(from, to) },
                addRowContentColor = RavanTheme.colors.text.onPrimary,
            )
        }

        when (profileUIModel) {
            is LoadableData.Loading -> {
                FoodieProgressIndicator(
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is LoadableData.Loaded -> {
                ProfileScreen(data = profileUIModel.data)
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
