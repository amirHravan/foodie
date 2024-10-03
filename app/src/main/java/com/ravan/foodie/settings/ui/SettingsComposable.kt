package com.ravan.foodie.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ravan.foodie.R
import com.ravan.foodie.domain.ui.component.FoodieTitleBar
import com.ravan.foodie.domain.ui.model.FoodieTitleBarUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.settings.ui.component.SettingsScreen
import com.ravan.foodie.settings.ui.component.body.SettingsBottomSheet
import com.ravan.foodie.settings.ui.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsComposable(
    viewModel: SettingsViewModel,
    navController: NavController,
) {
    val showBottomSheet = remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(RavanTheme.colors.background.primary)
    ) {
        Column {
            FoodieTitleBar(
                data = FoodieTitleBarUIModel(title = stringResource(id = R.string.settings_screen_title)),
                onBackClick = { viewModel.onBackClick() }
            ) {
                AboutUsButton {
                    viewModel.onAboutUsClick()
                }

            }
            SettingsScreen(
                onNotificationToggleChange = { newState ->
                    viewModel.onNotificationToggleChange(newState)
                }
            )
            if (showBottomSheet.value) {
                ModalBottomSheet(
                    modifier = Modifier,
                    sheetState = sheetState,
                    onDismissRequest = { showBottomSheet.value = false },
                ) {
                    RavanTheme {
                        SettingsBottomSheet()
                    }
                }
            }
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.navBack.setNavigateAction {
            navController.popBackStack()
        }

        viewModel.navAboutUs.setNavigateAction {
            showBottomSheet.value = true
        }
    }

}

@Composable
private fun AboutUsButton(onAboutUsClick: () -> Unit) {
    Icon(
        painter = painterResource(id = R.drawable.ic_info),
        contentDescription = "About_Us",
        tint = RavanTheme.colors.icon.onPrimary,
        modifier = Modifier
            .clip(RavanTheme.shapes.pill)
            .clickable(onClick = onAboutUsClick)
    )
}

