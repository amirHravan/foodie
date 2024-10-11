package com.ravan.foodie.settings.ui.component

import android.Manifest
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.ravan.foodie.R
import com.ravan.foodie.domain.notification.setAlarmsBasedOnPreference
import com.ravan.foodie.domain.ui.component.FoodieButton
import com.ravan.foodie.domain.ui.model.FoodieButtonUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.settings.ui.component.body.SettingsTitleToggleRow
import com.ravan.foodie.settings.ui.model.SettingsTitleToggleRowUIModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SettingsScreen(
    onNotificationToggleChange: (Boolean) -> Unit,
    onLogoutClick: () -> Unit,
) {

    val notificationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(
            permission = Manifest.permission.POST_NOTIFICATIONS
        )
    } else {
        null
    }

    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(RavanTheme.colors.background.primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.End
    ) {
        item {
            SettingsTitleToggleRow(
                data = SettingsTitleToggleRowUIModel(title = stringResource(id = R.string.settings_notification_toggle_title)),
                onToggleChange = {
                    if (it && notificationPermission?.status?.isGranted == false) {
                        notificationPermission.launchPermissionRequest()
                    }
                    onNotificationToggleChange(it)
                    setAlarmsBasedOnPreference(context)
                },
                initState = true,
            )
        }

        item {
            FoodieButton(
                data = FoodieButtonUIModel.General(
                    title = stringResource(id = R.string.settings_logout_button_label),
                    iconRes = R.drawable.ic_logout
                ), onClick = onLogoutClick,
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(1f)
            )
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    RavanTheme {
        SettingsScreen(
            onNotificationToggleChange = {},
            onLogoutClick = {}
        )

    }
}